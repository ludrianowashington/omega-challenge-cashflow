package com.omega.cashflow.controller.v1.caixa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.omega.cashflow.controller.v1.caixa.dto.CaixaCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.caixa.dto.CaixaResponseDTO;
import com.omega.cashflow.service.caixa.CaixaService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CaixaController implements CaixaAPI {

  private final CaixaService caixaService;

  @Override
  public ResponseEntity<CaixaResponseDTO> save(@RequestBody @Valid CaixaCreateOrUpdateDTO dto) {

    final var response = caixaService.saveCaixa(dto);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Override
  public ResponseEntity<CaixaResponseDTO> update(
    @PathVariable Long id,
    @RequestBody @Valid CaixaCreateOrUpdateDTO caixa
  ) {
    CaixaResponseDTO caixaAtualizada = caixaService.updateCaixa(id, caixa);
    return ResponseEntity.ok(caixaAtualizada);
  }

  @Override
  public ResponseEntity<CaixaResponseDTO> findById(@PathVariable Long id) {
    CaixaResponseDTO caixa = CaixaResponseDTO.toEntity(caixaService.findById(id));
    return ResponseEntity.ok(caixa);
  }

  @Override
 public ResponseEntity<Page<CaixaResponseDTO>> findAll(Pageable pageable) {
    Page<CaixaResponseDTO> caixas = caixaService.findAll(Pageable.unpaged());
    return ResponseEntity.ok(caixas);
  }

  @Override
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    caixaService.deleteCaixa(id);
    return ResponseEntity.noContent().build();
  }
}
