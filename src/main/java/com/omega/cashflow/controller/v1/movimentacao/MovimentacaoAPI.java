package com.omega.cashflow.controller.v1.movimentacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoResponseDTO;
import com.omega.cashflow.enumeration.TipoEnum;

import jakarta.validation.Valid;

@RequestMapping(value = "/v1/movimentacao")
public interface MovimentacaoAPI {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<MovimentacaoResponseDTO> create(@Valid @RequestBody MovimentacaoCreateOrUpdateDTO dto);

    @GetMapping
    ResponseEntity<Page<MovimentacaoResponseDTO>> findAll(Pageable pageable);

    @GetMapping("/{id}")
    ResponseEntity<MovimentacaoResponseDTO> findById(@PathVariable Long id);

    @GetMapping("/search")
    ResponseEntity<Page<MovimentacaoResponseDTO>> search(
        @RequestParam(required = false) TipoEnum tipo,
        @RequestParam(required = false) Double valorMinimo,
        @RequestParam(required = false) Double valorMaximo,
        @RequestParam(required = false) String dataInicio,
        @RequestParam(required = false) String dataFim,
        @RequestParam(required = false) Long caixaId,
        Pageable pageable
    );

    @PutMapping("/{id}")
    ResponseEntity<MovimentacaoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody MovimentacaoCreateOrUpdateDTO dto);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id);
}