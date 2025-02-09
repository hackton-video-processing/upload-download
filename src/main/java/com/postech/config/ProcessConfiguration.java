package com.postech.config;

import com.postech.application.client.ProcessClient;
import com.postech.application.client.UserClient;
import com.postech.application.usecases.AwsS3UseCases;
import com.postech.application.usecases.MskS3UseCases;
import com.postech.application.usecases.ProcessUseCases;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessConfiguration {

    @Value("${backend-configs.aws.s3.bucket}")
    private String s3BucketName;

    @Value("${backend-configs.aws.s3.path-name}")
    private String s3PathName;

    @Value("${backend-configs.upload.video-max-file-size-mb}")
    private String videoMaxFileSizeMB;

    @Value("${backend-configs.upload.video-max-duration-seconds}")
    private String videoMaxDurationSeconds;

    @Bean
    ProcessUseCases processUseCases(AwsS3UseCases awsS3UseCases, MskS3UseCases mskUseCases, ProcessClient processClient, UserClient userClient) {
        return new ProcessUseCases(awsS3UseCases, mskUseCases, processClient, userClient, s3BucketName, s3PathName, Long.parseLong(videoMaxFileSizeMB), Long.parseLong(videoMaxDurationSeconds));
    }

}
