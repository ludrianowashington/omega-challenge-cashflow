package com.omega.cashflow.controller.v1.movimentacao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoResponseDTO;
import com.omega.cashflow.enumeration.TipoEnum;
import com.omega.cashflow.service.movimentacao.MovimentacaoService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class MovimentacaoController implements MovimentacaoAPI {

    private final MovimentacaoService movimentacaoService;

    @Override
    public ResponseEntity<MovimentacaoResponseDTO> create(MovimentacaoCreateOrUpdateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(movimentacaoService.saveMovimentacao(dto));
    }

    @Override
    public ResponseEntity<Page<MovimentacaoResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(movimentacaoService.findAll(pageable));
    }

    @Override
    public ResponseEntity<MovimentacaoResponseDTO> findById(Long id) {
        return ResponseEntity.ok(movimentacaoService.findById(id));
    }

    @Override
    public ResponseEntity<Page<MovimentacaoResponseDTO>> search(
            String tipo,
            Double valorMinimo,
            Double valorMaximo,
            String dataInicio,
            String dataFim,
            Long caixaId,
            Pageable pageable) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate dataInicioFormatada = (dataInicio != null && !dataInicio.isEmpty())
            ? LocalDate.parse(dataInicio, formatter)
            : null;

        LocalDate dataFimFormatada = (dataFim != null && !dataFim.isEmpty())
            ? LocalDate.parse(dataFim, formatter)
            : null;

        TipoEnum tipoEnum = (tipo != null && !tipo.isEmpty())
            ? TipoEnum.valueOf(tipo.toUpperCase())
            : null;

        return ResponseEntity.ok(movimentacaoService.search(
            tipoEnum,
            valorMinimo,
            valorMaximo,
            dataInicioFormatada,
            dataFimFormatada,
            caixaId,
            pageable));
    }

    @Override
    public ResponseEntity<MovimentacaoResponseDTO> update(Long id, MovimentacaoCreateOrUpdateDTO dto) {
        return ResponseEntity.ok(movimentacaoService.updateMovimentacao(id, dto));
    }

    @Override
    public void delete(Long id) {
        movimentacaoService.deleteMovimentacao(id);
    }
}