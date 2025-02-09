package com.postech.infra.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.postech.application.client.AwsClientGateway;
import com.postech.domain.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class AwsS3ClientImpl implements AwsClientGateway {

    private final S3Client s3Client;
    private final Producer<String, String> kafkaProducer;
    private final ObjectMapper objectMapper;

    @Override
    public void saveS3Files(List<File> files, String bucket, String pathName) {
        for (File file : files) {
            try {
                String fileName = pathName + "/" + file.getName();

                var putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .build();

                s3Client.putObject(putObjectRequest, Paths.get(file.getAbsolutePath()));

                log.info("Arquivo enviado para o S3: {}", fileName);

            } catch (S3Exception e) {
                log.error("Erro ao enviar arquivo para o S3: {}", e.awsErrorDetails().errorMessage());
                throw new RuntimeException("Erro ao enviar arquivo para o S3:" + e.awsErrorDetails().errorMessage());
            }
        }
    }

    @Override
    public void sendMskMessage(MessageDTO message, String topicName) {
        try {
            var messageJson = objectMapper.writeValueAsString(message);
            ProducerRecord<String, String> record = new ProducerRecord<>(topicName, messageJson);

            kafkaProducer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    log.error("Erro ao enviar mensagem para o MSK: " + exception.getMessage());
                } else {
                    log.info("MSK - Mensagem enviada: " + new Gson().toJson(message) + " - Topic: " + metadata.topic());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Erro ao serializar e enviar mensagem para o MSK", e);
        }
    }
}
