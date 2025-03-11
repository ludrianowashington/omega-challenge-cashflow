package com.omega.cashflow.service.caixa;

import com.omega.cashflow.controller.v1.caixa.dto.CaixaCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.caixa.dto.CaixaResponseDTO;

public interface CaixaService {
  CaixaResponseDTO criarCaixa(CaixaCreateOrUpdateDTO caixa);
}
