package com.omega.cashflow.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoCreateOrUpdateDTO;
import com.omega.cashflow.entity.CaixaEntity;
import com.omega.cashflow.entity.MovimentacaoEntity;
import com.omega.cashflow.enumeration.TipoEnum;
import com.omega.cashflow.repository.CaixaRepository;
import com.omega.cashflow.repository.MovimentacaoRepository;
import com.omega.cashflow.service.caixa.CaixaService;
import com.omega.cashflow.service.movimentacao.MovimentacaoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MovimentacaoServiceTest {

    @Mock
    private MovimentacaoRepository movimentacaoRepository;

    @Mock
    private CaixaRepository caixaRepository;

    @Mock
    private CaixaService caixaService;

    @InjectMocks
    private MovimentacaoServiceImpl movimentacaoService;

    private MovimentacaoCreateOrUpdateDTO dto;
    private CaixaEntity caixa;
    private MovimentacaoEntity movimentacao;

    @BeforeEach
    void setUp() {
        caixa = new CaixaEntity(1L, "Caixa Teste", 1000.0);
        dto = new MovimentacaoCreateOrUpdateDTO(
            "Teste",
            LocalDate.now(),
            caixa,
            TipoEnum.ENTRADA,
            100.0
        );
        movimentacao = new MovimentacaoEntity(dto);
        movimentacao.setId(1L);
    }

    @Test
    void shouldCreateEntradaMovimentacao() {
        when(caixaService.findById(anyLong())).thenReturn(caixa);
        when(movimentacaoRepository.save(any())).thenReturn(movimentacao);
        when(caixaRepository.save(any())).thenReturn(caixa);

        var result = movimentacaoService.saveMovimentacao(dto);

        assertNotNull(result);
        assertEquals(dto.getValor(), result.valor());
        assertEquals(dto.getTipo(), result.tipo());
    }

    @Test
    void shouldCreateSaidaMovimentacao() {
        dto = new MovimentacaoCreateOrUpdateDTO(
            "Teste",
            LocalDate.now(),
            caixa,
            TipoEnum.SAIDA,
            100.0
        );
        movimentacao = new MovimentacaoEntity(dto);

        when(caixaService.findById(anyLong())).thenReturn(caixa);
        when(movimentacaoRepository.save(any())).thenReturn(movimentacao);
        when(caixaRepository.save(any())).thenReturn(caixa);

        var result = movimentacaoService.saveMovimentacao(dto);

        assertNotNull(result);
        assertEquals(dto.getValor(), result.valor());
        assertEquals(dto.getTipo(), result.tipo());
    }

    @Test
    void shouldFindById() {
        when(movimentacaoRepository.findById(anyLong())).thenReturn(Optional.of(movimentacao));

        var result = movimentacaoService.findById(1L);

        assertNotNull(result);
        assertEquals(movimentacao.getValor(), result.valor());
    }

    @Test
    void shouldReturnPageOfMovimentacoes() {
        var pageable = Pageable.unpaged();
        var movimentacaoList = List.of(movimentacao);
        var page = new PageImpl<>(movimentacaoList);

        when(movimentacaoRepository.findAll(pageable)).thenReturn(page);

        var result = movimentacaoService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }
}
