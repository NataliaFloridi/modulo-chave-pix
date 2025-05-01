package com.modulo.chave.pix.infrastructure.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.modulo.chave.pix.infrastructure.web.converter.StringToLocalDateTimeConverter;
import com.modulo.chave.pix.infrastructure.web.converter.StringToTipoChaveEnumConverter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final StringToTipoChaveEnumConverter stringToTipoChaveEnumConverter;
    private final StringToLocalDateTimeConverter stringToLocalDateTimeConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToTipoChaveEnumConverter);
        registry.addConverter(stringToLocalDateTimeConverter);
    }
} 