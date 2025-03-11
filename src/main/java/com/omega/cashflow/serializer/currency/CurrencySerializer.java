package com.omega.cashflow.serializer.currency;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CurrencySerializer extends JsonSerializer<Double> {
    private static final Locale LOCALE_BR = new Locale("pt", "BR");
    private static final NumberFormat FORMATTER = NumberFormat.getCurrencyInstance(LOCALE_BR);

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        gen.writeString(FORMATTER.format(value));
    }
}
