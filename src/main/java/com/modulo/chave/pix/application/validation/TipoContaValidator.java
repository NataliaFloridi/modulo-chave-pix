package com.modulo.chave.pix.application.validation;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TipoContaValidator {

    private static final List<TipoContaEnum> TIPOS_CONTA_VALIDOS = Arrays.asList(
            TipoContaEnum.CONTA_CORRENTE,
            TipoContaEnum.CONTA_POUPANCA);

    public void validate(TipoContaEnum tipoConta) throws ValidationException {
        log.info("Validando tipo de conta");
        if (tipoConta == null) {
            log.error("O campo tipoConta é obrigatório");
            throw new ValidationException("O campo tipoConta é obrigatório");
        }

        if (!TIPOS_CONTA_VALIDOS.contains(tipoConta)) {
            log.error("O campo tipoConta deve ser " + TIPOS_CONTA_VALIDOS);
            throw new ValidationException("O campo tipoConta deve ser " + TIPOS_CONTA_VALIDOS);
        }
        
        if (tipoConta.getDescricao().length() > 10) {
            log.error("O campo tipoConta deve ter no máximo 10 caracteres");
            throw new ValidationException("O campo tipoConta deve ter no máximo 10 caracteres");
        }
    }
}