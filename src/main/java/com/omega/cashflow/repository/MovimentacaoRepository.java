package com.omega.cashflow.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.omega.cashflow.entity.MovimentacaoEntity;
import com.omega.cashflow.enumeration.TipoEnum;

@Repository
public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEntity, Long> {

    @Query("SELECT m FROM MovimentacaoEntity m WHERE " +
           "(:tipo IS NULL OR m.tipo = :tipo) AND " +
           "(:valorMinimo IS NULL OR m.valor >= :valorMinimo) AND " +
           "(:valorMaximo IS NULL OR m.valor <= :valorMaximo) AND " +
           "(:dataInicio IS NULL OR m.data >= :dataInicio) AND " +
           "(:dataFim IS NULL OR m.data <= :dataFim) AND " +
           "(:caixaId IS NULL OR m.caixa.id = :caixaId)")
    Page<MovimentacaoEntity> search(
            @Param("tipo") TipoEnum tipo,
            @Param("valorMinimo") Double valorMinimo,
            @Param("valorMaximo") Double valorMaximo,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("caixaId") Long caixaId,
            Pageable pageable);
}
