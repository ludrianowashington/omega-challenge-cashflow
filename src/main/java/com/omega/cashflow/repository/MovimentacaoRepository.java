package com.omega.cashflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omega.cashflow.entity.MovimentacaoEntity;

@Repository
public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEntity, Long> {

}
