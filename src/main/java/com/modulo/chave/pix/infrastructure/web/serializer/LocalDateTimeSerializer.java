package com.modulo.chave.pix.infrastructure.web.serializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy[ HH:mm:ss]");

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws java.io.IOException {
        if (value == null) {
            gen.writeString("");
            return;
        }
        gen.writeString(value.format(FORMATTER));
    }

    @Override
    public Class<LocalDateTime> handledType() {
        return LocalDateTime.class;
    }
} 