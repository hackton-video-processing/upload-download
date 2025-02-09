package com.postech.application.usecases;

import com.postech.application.client.AwsClientGateway;
import com.postech.domain.dto.MessageDTO;
import com.postech.domain.enums.AwsErrorEnum;
import com.postech.domain.exceptions.AwsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MskS3UseCasesTests {

    private AwsClientGateway client;
    private MskS3UseCases mskS3UseCases;
    private final String topicName = "test-topic";

    @BeforeEach
    void setUp() {
        client = mock(AwsClientGateway.class);
        mskS3UseCases = new MskS3UseCases(client, topicName);
    }

    @Test
    void shouldSendMessageSuccessfully() {
        String processId = "123456";
        String userEmail = "user@example.com";

        assertDoesNotThrow(() -> mskS3UseCases.sendMessage(processId, userEmail));

        verify(client, times(1)).sendMskMessage(
                eq(MessageDTO.builder().processId(processId).email(userEmail).build()),
                eq(topicName)
        );
    }

    @Test
    void shouldThrowAwsExceptionWhenMessageSendingFails() {
        String processId = "123456";
        String userEmail = "user@example.com";

        doThrow(new RuntimeException("MSK error")).when(client)
                .sendMskMessage(any(MessageDTO.class), eq(topicName));

        AwsException exception = assertThrows(AwsException.class,
                () -> mskS3UseCases.sendMessage(processId, userEmail)
        );

        assertEquals(AwsErrorEnum.MSK_ERROR_SENDING_MESSAGE, exception.getError());
    }

}
