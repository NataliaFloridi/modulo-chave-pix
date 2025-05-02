package com.modulo.chave.pix.infrastructure.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;

@Component
public class StringToTipoChaveEnumConverter implements Converter<String, TipoChaveEnum> {
    
    @Override
    public TipoChaveEnum convert(String string){
        if (string == null || string.isEmpty()) {
            return null;
        }
        try {
            return TipoChaveEnum.valueOf(string.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de chave inválido: " + string + 
                ". Valores válidos são: CPF, CNPJ, EMAIL, TELEFONE, ALEATORIO");
        }
    }
} 