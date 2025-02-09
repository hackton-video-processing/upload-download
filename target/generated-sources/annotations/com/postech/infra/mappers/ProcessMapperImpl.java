package com.postech.infra.mappers;

import com.postech.domain.dto.FileDTO;
import com.postech.domain.entities.Process;
import com.postech.infra.dto.response.ProcessResponseDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-08T22:19:49-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class ProcessMapperImpl implements ProcessMapper {

    @Override
    public Process toProcess(ProcessResponseDTO processResponse) {
        if ( processResponse == null ) {
            return null;
        }

        Process.ProcessBuilder process = Process.builder();

        process.id( processResponse.id() );
        process.status( processResponse.status() );
        List<FileDTO> list = processResponse.files();
        if ( list != null ) {
            process.files( new ArrayList<FileDTO>( list ) );
        }
        process.created_at( processResponse.created_at() );

        return process.build();
    }

    @Override
    public ProcessResponseDTO toProcessResponse(Process process) {
        if ( process == null ) {
            return null;
        }

        String id = null;
        List<FileDTO> files = null;
        String status = null;
        String created_at = null;

        id = process.getId();
        List<FileDTO> list = process.getFiles();
        if ( list != null ) {
            files = new ArrayList<FileDTO>( list );
        }
        status = process.getStatus();
        created_at = process.getCreated_at();

        ProcessResponseDTO processResponseDTO = new ProcessResponseDTO( id, files, status, created_at );

        return processResponseDTO;
    }
}
