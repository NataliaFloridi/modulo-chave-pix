package com.modulo.chave.pix.application.validation;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ChavePixRegraValidatorStrategy;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.port.ChavePixPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlteracaoChavePixValidator implements ChavePixRegraValidatorStrategy {

    private final ChavePixPort chavePixPort;

    @Override
    public void validate(ChavePix chavePix) throws BusinessValidationException {
        log.info("Validando regras de alteração de chave PIX");
    
        ChavePix chaveExistente = chavePixPort.findByValorChave(chavePix.getValorChave());
        if (chaveExistente == null) {
            log.error("Chave PIX não encontrada");
            throw new BusinessValidationException("Chave PIX não encontrada");
        }

        log.info("Validando status da chave PIX");
        if (!chaveExistente.isStatusConta()) {
            log.error("Não é permitido alterar chaves inativadas");
            throw new BusinessValidationException("Não é permitido alterar chaves inativadas");
        }

        log.info("Validando ID da chave PIX");
        if (!chaveExistente.getId().equals(chavePix.getId())) {
            log.error("Não é permitido alterar o ID da chave");
            throw new BusinessValidationException("Não é permitido alterar o ID da chave");
        }

        log.info("Validando tipo da chave PIX");
        if (!chaveExistente.getTipoChave().equals(chavePix.getTipoChave())) {
            log.error("Não é permitido alterar o tipo da chave");
            throw new BusinessValidationException("Não é permitido alterar o tipo da chave");
        }

        log.info("Validando valor da chave PIX");
        if (!chaveExistente.getValorChave().equals(chavePix.getValorChave())) {
            log.error("Não é permitido alterar o valor da chave");
            throw new BusinessValidationException("Não é permitido alterar o valor da chave");
        }

        log.info("Chave PIX válida");
    }
} 