package com.omega.cashflow.service.movimentacao;

import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoResponseDTO;

public interface MovimentacaoService {
    MovimentacaoResponseDTO saveMovimento(MovimentacaoCreateOrUpdateDTO dto);

    // MovimentacaoResponseDTO update(Long id, MovimentacaoCreateOrUpdateDTO dto);

    // void delete(Long id);

    // MovimentacaoResponseDTO findById(Long id);

    // List<MovimentacaoResponseDTO> findAll();

    // List<MovimentacaoResponseDTO> search(
    //     TipoEnum tipo,
    //     Double valorMinimo,
    //     Double valorMaximo,
    //     String dataInicio,
    //     String dataFim,
    //     Long caixaId
    // );
}