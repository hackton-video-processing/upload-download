package com.postech.infra.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserClientImplTest {

    private UserClientImpl userClient;
    private WebClient.Builder webClientBuilder;
    private WebClient webClient;
    private RequestBodyUriSpec requestBodyUriSpec;
    private RequestHeadersSpec<?> requestHeadersSpec;
    private ResponseSpec responseSpec;

    private final String urlUserClient = "http://mock-url";

    @BeforeEach
    void setUp() {
        webClientBuilder = mock(WebClient.Builder.class);
        webClient = mock(WebClient.class);
        requestBodyUriSpec = mock(RequestBodyUriSpec.class);
        requestHeadersSpec = mock(RequestHeadersSpec.class);
        responseSpec = mock(ResponseSpec.class);

        when(webClientBuilder.baseUrl(urlUserClient)).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri("/validate")).thenReturn(requestBodyUriSpec);
//        when(requestBodyUriSpec.body(Mockito.any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

//    @Test
//    void shouldReturnTrueWhenTokenIsValid() {
//        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("Valid"));
//
//        userClient = new UserClientImpl(urlUserClient);
//        Boolean result = userClient.validateToken("valid-token");
//
//        assertTrue(result);
//    }

    @Test
    void shouldReturnFalseWhenTokenIsInvalid() {
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Invalid token")));

        userClient = new UserClientImpl(urlUserClient);
        Boolean result = userClient.validateToken("invalid-token");

        assertFalse(result);
    }
}

