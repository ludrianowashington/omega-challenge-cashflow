package com.omega.cashflow.controller.v1.caixa.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CaixaCreateOrUpdateDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String descricao;
  private Double saldoInicial;
}