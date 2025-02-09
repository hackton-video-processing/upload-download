package com.postech.application.usecases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.postech.application.client.ProcessClient;
import com.postech.application.client.UserClient;
import com.postech.domain.dto.FileDTO;
import com.postech.domain.entities.Process;
import com.postech.domain.enums.ProcessErrorEnum;
import com.postech.domain.exceptions.ProcessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.global.avformat;
import org.bytedeco.javacpp.PointerPointer;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ProcessUseCases {

    private final AwsS3UseCases s3UseCases;
    private final MskS3UseCases mskUseCases;
    private final ProcessClient processClient;
    private final UserClient userClient;
    private final String s3PathName;
    private final String s3BucketName;
    private final Long videoMaxFileSizeMB;
    private final Long videoMaxDurationSeconds;

    public Process process(String token, List<MultipartFile> multipartFiles) {

        if (multipartFiles == null || multipartFiles.isEmpty() || multipartFiles.size() > 3) {
            throw new ProcessException(ProcessErrorEnum.INVALID_QUANTITY_VIDEOS);
        }

        tokenValidate(token);

        var fileList = s3UseCases.convertMultiPartListToFileList(multipartFiles);

        isValidVideos(fileList);

        var fileDtoList = new ArrayList<FileDTO>();
        var fileNameList = new ArrayList<String>();
        fileList.forEach(file -> {
            fileNameList.add(file.getName());
            fileDtoList.add(FileDTO.builder()
                    .name(file.getName()).build());
        });

        var createdProcess = processClient.createProcess(fileNameList);

        createdProcess.setFiles(fileDtoList);

        s3UseCases.saveFiles(fileList, s3BucketName, s3PathName);
        mskUseCases.sendMessage(createdProcess.getId(), emailExtract(token));

        fileList.forEach(file -> {
            boolean isDeleted = file.delete();
            if (!isDeleted) {
                log.error("Ocorreu um erro ao deletar o arquivo da memória temporária: {}", file.getName());
            }
        });

        return createdProcess;
    }

    public Process consult(String processId, String token) {

        tokenValidate(token);

        var process = processClient.consultProcess(processId);

        if (process.isEmpty()) throw new ProcessException(ProcessErrorEnum.NOT_FOUND);

        return process.get();
    }

    private void tokenValidate(String token) {
        if (!userClient.validateToken(token))
            throw new ProcessException(ProcessErrorEnum.INVALID_TOKEN);
    }

    private String emailExtract(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("sub").asString();
    }


    public void isValidVideos(List<File> files) {
        files.forEach(file -> {
            if (!isValidFileSize(file)) {
                throw new ProcessException(ProcessErrorEnum.INVALID_FILE_SIZE);
            }
            try {
                if (!isValidMimeType(file)) {
                    throw new ProcessException(ProcessErrorEnum.INVALID_FILE_TYPE);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (!isValidDuration(file)) {
                throw new ProcessException(ProcessErrorEnum.INVALID_FILE_DURATION);
            }
        });
    }

    private boolean isValidFileSize(File file) {
        long fileSizeInMB = file.length() / (1024 * 1024);
        return fileSizeInMB <= videoMaxFileSizeMB;
    }

    private boolean isValidMimeType(File file) throws IOException {
        Tika tika = new Tika();
        String mimeType = tika.detect(file);
        return mimeType.startsWith("video/");
    }

    public boolean isValidDuration(File file) {
        AVFormatContext formatContext = avformat.avformat_alloc_context();
        try {
            if (avformat.avformat_open_input(formatContext, file.getAbsolutePath(), null, null) != 0) {
                return false;
            }

            if (avformat.avformat_find_stream_info(formatContext, (PointerPointer) null) < 0) {
                return false;
            }

            long durationMicroseconds = formatContext.duration();
            long durationSeconds = durationMicroseconds / 1_000_000;

            return durationSeconds <= videoMaxDurationSeconds;
        } finally {
            if (formatContext != null) {
                avformat.avformat_close_input(formatContext);
                avformat.avformat_free_context(formatContext);
            }
        }
    }

}
