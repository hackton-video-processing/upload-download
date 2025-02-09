package com.postech.application.client;

import com.postech.domain.dto.MessageDTO;

import java.io.File;
import java.util.List;

public interface AwsClientGateway {

    void saveS3Files(List<File> file, String bucket, String pathName);
    void sendMskMessage(MessageDTO message, String topicName);

}
