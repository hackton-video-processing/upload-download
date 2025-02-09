package com.postech.domain.exceptions;

import com.postech.domain.enums.ProcessErrorEnum;
import lombok.Getter;

@Getter
public class ProcessException extends RuntimeException {

    private final ProcessErrorEnum error;

    public ProcessException(ProcessErrorEnum error) {
        this.error = error;
    }

}
