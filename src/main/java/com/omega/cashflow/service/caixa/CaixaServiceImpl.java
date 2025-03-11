package com.omega.cashflow.service.caixa;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.omega.cashflow.controller.v1.caixa.dto.CaixaCreateOrUpdateDTO;
import com.omega.cashflow.controller.v1.caixa.dto.CaixaResponseDTO;
import com.omega.cashflow.entity.CaixaEntity;
import com.omega.cashflow.repository.CaixaRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CaixaServiceImpl implements CaixaService {

  private final CaixaRepository caixaRepository;

  @Override
  public CaixaResponseDTO saveCaixa(CaixaCreateOrUpdateDTO caixa) {
    validateDescricao(caixa);

    CaixaEntity novo = new CaixaEntity(caixa);

    return CaixaResponseDTO.toEntity(this.caixaRepository.save(novo));
  }

  @Override
  public CaixaResponseDTO updateCaixa(Long id, CaixaCreateOrUpdateDTO caixaDto) {
    if (!this.caixaRepository.existsById(id)) {
      throw new EntityNotFoundException("Caixa não encontrada com id: " + id);
    }

    CaixaEntity caixa = this.findById(id);

    validateDescricao(caixaDto);

    caixa.setDescricao(caixaDto.getDescricao());

    return CaixaResponseDTO.toEntity(this.caixaRepository.save(caixa));
  }

  @Override
  public CaixaEntity findById(Long id) {
    if (!this.caixaRepository.existsById(id)) {
      throw new EntityNotFoundException("Caixa não encontrada com id: " + id);
    }

    return this.caixaRepository.findById(id).get();
  }

  @Override
  public void deleteCaixa(Long id){
    if (!caixaRepository.existsById(id)) {
      throw new EntityNotFoundException("Caixa não encontrado com id: " + id);
    }

    this.caixaRepository.deleteById(id);
  }

  @Override
  public Page<CaixaResponseDTO> findAll(Pageable pageable) {
    return this.caixaRepository.findAll(pageable)
        .map(CaixaResponseDTO::toEntity);
  }

  private void validateDescricao(CaixaCreateOrUpdateDTO caixaDto){
    if (caixaDto.getDescricao() == null || caixaDto.getDescricao().trim().isEmpty()) {
      throw new IllegalArgumentException("A descrição não pode ser nula ou vazia.");
    }
    Optional<CaixaEntity> existingCustomer = this.caixaRepository.findCaixaByDescricao(caixaDto.getDescricao());
    if (existingCustomer.isPresent()) {
      throw new EntityExistsException("Descrição já em uso");
    }
  }

}
