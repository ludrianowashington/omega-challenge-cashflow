package com.omega.cashflow.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omega.cashflow.controller.v1.caixa.dto.CaixaCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoCreateOrUpdateDTO;
import com.omega.cashflow.entity.CaixaEntity;
import com.omega.cashflow.enumeration.TipoEnum;
import com.omega.cashflow.repository.CaixaRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MovimentacaoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CaixaRepository caixaRepository;

    @Test
    void shouldCreateMovimentacaoEntrada() throws Exception {
        var caixaDto = new CaixaCreateOrUpdateDTO("Caixa Teste", 1000.0);
        var caixa = new CaixaEntity(caixaDto);
        caixa = caixaRepository.save(caixa);

        var dto = new MovimentacaoCreateOrUpdateDTO(
            "Teste Movimentação",
            LocalDate.now(),
            caixa,
            TipoEnum.ENTRADA,
            100.0
        );

        mockMvc.perform(post("/v1/movimentacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao").value(dto.getDescricao()))
                .andExpect(jsonPath("$.valor").exists())
                .andExpect(jsonPath("$.tipo").value(dto.getTipo().toString()));
    }

    @Test
    void shouldRejectInvalidMovimentacao() throws Exception {
        var caixaDto = new CaixaCreateOrUpdateDTO("Caixa Teste", 1000.0);
        var caixa = new CaixaEntity(caixaDto);
        caixa = caixaRepository.save(caixa);

        var dto = new MovimentacaoCreateOrUpdateDTO(
            "",
            null,
            caixa,
            null,
            -100.0
        );

        mockMvc.perform(post("/v1/movimentacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
