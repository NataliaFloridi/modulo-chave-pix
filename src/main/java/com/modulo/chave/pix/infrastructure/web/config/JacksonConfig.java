package com.modulo.chave.pix.infrastructure.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.modulo.chave.pix.infrastructure.web.serializer.LocalDateTimeSerializer;

import java.time.LocalDateTime;

//Garante que o serializer seja utilizado.
//Jackson é um framework padrão para serialização e deserialização de JSON no Spring.
@Configuration
public class JacksonConfig {
    
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.build();
        
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        objectMapper.registerModule(module);
        
        return objectMapper;
    }
} 