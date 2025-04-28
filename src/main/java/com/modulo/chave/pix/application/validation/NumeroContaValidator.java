package com.modulo.chave.pix.application.validation;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ContasValidatorStrategy;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NumeroContaValidator implements ContasValidatorStrategy {

    @Override
    public void validate(Integer numeroConta) throws BusinessValidationException {
        log.info("Validando número da conta");
        if (numeroConta == null || numeroConta.equals(0L)) {
            log.error("O número da conta é obrigatório");
            throw new BusinessValidationException("O número da conta é obrigatório");
        }

        if (!numeroConta.toString().matches("\\d+")) {
            log.error("O número da conta deve conter apenas números");
            throw new BusinessValidationException("O número da conta deve conter apenas números");
        }

        if (numeroConta.toString().length() > 8) {
            log.error("O número da conta deve ter no máximo 8 dígitos");
            throw new BusinessValidationException("O número da conta deve ter no máximo 8 dígitos");
        }        
    }
}