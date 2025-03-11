package com.omega.cashflow.controller.v1.caixa.dto;

import com.omega.cashflow.entity.CaixaEntity;

public record CaixaResponseDTO(
  Long id,
  String descricao,
  Double saldoInicial
) {

  public static CaixaResponseDTO toEntity(CaixaEntity cx) {
    return new CaixaResponseDTO(
      cx.getId(),
      cx.getDescricao(),
      cx.getSaldoInicial()
      );
  }
}