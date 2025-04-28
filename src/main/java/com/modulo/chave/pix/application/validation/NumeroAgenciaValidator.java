package com.modulo.chave.pix.application.validation;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ContasValidatorStrategy;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NumeroAgenciaValidator implements ContasValidatorStrategy {

    @Override
    public void validate(Integer numeroAgencia) throws BusinessValidationException {
        log.info("Validando número da agência");
        if (numeroAgencia == null || numeroAgencia.equals(0)) {
            log.error("O número da agência é obrigatório");
            throw new BusinessValidationException("O número da agência é obrigatório");
        }

        if (numeroAgencia.toString().length() > 4) {
            log.error("O número da agência deve ter no máximo 4 dígitos");
            throw new BusinessValidationException("O número da agência deve ter no máximo 4 dígitos");
        }

        if (!numeroAgencia.toString().matches("\\d+")) {
            log.error("O número da agência deve conter apenas números");
            throw new BusinessValidationException("O número da agência deve conter apenas números");
        }
    }
}