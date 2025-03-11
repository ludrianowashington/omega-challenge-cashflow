package com.omega.cashflow.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omega.cashflow.controller.v1.caixa.dto.CaixaCreateOrUpdateDTO;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CaixaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCaixa() throws Exception {
        var dto = new CaixaCreateOrUpdateDTO("Teste", 100.0);

        mockMvc.perform(post("/v1/caixa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao").value(dto.getDescricao()));
    }

    @Test
    void shouldRejectInvalidCaixa() throws Exception {
        var dto = new CaixaCreateOrUpdateDTO("", -100.0);

        mockMvc.perform(post("/v1/caixa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
