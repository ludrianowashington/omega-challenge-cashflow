package com.omega.cashflow.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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

import com.omega.cashflow.controller.v1.caixa.dto.CaixaCreateOrUpdateDTO;
import com.omega.cashflow.entity.CaixaEntity;
import com.omega.cashflow.repository.CaixaRepository;
import com.omega.cashflow.service.caixa.CaixaServiceImpl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CaixaServiceTest {

    @Mock
    private CaixaRepository caixaRepository;

    @InjectMocks
    private CaixaServiceImpl caixaService;

    private CaixaCreateOrUpdateDTO dto;
    private CaixaEntity caixaEntity;

    @BeforeEach
    void setUp() {
        dto = new CaixaCreateOrUpdateDTO("Teste", 100.0);
        caixaEntity = new CaixaEntity(1L, "Teste", 100.0);
    }

    @Test
    void shouldCreateCaixa() {
        when(caixaRepository.findCaixaByDescricao(anyString())).thenReturn(Optional.empty());
        when(caixaRepository.save(any())).thenReturn(caixaEntity);

        var result = caixaService.saveCaixa(dto);

        assertNotNull(result);
        assertEquals(dto.getDescricao(), result.descricao());
        assertEquals(0.0, result.saldoInicial());
    }

    @Test
    void shouldThrowWhenDuplicateDescricao() {
        when(caixaRepository.findCaixaByDescricao(anyString())).thenReturn(Optional.of(caixaEntity));

        assertThrows(EntityExistsException.class, () -> caixaService.saveCaixa(dto));
    }

    @Test
    void shouldThrowWhenCaixaNotFound() {
        when(caixaRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> caixaService.findById(1L));
    }

    @Test
    void shouldUpdateCaixa() {
        var newDescricao = "Teste Atualizado";
        var updateDto = new CaixaCreateOrUpdateDTO(newDescricao, 200.0);

        when(caixaRepository.existsById(anyLong())).thenReturn(true);
        when(caixaRepository.findById(anyLong())).thenReturn(Optional.of(caixaEntity));
        when(caixaRepository.findCaixaByDescricao(anyString())).thenReturn(Optional.empty());
        when(caixaRepository.save(any())).thenReturn(new CaixaEntity(1L, newDescricao, 200.0));

        var result = caixaService.updateCaixa(1L, updateDto);

        assertNotNull(result);
        assertEquals(newDescricao, result.descricao());
    }

    @Test
    void shouldDeleteCaixa() {
        when(caixaRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> caixaService.deleteCaixa(1L));
    }

    @Test
    void shouldReturnPageOfCaixas() {
        var pageable = Pageable.unpaged();
        var caixaList = List.of(caixaEntity);
        var page = new PageImpl<>(caixaList);

        when(caixaRepository.findAll(pageable)).thenReturn(page);

        var result = caixaService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void shouldReturnCaixaById() {
        when(caixaRepository.existsById(anyLong())).thenReturn(true);
        when(caixaRepository.findById(anyLong())).thenReturn(Optional.of(caixaEntity));

        var result = caixaService.findById(1L);

        assertNotNull(result);
        assertEquals(caixaEntity.getDescricao(), result.getDescricao());
    }

    @Test
    void shouldThrowWhenUpdateWithExistingDescricao() {
        var updateDto = new CaixaCreateOrUpdateDTO("Outro Caixa", 200.0);
        var existingCaixa = new CaixaEntity(2L, "Outro Caixa", 200.0);

        when(caixaRepository.existsById(anyLong())).thenReturn(true);
        when(caixaRepository.findById(anyLong())).thenReturn(Optional.of(caixaEntity));
        when(caixaRepository.findCaixaByDescricao(anyString())).thenReturn(Optional.of(existingCaixa));

        assertThrows(EntityExistsException.class, () -> caixaService.updateCaixa(1L, updateDto));
    }

    @Test
    void shouldThrowWhenDeleteNonExistentCaixa() {
        when(caixaRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> caixaService.deleteCaixa(1L));
    }
}
