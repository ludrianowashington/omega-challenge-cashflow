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

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovimentacaoCreateOrUpdateDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank(message = "A descrição é obrigatória")
  @Size(min = 3, max = 255, message = "A descrição deve ter entre 3 e 255 caracteres")
  private String descricao;

  @NotNull(message = "A data é obrigatória")
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate data;

  @NotNull(message = "O caixa é obrigatório")
  @Valid
  private CaixaEntity caixa;

  @NotNull(message = "O tipo é obrigatório")
  private TipoEnum tipo;

  @NotNull(message = "O valor é obrigatório")
  @Positive(message = "O valor deve ser maior que zero")
  @JsonSerialize(using = CurrencySerializer.class)
  @JsonDeserialize(using = CurrencyDeserializer.class)
  private Double valor;
}