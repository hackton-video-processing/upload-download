//package com.postech.infra.handler;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = {ProducaoAdvice.class, ProducaoControllerMock.class})
//class ProducaoAdviceTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void testProducaoExceptionHandler() throws Exception {
//        mockMvc.perform(get("/producao/test-error-producao")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//    }
//}