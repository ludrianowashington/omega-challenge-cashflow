package com.omega.cashflow.service.movimentacao;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoResponseDTO;
import com.omega.cashflow.entity.CaixaEntity;
import com.omega.cashflow.entity.MovimentacaoEntity;
import com.omega.cashflow.enumeration.TipoEnum;
import com.omega.cashflow.exception.MovimentacaoException;
import com.omega.cashflow.exception.RecursoNaoEncontradoException;
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
  public MovimentacaoResponseDTO saveMovimentacao(MovimentacaoCreateOrUpdateDTO dto) {
    try {
        MovimentacaoEntity novaMovimentacao = new MovimentacaoEntity(dto);
        validarMovimentacao(novaMovimentacao);

        var caixa = caixaService.findById(dto.getCaixa().getId());
        processarMovimentacao(novaMovimentacao, caixa);

        return MovimentacaoResponseDTO.toEntity(novaMovimentacao);
    } catch (Exception e) {
        throw new MovimentacaoException("Erro ao salvar movimentação", e);
    }
  }

  private void processarMovimentacao(MovimentacaoEntity movimentacao, CaixaEntity caixa) {
    try {
        atualizarSaldoCaixa(caixa, movimentacao.getTipo(), movimentacao.getValor());
        caixaRepository.save(caixa);

        movimentacao.setCaixa(caixa);
        movimentacaoRepository.save(movimentacao);
    } catch (Exception e) {
        throw new MovimentacaoException("Erro ao processar movimentação", e);
    }
  }

  private void validarMovimentacao(MovimentacaoEntity movimentacao) {
    if (movimentacao.getValor() == null || movimentacao.getValor() <= 0) {
        throw new MovimentacaoException("O valor da movimentação deve ser maior que zero");
    }
    if (movimentacao.getData() == null) {
        throw new MovimentacaoException("A data da movimentação é obrigatória");
    }
    if (movimentacao.getDescricao() == null || movimentacao.getDescricao().trim().isEmpty()) {
        throw new MovimentacaoException("A descrição da movimentação é obrigatória");
    }
    if (movimentacao.getTipo() == null) {
        throw new MovimentacaoException("O tipo da movimentação é obrigatório");
    }
    if (movimentacao.getCaixa() == null) {
        throw new MovimentacaoException("O caixa da movimentação é obrigatório");
    }
  }

  private void atualizarSaldoCaixa(CaixaEntity caixa, TipoEnum tipo, Double valor) {
    switch (tipo) {
        case ENTRADA -> caixa.addSaldo(valor);
        case SAIDA -> caixa.removeSaldo(valor);
        default -> throw new IllegalArgumentException("Tipo de movimentação inválido");
    }
  }

  private void reverterSaldoCaixa(CaixaEntity caixa, TipoEnum tipo, Double valor) {
    switch (tipo) {
        case ENTRADA -> caixa.removeSaldo(valor);
        case SAIDA -> caixa.addSaldo(valor);
        default -> throw new IllegalArgumentException("Tipo de movimentação inválido");
    }
  }

  @Override
  @Transactional
  public MovimentacaoResponseDTO updateMovimentacao(Long id, MovimentacaoCreateOrUpdateDTO dto) {
    MovimentacaoEntity movimentacao = movimentacaoRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Movimentação não encontrada"));

    try {
        // Validar novo DTO
        MovimentacaoEntity novaMovimentacao = new MovimentacaoEntity(dto);
        validarMovimentacao(novaMovimentacao);

        CaixaEntity caixaAtual = movimentacao.getCaixa();


        // Reverter saldo do caixa atual
        reverterSaldoCaixa(caixaAtual, movimentacao.getTipo(), movimentacao.getValor());

        // Se o DTO tem caixa diferente, trocar a referência
        if (dto.getCaixa() != null && !caixaAtual.getId().equals(dto.getCaixa().getId())) {
            CaixaEntity novoCaixa = caixaService.findById(dto.getCaixa().getId());
            movimentacao.setCaixa(novoCaixa);
            caixaAtual = novoCaixa;
        }

        // Atualizar movimentação e saldo
        movimentacao.update(dto);
        atualizarSaldoCaixa(caixaAtual, dto.getTipo(), dto.getValor());

        caixaRepository.save(caixaAtual);
        movimentacaoRepository.save(movimentacao);

        return MovimentacaoResponseDTO.toEntity(movimentacao);
    } catch (Exception e) {
        throw new MovimentacaoException("Erro ao atualizar movimentação: " + e.getMessage(), e);
    }
  }

  @Override
  @Transactional
  public void deleteMovimentacao(Long id) {
    MovimentacaoEntity movimentacao = movimentacaoRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Movimentação não encontrada"));

    try {
        movimentacaoRepository.delete(movimentacao);
    } catch (Exception e) {
        throw new MovimentacaoException("Erro ao excluir movimentação", e);
    }
  }

  @Override
  public MovimentacaoResponseDTO findById(Long id) {
    return movimentacaoRepository.findById(id)
        .map(MovimentacaoResponseDTO::toEntity)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Movimentação não encontrada"));
  }

  @Override
  public Page<MovimentacaoResponseDTO> findAll(Pageable pageable) {
    return movimentacaoRepository.findAll(pageable)
        .map(MovimentacaoResponseDTO::toEntity);
  }

  @Override
  public Page<MovimentacaoResponseDTO> search(
      TipoEnum tipo,
      Double valorMinimo,
      Double valorMaximo,
      LocalDate dataInicio,
      LocalDate dataFim,
      Long caixaId,
      Pageable pageable) {

    try {
        return movimentacaoRepository.search(
            tipo,
            valorMinimo,
            valorMaximo,
            dataInicio,
            dataFim,
            caixaId,
            pageable
        ).map(MovimentacaoResponseDTO::toEntity);
    } catch (Exception e) {
        throw new MovimentacaoException("Erro ao realizar busca de movimentações", e);
    }
  }
}