package com.omega.cashflow.controller.v1.movimentacao.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.omega.cashflow.entity.CaixaEntity;
import com.omega.cashflow.enumeration.TipoEnum;
import com.omega.cashflow.serializer.currency.CurrencyDeserializer;
import com.omega.cashflow.serializer.currency.CurrencySerializer;
import com.omega.cashflow.serializer.localDate.LocalDateDeserializer;
import com.omega.cashflow.serializer.localDate.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovimentacaoCreateOrUpdateDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String descricao;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate data;

  private CaixaEntity caixa;
  private TipoEnum tipo;

  @JsonSerialize(using = CurrencySerializer.class)
  @JsonDeserialize(using = CurrencyDeserializer.class)
  private Double valor;
}