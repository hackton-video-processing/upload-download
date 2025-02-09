//package com.postech.infra.handler;
//
//import com.postech.domain.enums.AwsErrorEnum;
//import com.postech.domain.enums.ErroProducaoEnum;
//import com.postech.domain.exceptions.AwsException;
//import com.postech.domain.exceptions.ProcessException;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/producao")
//public class ProducaoControllerMock {
//
//    @GetMapping("/test-error-producao")
//    public void throwErrorProducao() {
//        throw new ProcessException(ErroProducaoEnum.NOT_FOUND);
//    }
//
//    @GetMapping("/test-error-pedido")
//    public void throwErrorPedido() {
//        throw new AwsException(AwsErrorEnum.ESTADO_INVALIDO);
//    }
//}
