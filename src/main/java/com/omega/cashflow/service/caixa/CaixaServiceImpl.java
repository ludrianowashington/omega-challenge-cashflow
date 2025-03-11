package com.omega.cashflow.service.caixa;

import org.springframework.stereotype.Service;

import com.omega.cashflow.controller.v1.caixa.dto.CaixaCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.caixa.dto.CaixaResponseDTO;
import com.omega.cashflow.entity.CaixaEntity;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CaixaServiceImpl implements CaixaService {

  @Override
  public CaixaResponseDTO criarCaixa(CaixaCreateOrUpdateDTO caixa) {

    CaixaEntity novoCaixa = new CaixaEntity(caixa);

    return CaixaResponseDTO.toEntity(novoCaixa);
  }
}
