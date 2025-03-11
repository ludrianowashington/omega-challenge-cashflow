package com.omega.cashflow.entity;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.omega.cashflow.controller.v1.caixa.dto.CaixaCreateOrUpdateDTO;
import com.omega.cashflow.serializer.currency.CurrencyDeserializer;
import com.omega.cashflow.serializer.currency.CurrencySerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "caixa")
public class CaixaEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "descricao", nullable = false)
  private String descricao;

  @Column(name = "saldoInicial", nullable = false)
  @JsonSerialize(using = CurrencySerializer.class)
  @JsonDeserialize(using = CurrencyDeserializer.class)
  private Double saldoInicial;

  public CaixaEntity(CaixaCreateOrUpdateDTO cx) {
    this.descricao = cx.getDescricao();
    this.saldoInicial = 0.0;
  }

  public void addSaldo(Double valor) {
    this.saldoInicial = Double.sum(this.saldoInicial, valor);
  }

  public void removeSaldo(Double valor) {
    validateAmountUnsuficient(valor);

    this.saldoInicial -= valor;
  }

  private void validateAmountUnsuficient(
    Double valor
  ) {
    if (this.saldoInicial.compareTo(valor) < 0) {
      throw new IllegalArgumentException("Saldo insuficiente no caixa para realizar a saída.");
    }
  }
}