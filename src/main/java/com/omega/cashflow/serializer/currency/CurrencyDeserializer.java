package com.omega.cashflow.serializer.currency;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CurrencyDeserializer extends JsonDeserializer<Double> {


    @Override
    public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String valor = p.getText().trim();
        try {
            // Se for um número direto, apenas converte
            if (!valor.contains("R$")) {
                return Double.valueOf(valor);
            }

            // Limpa a formatação brasileira
            valor = valor.replace("R$", "")
                        .replaceAll("\\s+", "") // Remove espaços
                        .replaceAll("[.](?=.*[.])", "") // Remove pontos de milhar
                        .replace(",", ".") // Troca vírgula decimal por ponto
                        .trim();

            BigDecimal bigDecimal = new BigDecimal(valor).setScale(2, RoundingMode.HALF_UP);
            return bigDecimal.doubleValue();
        } catch (NumberFormatException e) {
            throw new RuntimeException("Erro ao desserializar valor monetário: " + valor, e);
        }
    }
}