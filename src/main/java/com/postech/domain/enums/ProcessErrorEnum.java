package com.postech.domain.enums;

import lombok.Getter;

@Getter
public enum ProcessErrorEnum {
    NOT_FOUND(400, "Processo não encontrado"),
    ERROR_CLIENT_PROCESS(400, "Erro na integração com a api de processamento"),
    INVALID_TOKEN(400, "Token inválido ou expirado"),
    INVALID_FILE_SIZE(400, "Arquivo maior do que o tamanho permitido de 100M"),
    INVALID_FILE_TYPE(400, "Tipo de arquivo inválido, somente videos são aceitos"),
    INVALID_FILE_DURATION(400, "Duração maior do que a permitido"),
    INVALID_QUANTITY_VIDEOS(400, "É permitido o envio de no mínimo 1 video e no máximo 3 por processamento");

    private final Integer httpStatusCode;
    private final String detail;

    ProcessErrorEnum(Integer httpStatusCode, String detail) {
        this.httpStatusCode = httpStatusCode;
        this.detail = detail;
    }
}
