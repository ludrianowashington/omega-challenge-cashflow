package com.omega.cashflow.controller.v1.movimentacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoResponseDTO;

import jakarta.validation.Valid;

@RequestMapping(value = "/v1/movimentacao")
public interface MovimentacaoAPI {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<MovimentacaoResponseDTO> create(@Valid @RequestBody MovimentacaoCreateOrUpdateDTO dto);

    // @GetMapping
    // ResponseEntity<List<MovimentacaoResponseDTO>> findAll();

    // @GetMapping("/{id}")
    // ResponseEntity<MovimentacaoResponseDTO> findById(@PathVariable Long id);

    // @GetMapping("/search")
    // ResponseEntity<List<MovimentacaoResponseDTO>> search(
    //     @RequestParam(required = false) TipoEnum tipo,
    //     @RequestParam(required = false) Double valorMinimo,
    //     @RequestParam(required = false) Double valorMaximo,
    //     @RequestParam(required = false) String dataInicio,
    //     @RequestParam(required = false) String dataFim,
    //     @RequestParam(required = false) Long caixaId
    // );

    // @PutMapping("/{id}")
    // ResponseEntity<MovimentacaoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody MovimentacaoCreateOrUpdateDTO dto);

    // @DeleteMapping("/{id}")
    // @ResponseStatus(HttpStatus.NO_CONTENT)
    // void delete(@PathVariable Long id);


}