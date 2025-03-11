package com.omega.cashflow.service.caixa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.omega.cashflow.controller.v1.caixa.dto.CaixaCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.caixa.dto.CaixaResponseDTO;
import com.omega.cashflow.entity.CaixaEntity;

public interface CaixaService {
  CaixaResponseDTO saveCaixa(CaixaCreateOrUpdateDTO caixa);

  CaixaResponseDTO updateCaixa(Long id, CaixaCreateOrUpdateDTO caixa);

  CaixaEntity findById(Long id);

  void deleteCaixa(Long id);

  Page<CaixaResponseDTO> findAll(Pageable pageable);
}
