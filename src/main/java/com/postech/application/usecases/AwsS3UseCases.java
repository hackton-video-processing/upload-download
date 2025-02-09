package com.postech.application.usecases;

import com.postech.application.client.AwsClientGateway;
import com.postech.domain.enums.AwsErrorEnum;
import com.postech.domain.exceptions.AwsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class AwsS3UseCases {

    private final AwsClientGateway client;

    public void saveFiles(List<File> files, String pathName, String s3BucketName) {
        try {
            client.saveS3Files(files, s3BucketName, pathName);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AwsException(AwsErrorEnum.S3_ERROR_SAVING);
        }
    }

    public List<File> convertMultiPartListToFileList(List<MultipartFile> files) {

        List<File> convFiles = new ArrayList<>();

        files.forEach(file -> {
            var convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
            try (FileOutputStream fos = new FileOutputStream(convFile)) {
                fos.write(file.getBytes());
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new AwsException(AwsErrorEnum.S3_FILE_CONVERT_ERROR);
            }
            convFiles.add(convFile);
        });

        return convFiles;
    }

}
