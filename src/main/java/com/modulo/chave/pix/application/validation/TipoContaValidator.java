package com.modulo.chave.pix.application.validation;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TipoContaValidator {

    public void validate(TipoContaEnum tipoConta) throws ValidationException {
        
        log.info("Validando tipo de conta");
        if (tipoConta == null) {
            log.error("O campo tipoConta é obrigatório");
            throw new ValidationException("O campo tipoConta é obrigatório");
        }

        if (tipoConta != TipoContaEnum.CORRENTE && tipoConta != TipoContaEnum.POUPANCA) {
            log.error("Tipo de conta inválido: {}", tipoConta);
            throw new ValidationException("Tipo de conta inválido: " + tipoConta);
        }
        
        /* essa validação não é necessária se utilizarmos o enum, 
        mas está aqui caso se seja necessário uso de string futuramente
        if (tipoConta.getDescricao().length() > 10) {
            log.error("O campo tipoConta deve ter no máximo 10 caracteres");
            throw new ValidationException("O campo tipoConta deve ter no máximo 10 caracteres");
        }
        */
    }
}