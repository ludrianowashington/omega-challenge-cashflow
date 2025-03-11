package com.omega.cashflow.controller.v1.movimentacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoResponseDTO;
import com.omega.cashflow.service.movimentacao.MovimentacaoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class MovimentacaoController implements MovimentacaoAPI {

  private final MovimentacaoService movimentacaoService;

  @Override
  public ResponseEntity<MovimentacaoResponseDTO> create(@Valid MovimentacaoCreateOrUpdateDTO dto) {
    final var response = movimentacaoService.saveMovimento(dto);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }



}