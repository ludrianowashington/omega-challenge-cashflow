package com.omega.cashflow.controller.v1.caixa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.omega.cashflow.controller.v1.caixa.dto.CaixaCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.caixa.dto.CaixaResponseDTO;

import jakarta.validation.Valid;

@RequestMapping("/v1/caixa")
public interface CaixaAPI {

  @PostMapping
  public ResponseEntity<CaixaResponseDTO> save(@Valid @RequestBody CaixaCreateOrUpdateDTO dto);

  @PutMapping("/{id}")
  public ResponseEntity<CaixaResponseDTO> update(
    @PathVariable Long id,
    @RequestBody @Valid CaixaCreateOrUpdateDTO caixa
  );

  @GetMapping("/{id}")
  public ResponseEntity<CaixaResponseDTO> findById(@PathVariable Long id);

  @GetMapping
  public ResponseEntity<Page<CaixaResponseDTO>> findAll(Pageable pageable);

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id);
}
