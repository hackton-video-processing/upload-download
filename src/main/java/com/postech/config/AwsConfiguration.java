package com.postech.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.application.client.AwsClientGateway;
import com.postech.application.usecases.AwsS3UseCases;
import com.postech.application.usecases.MskS3UseCases;
import com.postech.infra.client.AwsS3ClientImpl;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.util.Properties;

@Configuration
public class AwsConfiguration {

    @Value("${backend-configs.aws.credentials.key.id}")
    private String accessKeyId;

    @Value("${backend-configs.aws.credentials.secret.key}")
    private String secretAccessKey;

    @Value("${backend-configs.aws.region}")
    private String region;

    @Value("${backend-configs.aws.msk.brokers}")
    private String mskBrokers;

    @Value("${backend-configs.aws.msk.topic-name}")
    private String topicName;

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(false).build())
                .region(Region.of(region))
                .build();
    }

    @Bean
    public Producer<String, String> createKafkaProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, mskBrokers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);

        return new KafkaProducer<>(props);
    }

    @Bean
    AwsS3UseCases awsS3UseCases(AwsClientGateway repositoryGateway) {
        return new AwsS3UseCases(repositoryGateway);
    }

    @Bean
    MskS3UseCases mskS3UseCases(AwsClientGateway repositoryGateway) {
        return new MskS3UseCases(repositoryGateway, topicName);
    }

    @Bean
    AwsS3ClientImpl awsS3Client(@Autowired ObjectMapper objectMapper) {
        return new AwsS3ClientImpl(s3Client(), createKafkaProducer(), objectMapper);
    }

}
