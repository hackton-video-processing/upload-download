package com.postech.application.usecases;

import com.postech.application.client.AwsClientGateway;
import com.postech.domain.dto.MessageDTO;
import com.postech.domain.enums.AwsErrorEnum;
import com.postech.domain.exceptions.AwsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MskS3UseCases {

    private final AwsClientGateway client;
    private final String topicName;

    public void sendMessage(String processId, String userEmail) {
        try {
            client.sendMskMessage(MessageDTO.builder().processId(processId).email(userEmail).build(), topicName);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AwsException(AwsErrorEnum.MSK_ERROR_SENDING_MESSAGE);
        }
    }

}
