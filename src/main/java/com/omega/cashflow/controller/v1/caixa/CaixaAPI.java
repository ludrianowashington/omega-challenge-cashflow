package com.omega.cashflow.controller.v1.caixa;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.omega.cashflow.controller.v1.caixa.dto.CaixaCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.caixa.dto.CaixaResponseDTO;

import jakarta.validation.Valid;

@RequestMapping("/v1/caixa")
public interface CaixaAPI {

  @PostMapping
  public ResponseEntity<CaixaResponseDTO> criarCaixa(@Valid @RequestBody CaixaCreateOrUpdateDTO dto);
}
