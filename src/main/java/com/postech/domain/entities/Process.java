package com.postech.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.postech.domain.dto.FileDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Process {

    private String id;
    private String status;
    private List<FileDTO> files;
    private String created_at;

}
