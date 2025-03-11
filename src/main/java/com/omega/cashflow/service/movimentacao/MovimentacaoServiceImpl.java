package com.omega.cashflow.service.movimentacao;

import org.springframework.stereotype.Service;

import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoResponseDTO;
import com.omega.cashflow.entity.MovimentacaoEntity;
import com.omega.cashflow.repository.CaixaRepository;
import com.omega.cashflow.repository.MovimentacaoRepository;
import com.omega.cashflow.service.caixa.CaixaService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MovimentacaoServiceImpl implements MovimentacaoService {

  private final MovimentacaoRepository movimentacaoRepository;

  private final CaixaRepository caixaRepository;

  private final CaixaService caixaService;

  @Override
  @Transactional
  public MovimentacaoResponseDTO saveMovimento(MovimentacaoCreateOrUpdateDTO movimentoDto) {

    MovimentacaoEntity novaMovimentacao = new MovimentacaoEntity(movimentoDto);
    var caixa = caixaService.findById(movimentoDto.getCaixa().getId());

    switch (movimentoDto.getTipo()){
      case SAIDA -> caixa.removeSaldo(movimentoDto.getValor());
      case ENTRADA -> caixa.addSaldo(movimentoDto.getValor());
      default -> throw new IllegalArgumentException("Tipo inválido.");
    }

    this.caixaRepository.save(caixa);

    MovimentacaoEntity movimentacao = this.movimentacaoRepository.save(novaMovimentacao);
    movimentacao.setCaixa(caixa);


    return MovimentacaoResponseDTO.toEntity(movimentacao);
  }
}