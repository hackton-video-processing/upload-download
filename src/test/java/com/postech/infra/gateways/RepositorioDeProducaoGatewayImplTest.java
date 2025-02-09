//package com.postech.infra.gateways;
//
//import com.postech.domain.entities.Producao;
//import com.postech.infra.mappers.ProducaoMapper;
//import com.postech.infra.persistence.entities.ProducaoEntity;
//import com.postech.infra.persistence.repositories.ProducaoRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class RepositorioDeProducaoGatewayImplTest {
//
//    @Mock
//    private ProducaoRepository repository;
//
//    @Mock
//    private ProducaoMapper mapper;
//
//    @InjectMocks
//    private RepositorioDeProducaoGatewayImpl gateway;
//
//    private Producao producao;
//    private ProducaoEntity producaoEntity;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        producao = new Producao(123L, LocalDateTime.of(2024, 11, 27, 12, 0));
//        producaoEntity = new ProducaoEntity();
//        producaoEntity.setIdPedido(123L);
//        producaoEntity.setInicioPreparo(LocalDateTime.of(2024, 11, 27, 12, 0));
//    }
//
//    @Test
//    void testSalvar() {
//        when(mapper.paraEntidade(producao)).thenReturn(producaoEntity);
//        when(repository.save(producaoEntity)).thenReturn(producaoEntity);
//        when(mapper.paraDominio(producaoEntity)).thenReturn(producao);
//
//        Producao resultado = gateway.salvar(producao);
//
//        assertNotNull(resultado);
//        assertEquals(123L, resultado.getIdPedido());
//
//        verify(mapper).paraEntidade(producao);
//        verify(repository).save(producaoEntity);
//        verify(mapper).paraDominio(producaoEntity);
//    }
//
//    @Test
//    void testBuscarPorPedidoIdEncontrado() {
//        when(repository.getProducaoEntityByIdPedido(123L)).thenReturn(Optional.of(producaoEntity));
//        when(mapper.paraDominio(producaoEntity)).thenReturn(producao);
//
//        Producao resultado = gateway.buscarPorPedidoId(123L);
//
//        assertNotNull(resultado);
//        assertEquals(123L, resultado.getIdPedido());
//
//        verify(repository).getProducaoEntityByIdPedido(123L);
//        verify(mapper).paraDominio(producaoEntity);
//    }
//
//    @Test
//    void testBuscarPorPedidoIdNaoEncontrado() {
//        when(repository.getProducaoEntityByIdPedido(123L)).thenReturn(Optional.empty());
//
//        Producao resultado = gateway.buscarPorPedidoId(123L);
//
//        assertNull(resultado);
//
//        verify(repository).getProducaoEntityByIdPedido(123L);
//        verify(mapper, never()).paraDominio(any());
//    }
//}
