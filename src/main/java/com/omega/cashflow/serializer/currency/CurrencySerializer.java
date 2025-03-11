package com.omega.cashflow.serializer.currency;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CurrencySerializer extends JsonSerializer<Double> {

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        NumberFormat FORMATTER = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        FORMATTER.setMinimumFractionDigits(2);
        FORMATTER.setMaximumFractionDigits(2);

        if (value == null) {
            gen.writeString("R$ 0,00");
            return;
        }

        String valorFormatado = FORMATTER.format(value)
            .replace("\u00A0", " ");

        gen.writeString(valorFormatado);
    }
}
