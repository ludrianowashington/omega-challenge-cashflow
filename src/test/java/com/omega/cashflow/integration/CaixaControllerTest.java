package com.omega.cashflow.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omega.cashflow.controller.v1.caixa.CaixaController;
import com.omega.cashflow.controller.v1.caixa.dto.CaixaCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.caixa.dto.CaixaResponseDTO;
import com.omega.cashflow.service.caixa.CaixaService;

@WebMvcTest(CaixaController.class)
class CaixaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CaixaService caixaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenCreateCaixa_thenReturnCreated() throws Exception {
        // Arrange
        var dto = new CaixaCreateOrUpdateDTO();
        dto.setDescricao("Caixa Teste");
        dto.setSaldoInicial(100.0);

        var responseDTO = new CaixaResponseDTO(1L, "Caixa Teste", 100.0);

        when(caixaService.saveCaixa(any(CaixaCreateOrUpdateDTO.class)))
            .thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(post("/v1/caixa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.descricao").value("Caixa Teste"))
            .andExpect(jsonPath("$.saldoInicial").value(100.0));
    }

    @Test
    void whenUpdateCaixa_thenReturnOk() throws Exception {
        // Arrange
        var id = 1L;
        var dto = new CaixaCreateOrUpdateDTO();
        dto.setDescricao("Caixa Atualizado");
        dto.setSaldoInicial(200.0);

        var responseDTO = new CaixaResponseDTO(id, "Caixa Atualizado", 200.0);

        when(caixaService.updateCaixa(any(Long.class), any(CaixaCreateOrUpdateDTO.class)))
            .thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(put("/v1/caixa/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.descricao").value("Caixa Atualizado"));
    }

    @Test
    void whenFindAll_thenReturnPageOfCaixas() throws Exception {
        // Arrange
        var responseDTO = new CaixaResponseDTO(1L, "Caixa Teste", 100.0);
        var page = new PageImpl<>(List.of(responseDTO));

        when(caixaService.findAll(any())).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/v1/caixa"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void whenDeleteCaixa_thenReturnNoContent() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/v1/caixa/{id}", 1L))
            .andExpect(status().isNoContent());
    }
}