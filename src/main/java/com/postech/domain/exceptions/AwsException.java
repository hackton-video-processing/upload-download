package com.postech.domain.exceptions;

import com.postech.domain.enums.AwsErrorEnum;
import lombok.Getter;

@Getter
public class AwsException extends RuntimeException {

    private final AwsErrorEnum error;

    public AwsException(AwsErrorEnum error) {
        this.error = error;
    }

}
