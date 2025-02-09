//package com.postech.infra.client;
//
//import com.google.gson.Gson;
//import com.postech.domain.enums.EstadoPedidoEnum;
//import com.postech.infra.dto.client.pedido.ClienteResponseDTO;
//import com.postech.infra.dto.client.pedido.PedidoResponseDTO;
//import okhttp3.mockwebserver.MockResponse;
//import okhttp3.mockwebserver.MockWebServer;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//@ExtendWith(MockitoExtension.class)
//class ProcessClientImplTest {
//
//    @InjectMocks
//    private ProcessClientImpl pedidoClient;
//
//    private static MockWebServer mockWebServer;
//
//    @BeforeAll
//    static void setUp() throws IOException {
//        mockWebServer = new MockWebServer();
//        mockWebServer.start();
//    }
//
//    @BeforeEach
//    void init() {
//        pedidoClient = new ProcessClientImpl(String.format("http://localhost:%s", mockWebServer.getPort()));
//    }
//
//    @AfterAll
//    static void tearDown() throws IOException {
//        mockWebServer.shutdown();
//    }
//
//    @Test
//    public void testConsultarStatusPedido() {
//        var cliente = new ClienteResponseDTO(1L, "teste");
//        var pedidoResponseDTO = new PedidoResponseDTO(1L, cliente, EstadoPedidoEnum.RECEBIDO, null); // Supondo que esse é o construtor
//        mockWebServer.enqueue(new MockResponse()
//                .setBody(new Gson().toJson(pedidoResponseDTO))
//                .setResponseCode(200)
//                .addHeader("Content-Type", "application/json"));
//
//        var estado = pedidoClient.consultarStatusPedido(6L);
//        assertEquals(EstadoPedidoEnum.RECEBIDO, estado);
//    }
//
//    @Test
//    public void testConsultarStatusPedido_ComErro() {
//        mockWebServer.enqueue(new MockResponse()
//                .setResponseCode(500));
//
//        EstadoPedidoEnum estado = pedidoClient.consultarStatusPedido(1L);
//        assertNull(estado);
//    }
//
//    @Test
//    public void testAtualizarEstadoPorPedidoId() throws Exception {
//
//        mockWebServer.enqueue(new MockResponse()
//                .setResponseCode(200)
//                .addHeader("Content-Type", "application/json"));
//
//        pedidoClient.atualizarEstadoPorPedidoId(1L, EstadoPedidoEnum.PRONTO);
//        mockWebServer.takeRequest(10, TimeUnit.SECONDS);
//    }
//}
