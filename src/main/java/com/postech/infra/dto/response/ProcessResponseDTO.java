package com.postech.infra.dto.response;

import com.postech.domain.dto.FileDTO;

import java.util.List;

public record ProcessResponseDTO(
        String id,
        List<FileDTO> files,
        String status,
        String created_at
) {
}
