package com.postech.infra.mappers;

import com.postech.domain.entities.Process;
import com.postech.infra.dto.response.ProcessResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProcessMapper {

    Process toProcess(ProcessResponseDTO processResponse);

    ProcessResponseDTO toProcessResponse(Process process);

}
