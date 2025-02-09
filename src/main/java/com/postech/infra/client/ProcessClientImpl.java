package com.postech.infra.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.application.client.ProcessClient;
import com.postech.domain.entities.Process;
import com.postech.domain.enums.ProcessErrorEnum;
import com.postech.domain.exceptions.ProcessException;
import com.postech.infra.dto.request.ProcessRequestDTO;
import com.postech.infra.dto.request.ProcessResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
public class ProcessClientImpl implements ProcessClient {

    private final String urlProcessClient;

    @Override
    public Process createProcess(ArrayList<String> fileNames) {
        var response =  WebClient.builder()
                .baseUrl(urlProcessClient).build()
                .post()
                .uri("/process")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(ProcessRequestDTO.builder().files(fileNames).build()))
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    throw new ProcessException(ProcessErrorEnum.ERROR_CLIENT_PROCESS);
                })
                .block();

        var objectMapper = new ObjectMapper();
        ProcessResponseDTO responseMapped;
        try {
            responseMapped = objectMapper.readValue(response, ProcessResponseDTO.class);
        } catch (JsonProcessingException e) {
            throw new ProcessException(ProcessErrorEnum.ERROR_CLIENT_PROCESS);
        }

        return Process.builder().created_at(LocalDateTime.now().toString())
                .status("REQUESTED")
                .id(responseMapped.getId())
                .build();
    }

    @Override
    public Optional<Process> consultProcess(String processId) {
        return WebClient.builder()
                .baseUrl(urlProcessClient).build()
                .get()
                .uri("/process/{processId}", processId)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> Mono.empty())
                .bodyToMono(Process.class)
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .block();
    }

}
