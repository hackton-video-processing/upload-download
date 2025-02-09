package com.postech.infra.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.postech.infra.dto.ErrorDTO;
import com.postech.infra.dto.response.ProcessResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/process")
@Tag(name = "Process", description = "Recursos relacionados ao processamento de um vídeo")
public interface ProcessResource {

    @Operation(summary = "Enviar video(s) para processamento", method = "POST", description = "Recurso para enviar e processar um video")
    @ApiResponses(value = {
            @ApiResponse(description = "Processo inicializado com sucesso", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProcessResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro no processamento do video", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProcessResponseDTO> process(@RequestParam(value = "files", required = false) List<MultipartFile> files, @RequestHeader(value = "token") String token) throws JsonProcessingException;

    @Operation(summary = "Consultar processamento", method = "GET", description = "Recurso para consultar processamento do video")
    @ApiResponses(value = {
            @ApiResponse(description = "Processo encontrado", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProcessResponseDTO.class))),
            @ApiResponse(description = "Processo não foi encontrado", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProcessResponseDTO> getProcess(@PathVariable String id, @RequestHeader(value = "token") String token);

}