package com.postech.config;

import com.postech.infra.client.ProcessClientImpl;
import com.postech.infra.client.UserClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientsConfiguration {

    @Value("${backend-configs.client.process.url}")
    private String urlProcessClient;

    @Value("${backend-configs.client.user.url}")
    private String urlUserClient;

    @Bean
    ProcessClientImpl processClient() {
        return new ProcessClientImpl(urlProcessClient);
    }

    @Bean
    UserClientImpl userClient() {
        return new UserClientImpl(urlUserClient);
    }

}
