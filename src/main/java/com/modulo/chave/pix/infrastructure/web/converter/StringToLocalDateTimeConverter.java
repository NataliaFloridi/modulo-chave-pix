package com.modulo.chave.pix.infrastructure.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy[ HH:mm:ss]");

    @Override
    public LocalDateTime convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        
        String trimmedSource = source.trim();
        try {
            return LocalDateTime.parse(trimmedSource, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data inválida: '" + source + 
                "'. O formato deve ser 'dd-MM-yyyy[ HH:mm:ss]' (ex: 30-01-2021 00:00:00)");
        }
    }
} 