package com.postech.infra.handler;

import com.postech.domain.exceptions.ProcessException;
import com.postech.infra.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProcessAdvice {

    @ExceptionHandler(value = {ProcessException.class})
    ResponseEntity<Object> processExceptionHandler(ProcessException excecao) {
        return ResponseEntity.status(HttpStatus.valueOf(excecao.getError().getHttpStatusCode())).body(new ErrorDTO(excecao.getError().name(), excecao.getError().getDetail()));
    }

}
