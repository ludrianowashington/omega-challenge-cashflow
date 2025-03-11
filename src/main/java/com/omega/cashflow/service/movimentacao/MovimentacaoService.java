package com.omega.cashflow.service.movimentacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoResponseDTO;
import com.omega.cashflow.enumeration.TipoEnum;

public interface MovimentacaoService {
    MovimentacaoResponseDTO saveMovimentacao(MovimentacaoCreateOrUpdateDTO dto);

    MovimentacaoResponseDTO updateMovimentacao(Long id, MovimentacaoCreateOrUpdateDTO dto);

    void deleteMovimentacao(Long id);

    MovimentacaoResponseDTO findById(Long id);

    Page<MovimentacaoResponseDTO> findAll(Pageable pageable);

    Page<MovimentacaoResponseDTO> search(
        TipoEnum tipo,
        Double valorMinimo,
        Double valorMaximo,
        String dataInicio,
        String dataFim,
        Long caixaId,
        Pageable pageable
    );
}