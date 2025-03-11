package com.omega.cashflow.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.omega.cashflow.controller.v1.movimentacao.dto.MovimentacaoCreateOrUpdateDTO;
import com.omega.cashflow.enumeration.TipoEnum;
import com.omega.cashflow.serializer.currency.CurrencyDeserializer;
import com.omega.cashflow.serializer.currency.CurrencySerializer;
import com.omega.cashflow.serializer.localDate.LocalDateDeserializer;
import com.omega.cashflow.serializer.localDate.LocalDateSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movimentacao")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimentacaoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "data", nullable = false)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate data;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "caixa", nullable = false)
    private CaixaEntity caixa;

    @Column(name = "tipo", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TipoEnum tipo;

    @Column(name = "valor", nullable = false)
    @JsonSerialize(using = CurrencySerializer.class)
    @JsonDeserialize(using = CurrencyDeserializer.class)
    private Double valor;

    public MovimentacaoEntity(MovimentacaoCreateOrUpdateDTO mv) {
        this.descricao = mv.getDescricao();
        this.data = mv.getData();
        this.tipo = mv.getTipo();
        this.caixa = mv.getCaixa();
        this.valor = mv.getValor();
    }

    public void update(MovimentacaoCreateOrUpdateDTO dto) {
        this.descricao = dto.getDescricao();
        this.data = dto.getData();
        this.tipo = dto.getTipo();
        this.valor = dto.getValor();
    }

    public void validar() {
        if (this.valor <= 0) {
            throw new IllegalArgumentException("O valor da movimentação deve ser maior que zero");
        }
        if (this.data == null) {
            throw new IllegalArgumentException("A data da movimentação é obrigatória");
        }
        if (this.descricao == null || this.descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição da movimentação é obrigatória");
        }
    }
}