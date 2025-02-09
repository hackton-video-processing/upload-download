package com.postech.application.usecases;

import com.postech.application.client.ProcessClient;
import com.postech.application.client.UserClient;
import com.postech.domain.entities.Process;
import com.postech.domain.exceptions.ProcessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProcessUseCasesTests {

    @Mock
    private AwsS3UseCases s3UseCases;

    @Mock
    private MskS3UseCases mskUseCases;

    @Mock
    private ProcessClient processClient;

    @Mock
    private UserClient userClient;

    private ProcessUseCases processUseCases;

    private final String token = "valid-token";

    @BeforeEach
    void setUp() {
        when(userClient.validateToken(token)).thenReturn(true);
        processUseCases = new ProcessUseCases(s3UseCases, mskUseCases, processClient, userClient, "test", "teste", 100L, 300L);
    }

    @Test
    void shouldThrowExceptionWhenInvalidToken() {
        when(userClient.validateToken(token)).thenReturn(false);
        assertThrows(ProcessException.class, () -> processUseCases.consult("123", token));
    }

//    @Test
//    void shouldProcessVideosSuccessfully() {
//        MultipartFile fileMock = mock(MultipartFile.class);
//        File convertedFile = new File("test.mp4");
//        String processId = UUID.randomUUID().toString();
//        Process processMock = new Process();
//        processMock.setId(processId);
//
//        when(s3UseCases.convertMultiPartListToFileList(any())).thenReturn(List.of(convertedFile));
//        doNothing().when(s3UseCases).saveFiles(any(), any(), any());
//        doNothing().when(mskUseCases).sendMessage(any(), any());
//        when(processClient.createProcess(any())).thenReturn(processMock);
//
//        Process result = processUseCases.process(token, List.of(fileMock));
//
//        assertNotNull(result);
//        assertEquals(processId, result.getId());
//    }

    @Test
    void shouldThrowExceptionWhenProcessNotFound() {
        when(processClient.consultProcess(any())).thenReturn(Optional.empty());

        assertThrows(ProcessException.class, () -> processUseCases.consult("invalid-id", token));
    }
}
