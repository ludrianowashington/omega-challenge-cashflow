package com.omega.cashflow.enumeration;

public enum TipoEnum {
  ENTRADA("ENTRADA"),
  SAIDA("SAIDA");

  private String displayName;

  TipoEnum(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}