package com.postech.infra.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.postech.application.usecases.ProcessUseCases;
import com.postech.infra.dto.response.ProcessResponseDTO;
import com.postech.infra.mappers.ProcessMapper;
import com.postech.infra.resource.ProcessResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProcessController implements ProcessResource {

    private final ProcessUseCases useCases;
    private final ProcessMapper mapper;

    @Override
    public ResponseEntity<ProcessResponseDTO> process(List<MultipartFile> files, String token) throws JsonProcessingException {
        var process = useCases.process(token, files);
        return ResponseEntity.ok().body(mapper.toProcessResponse(process));
    }

    @Override
    public ResponseEntity<ProcessResponseDTO> getProcess(String id, String token) {
        return ResponseEntity.ok().body(mapper.toProcessResponse(useCases.consult(id, token)));
    }

}