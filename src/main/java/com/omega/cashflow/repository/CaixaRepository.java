package com.omega.cashflow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omega.cashflow.entity.CaixaEntity;

@Repository
public interface CaixaRepository extends JpaRepository<CaixaEntity, Long> {
  Optional<CaixaEntity> findCaixaByDescricao(String descricao);
}
