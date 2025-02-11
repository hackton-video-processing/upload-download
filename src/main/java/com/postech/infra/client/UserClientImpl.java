package com.postech.infra.client;

import com.postech.application.client.UserClient;
import com.postech.infra.dto.request.TokenRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class UserClientImpl implements UserClient {

    private final String urlUserClient;

    @Override
    public Boolean validateToken(String token) {
        return WebClient.builder()
                .baseUrl(urlUserClient)
                .build()
                .post()
                .uri("/validate")
                .body(BodyInserters.fromValue(TokenRequestDTO.builder().token(token).build()))
                .retrieve()
                .bodyToMono(String.class)
                .thenReturn(true)
                .onErrorReturn(false)
                .block();
    }

}
