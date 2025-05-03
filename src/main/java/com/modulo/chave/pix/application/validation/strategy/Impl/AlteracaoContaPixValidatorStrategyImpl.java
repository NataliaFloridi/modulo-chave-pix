package com.modulo.chave.pix.application.validation.strategy.Impl;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ChavePixAlteracaoRegraValidatorStrategy;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlteracaoContaPixValidatorStrategyImpl implements ChavePixAlteracaoRegraValidatorStrategy {

    @Override
    public void validate(ChavePix chavePixAtual, ChavePix chavePixOriginal) throws BusinessValidationException {
        log.info("Validando regras de alteração de conta PIX");
        
        log.info("Validando status da conta PIX");
        if (chavePixAtual.getDataInativacao() != null) {
            log.error("Não é permitido alterar chaves pix inativadas");
            throw new BusinessValidationException("Não é permitido alterar chaves pix inativadas");
        }

        //log.info("Validando ID da chave PIX");
        //if (!chavePixAtual.getId().equals(chavePixOriginal.getId())) {
        //    log.error("Não é permitido alterar o ID da chave PIX");
        //    throw new BusinessValidationException("Não é permitido alterar o ID da chave PIX");
        //}

        //log.info("Validando tipo da chave PIX");
        //if (!chavePixAtual.getTipoChave().equals(chavePixOriginal.getTipoChave())) {
        //    log.error("Não é permitido alterar o tipo da chave");
        //    throw new BusinessValidationException("Não é permitido alterar o tipo da chave");
        //}

        //log.info("Validando valor da chave PIX");
        //if (!chavePixAtual.getValorChave().equals(chavePixOriginal.getValorChave())) {
        //    log.error("Não é permitido alterar o valor da chave");
        //    throw new BusinessValidationException("Não é permitido alterar o valor da chave");
        //}

        log.info("Chave PIX válida");
    }
} 