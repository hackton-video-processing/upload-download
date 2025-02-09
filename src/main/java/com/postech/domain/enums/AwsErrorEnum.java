package com.postech.domain.enums;

import lombok.Getter;

@Getter
public enum AwsErrorEnum {
    S3_ERROR_SAVING(400, "Erro ao salvar arquivo"),
    MSK_ERROR_SENDING_MESSAGE(400, "Erro ao enviar para o kafka"),
    S3_FILE_CONVERT_ERROR(400, "Erro ao tentar converter arquivo");

    private final Integer httpStatusCode;
    private final String detail;

    AwsErrorEnum(Integer httpStatusCode, String detail) {
        this.httpStatusCode = httpStatusCode;
        this.detail = detail;
    }
}
