package com.modulo.chave.pix.application.validation.strategy.Impl;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ChavePixAlteracaoRegraValidatorStrategy;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlteracaoChavePixValidatorStrategyImpl implements ChavePixAlteracaoRegraValidatorStrategy {

    @Override
    public void validate(ChavePix chavePixAtual, ChavePix chavePixOriginal) throws BusinessValidationException {
        log.info("Validando regras de alteração de chave PIX");
    
        //ok
        log.info("Validando status da chave PIX");
        if (chavePixAtual.getDataInativacao() != null) {
            log.error("Não é permitido alterar chaves inativadas");
            throw new BusinessValidationException("Não é permitido alterar chaves inativadas");
        }

        //ok
        log.info("Validando ID da chave PIX");
        if (!chavePixAtual.getId().equals(chavePixOriginal.getId())) {
            log.error("Não é permitido alterar o ID da chave");
            throw new BusinessValidationException("Não é permitido alterar o ID da chave");
        }

        //ok
        log.info("Validando tipo da chave PIX");
        if (!chavePixAtual.getTipoChave().equals(chavePixOriginal.getTipoChave())) {
            log.error("Não é permitido alterar o tipo da chave");
            throw new BusinessValidationException("Não é permitido alterar o tipo da chave");
        }

        log.info("Validando valor da chave PIX");
        if (chavePixAtual.getTipoChave().equals(TipoChaveEnum.ALEATORIO)) {
            log.error("Não é permitido alterar o valor da chave aleatória");
            throw new BusinessValidationException("Não é permitido alterar o valor da chave aleatória");
        }
        log.info("Chave PIX válida");
    }
} 