package com.omega.cashflow.controller.v1.caixa.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CaixaCreateOrUpdateDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank(message = "A descrição não pode ser vazia")
  @NotNull(message = "A descrição não pode ser nula")
  private String descricao;

  @NotNull(message = "O saldo inicial não pode ser nulo")
  @PositiveOrZero(message = "O saldo inicial deve ser positivo ou zero")
  private Double saldoInicial;
}