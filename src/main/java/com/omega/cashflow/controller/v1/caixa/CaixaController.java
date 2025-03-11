package com.omega.cashflow.controller.v1.caixa;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.omega.cashflow.controller.v1.caixa.dto.CaixaCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.caixa.dto.CaixaResponseDTO;
import com.omega.cashflow.service.caixa.CaixaService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CaixaController implements CaixaAPI {

  private final CaixaService caixaService;

  @Override
  public ResponseEntity<CaixaResponseDTO> criarCaixa(@Valid @RequestBody CaixaCreateOrUpdateDTO dto) {

    final var response = caixaService.criarCaixa(dto);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
