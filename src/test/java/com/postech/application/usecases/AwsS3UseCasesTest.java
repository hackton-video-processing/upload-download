package com.postech.application.usecases;

import com.postech.application.client.AwsClientGateway;
import com.postech.domain.enums.AwsErrorEnum;
import com.postech.domain.exceptions.AwsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AwsS3UseCasesTest {

    private AwsClientGateway client;
    private AwsS3UseCases awsS3UseCases;

    @BeforeEach
    void setUp() {
        client = mock(AwsClientGateway.class);
        awsS3UseCases = new AwsS3UseCases(client);
    }

    @Test
    void shouldSaveFilesSuccessfully() {
        List<File> files = List.of(new File("test-file.txt"));
        String pathName = "uploads/";
        String s3BucketName = "my-bucket";

        assertDoesNotThrow(() -> awsS3UseCases.saveFiles(files, pathName, s3BucketName));
        verify(client, times(1)).saveS3Files(files, s3BucketName, pathName);
    }

    @Test
    void shouldThrowAwsExceptionWhenSaveFilesFails() {
        List<File> files = List.of(new File("test-file.txt"));
        String pathName = "uploads/";
        String s3BucketName = "my-bucket";

        doThrow(new RuntimeException("AWS error")).when(client).saveS3Files(files, s3BucketName, pathName);

        AwsException exception = assertThrows(AwsException.class,
                () -> awsS3UseCases.saveFiles(files, pathName, s3BucketName)
        );

        assertEquals(AwsErrorEnum.S3_ERROR_SAVING, exception.getError());
    }

    @Test
    void shouldConvertMultiPartFilesToFileList(@TempDir Path tempDir) throws Exception {
        MultipartFile multipartFile = mock(MultipartFile.class);
        Path tempFile = tempDir.resolve("test-video.mp4");
        Files.write(tempFile, "dummy data".getBytes());

        when(multipartFile.getOriginalFilename()).thenReturn("test-video.mp4");
        when(multipartFile.getBytes()).thenReturn(Files.readAllBytes(tempFile));

        List<File> convertedFiles = awsS3UseCases.convertMultiPartListToFileList(List.of(multipartFile));

        assertNotNull(convertedFiles);
        assertEquals(1, convertedFiles.size());
        assertEquals("test-video.mp4", convertedFiles.get(0).getName());
        assertTrue(convertedFiles.get(0).exists());
    }

    @Test
    void shouldThrowAwsExceptionWhenFileConversionFails() throws Exception {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn("invalid-file.mp4");
        when(multipartFile.getBytes()).thenThrow(new RuntimeException("File read error"));

        AwsException exception = assertThrows(AwsException.class,
                () -> awsS3UseCases.convertMultiPartListToFileList(List.of(multipartFile))
        );

        assertEquals(AwsErrorEnum.S3_FILE_CONVERT_ERROR, exception.getError());
    }
}
