package com.omega.cashflow.controller.v1.movimentacao.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.omega.cashflow.controller.v1.caixa.dto.CaixaResponseDTO;
import com.omega.cashflow.entity.MovimentacaoEntity;
import com.omega.cashflow.enumeration.TipoEnum;
import com.omega.cashflow.serializer.currency.CurrencySerializer;
import com.omega.cashflow.serializer.localDate.LocalDateSerializer;

public record MovimentacaoResponseDTO(
  Long id,
  @JsonSerialize(using = LocalDateSerializer.class)
  LocalDate data,
  TipoEnum tipo,
  CaixaResponseDTO caixa,
  String descricao,
  @JsonSerialize(using = CurrencySerializer.class)
  Double valor
) {
  public static MovimentacaoResponseDTO toEntity(MovimentacaoEntity mv) {
    return new MovimentacaoResponseDTO(
      mv.getId(),
      mv.getData(),
      mv.getTipo(),
      CaixaResponseDTO.toEntity(mv.getCaixa()),
      mv.getDescricao(),
      mv.getValor()
    );
  }
}