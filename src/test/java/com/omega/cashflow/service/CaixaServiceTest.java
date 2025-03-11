package com.omega.cashflow.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.omega.cashflow.controller.v1.caixa.dto.CaixaCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.caixa.dto.CaixaResponseDTO;
import com.omega.cashflow.entity.CaixaEntity;
import com.omega.cashflow.repository.CaixaRepository;
import com.omega.cashflow.service.caixa.CaixaServiceImpl;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class CaixaServiceTest {

  @Mock
  private CaixaRepository caixaRepository;

  @InjectMocks
  private CaixaServiceImpl caixaService;

  @Captor
  private ArgumentCaptor<CaixaEntity> caixaArgumentCaptor;

  @Captor
  private ArgumentCaptor<Long> idArgumentCaptor;

  @Nested
  class createCaixaTest {

    @Test
    @DisplayName("Deve criar Caixa com sucesso")
    void deveCriarCaixaERetornarRespostaDto() {
      // Arrange
      CaixaCreateOrUpdateDTO criarCaixaDto = new CaixaCreateOrUpdateDTO();
      criarCaixaDto.setDescricao("Banco Novo");
      CaixaEntity caixaEsperada = new CaixaEntity(criarCaixaDto);
      doReturn(caixaEsperada).when(caixaRepository).save(caixaArgumentCaptor.capture());

      // Act
      CaixaResponseDTO resposta = caixaService.saveCaixa(criarCaixaDto);
      assertNotNull(resposta);
      // Assert
      var caixaCaptured = caixaArgumentCaptor.getValue();

      assertEquals(criarCaixaDto.getDescricao(), caixaCaptured.getDescricao());
      verify(caixaRepository, times(1)).save(any(CaixaEntity.class));
    }

    @Test
    @DisplayName("Jogar exceção se o erro acontecer ao criar Caixa")
    void jogarExcecaoDeErroAoCriarCaixa() {
      CaixaCreateOrUpdateDTO criarCaixaDto = new CaixaCreateOrUpdateDTO();
      criarCaixaDto.setDescricao("Novo Banco");

      doThrow(new RuntimeException()).when(caixaRepository).save(any(CaixaEntity.class));

      assertThrows(RuntimeException.class, () -> caixaService.saveCaixa(criarCaixaDto));
    }

  }

  @Nested
  class listCaixas {


    @Test
    void listarCaixasDeveRetornarListaVaziaQuandoNaoExistemCaixas() {

      doReturn(new PageImpl<>(Collections.emptyList())).when(caixaRepository).findAll(any(Pageable.class));

      var resultado = caixaService.findAll(Pageable.unpaged());

      assertNotNull(resultado);
      assertTrue(resultado.isEmpty());
      verify(caixaRepository, times(1)).findAll(any(Pageable.class));
    }

  }

  @Nested
  class deleteById {

    @DisplayName("Deletar Caixa com Sucesso")
    @Test
    void deletarCaixaComSucesso() {
      Long id = 1L;
      doReturn(true)
        .when(caixaRepository)
        .existsById(idArgumentCaptor.capture());

      doNothing()
        .when(caixaRepository)
        .deleteById(idArgumentCaptor.capture());

      caixaService.deleteCaixa(id);

      var idList = idArgumentCaptor.getAllValues();
      assertEquals(1L, idList.get(0));
      assertEquals(1L, idList.get(1));

      verify(caixaRepository, times(1)).existsById(idList.get(0));
      verify(caixaRepository, times(1)).deleteById(idList.get(1));
    }

    @Test
    @DisplayName("Lançar ResourceNotFoundException quando o Caixa não existe")
    void deletarCaixaComFalha() {
      Long id = 1L;
      doReturn(false).when(caixaRepository).existsById(id);


      EntityNotFoundException exception = assertThrows(
        EntityNotFoundException.class,
        () -> caixaService.deleteCaixa(id)
      );


      assertEquals("Caixa não encontrado com id: " + id, exception.getMessage());


      verify(caixaRepository, times(1))
        .existsById(id);

      verify(caixaRepository, times(0)).deleteById(any());
    }

  }

}
