package com.omega.cashflow.serializer.currency;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CurrencyDeserializer extends JsonDeserializer<Double> {
    private static final Locale LOCALE_BR = new Locale("pt", "BR");

    @Override
    public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String valor = p.getText()
            .replace("R$", "")
            .trim();

        try {
            NumberFormat format = NumberFormat.getNumberInstance(LOCALE_BR);
            return format.parse(valor).doubleValue();
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao desserializar valor monetário: " + valor, e);
        }
    }
}