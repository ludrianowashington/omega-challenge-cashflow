package com.omega.cashflow.entity;

import java.io.Serializable;

import com.omega.cashflow.controller.v1.caixa.dto.CaixaCreateOrUpdateDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
  private Double saldoInicial;

  public CaixaEntity(CaixaCreateOrUpdateDTO cx) {
    this.descricao = cx.getDescricao();
    this.saldoInicial = cx.getSaldoInicial();
  }
}