package com.postech;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Pós Tech - Arquitetura Hexagonal - Serviço de upload e download",
                version = "1.0",
                description = "Aplicação responsável por fazer upload, enviar arquivos para o processamento e permitir a consulta/download dos arquivos processados."
        )
)
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
