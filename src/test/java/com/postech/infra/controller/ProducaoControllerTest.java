//package com.postech.infra.controller;
//
//import com.postech.application.usecases.ProcessUseCases;
//import com.postech.domain.entities.Producao;
//import com.postech.domain.enums.EstadoProducaoEnum;
//import com.postech.infra.dto.response.FinalizarPreparoResponseDTO;
//import com.postech.infra.dto.response.IniciarPreparoResponseDTO;
//import com.postech.infra.mappers.ProducaoMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class ProducaoControllerTest {
//
//    @InjectMocks
//    private ProducaoController controller;
//
//    @Mock
//    private ProcessUseCases useCases;
//
//    @Mock
//    private ProducaoMapper mapper;
//
//    ProducaoControllerTest() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void iniciarPreparoTest() {
//        Long id = 1L;
//        Producao mockProducao = new Producao(id, null); // Producao mockada
//
//        // Mock do caso de uso
//        when(useCases.iniciarProducao(id)).thenReturn(mockProducao);
//
//        // Mock do mapeamento
//        var mockDTO = new IniciarPreparoResponseDTO(1L, 1L, EstadoProducaoEnum.PREPARANDO, LocalDateTime.now());
//        when(mapper.paraIniciarPreparoDTO(mockProducao)).thenReturn(mockDTO);
//
//        // Executar o método
//        ResponseEntity<Object> response = controller.iniciarPreparo(id);
//
//        // Verificações
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(mockDTO, response.getBody());
//
//        // Verifica se os mocks foram chamados
//        verify(useCases, times(1)).iniciarProducao(id);
//        verify(mapper, times(1)).paraIniciarPreparoDTO(mockProducao);
//    }
//
//    @Test
//    void finalizarPreparoTest() {
//        Long id = 1L;
//        Producao mockProducao = new Producao(id, null); // Producao mockada
//
//        // Mock do caso de uso
//        when(useCases.finalizarProducao(id)).thenReturn(mockProducao);
//
//        // Mock do mapeamento
//        var mockDTO = new FinalizarPreparoResponseDTO(1L, 1L, EstadoProducaoEnum.PRONTO, LocalDateTime.now(), LocalDateTime.now(), LocalTime.now());
//        when(mapper.paraFinalizarPreparoDTO(mockProducao)).thenReturn(mockDTO);
//
//        // Executar o método
//        ResponseEntity<Object> response = controller.finalizarPreparo(id);
//
//        // Verificações
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(mockDTO, response.getBody());
//
//        // Verifica se os mocks foram chamados
//        verify(useCases, times(1)).finalizarProducao(id);
//        verify(mapper, times(1)).paraFinalizarPreparoDTO(mockProducao);
//    }
//}
