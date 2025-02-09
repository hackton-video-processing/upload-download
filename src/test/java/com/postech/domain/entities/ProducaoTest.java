//package com.postech.domain.entities;
//
//import com.postech.domain.enums.EstadoProducaoEnum;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//class ProducaoTest {
//
//    private Producao producao;
//
//    @BeforeEach
//    void setUp() {
//        // Inicializa a entidade Producao antes de cada teste
//        producao = new Producao(123L, LocalDateTime.of(2024, 11, 27, 12, 0));
//    }
//
//    @Test
//    void testCriacaoProducao() {
//        // Verifica se a criação da Producao foi bem-sucedida
//        assertNotNull(producao);
//        assertEquals(123L, producao.getIdPedido());
//        assertEquals(EstadoProducaoEnum.PREPARANDO, producao.getStatus());
//        assertEquals(LocalDateTime.of(2024, 11, 27, 12, 0), producao.getInicioPreparo());
//    }
//
//    @Test
//    void testStatusInicial() {
//        // Verifica se o status inicial é "PREPARANDO"
//        assertEquals(EstadoProducaoEnum.PREPARANDO, producao.getStatus());
//    }
//
//    @Test
//    void testSetFimPreparo() {
//        // Define a data e hora do fim do preparo
//        LocalDateTime fimPreparo = LocalDateTime.of(2024, 11, 27, 14, 30);
//        producao.setFimPreparo(fimPreparo);
//
//        // Verifica se o fim do preparo foi corretamente definido
//        assertEquals(fimPreparo, producao.getFimPreparo());
//    }
//
//    @Test
//    void testSetParameters() {
//        // Define a data e hora do fim do preparo
//        var dateTime = LocalDateTime.of(2024, 11, 27, 14, 30);
//        producao.setId(1L);
//        producao.setIdPedido(1L);
//        producao.setInicioPreparo(dateTime);
//
//        // Verifica se o fim do preparo foi corretamente definido
//        assertEquals(1L, producao.getId());
//        assertEquals(1L, producao.getIdPedido());
//        assertEquals(dateTime, producao.getInicioPreparo());
//    }
//
//    @Test
//    void testCalculaTempoTotalPreparo() {
//        // Define o fim do preparo e calcula o tempo total
//        LocalDateTime inicioPreparo = producao.getInicioPreparo();
//        LocalDateTime fimPreparo = inicioPreparo.plusHours(2).plusMinutes(30);
//        producao.setFimPreparo(fimPreparo);
//
//        // Calcula o tempo total de preparo e define o valor
//        LocalTime tempoTotal = LocalTime.of(2, 30);
//        producao.setTempoTotalPreparo(tempoTotal);
//
//        // Verifica se o tempo total de preparo está correto
//        assertEquals(tempoTotal, producao.getTempoTotalPreparo());
//    }
//
//    @Test
//    void testAlterarStatus() {
//        // Altera o status da produção
//        producao.setStatus(EstadoProducaoEnum.RECEBIDO);
//
//        // Verifica se o status foi alterado corretamente
//        assertEquals(EstadoProducaoEnum.RECEBIDO, producao.getStatus());
//    }
//}
