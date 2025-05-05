package com.modulo.chave.pix.application.validation.strategy.Impl;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ChavePixRegraValidatorStrategy;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.exception.DuplicateKeyException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.port.InclusaoChavePixPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChaveDuplicadaValidatorStrategyImpl implements ChavePixRegraValidatorStrategy {

    private final InclusaoChavePixPort chavePixPort;

    @Override
    public void validate(ChavePix chavePix) throws BusinessValidationException {
        log.info("Validando chave pix existente");
        if(chavePixPort.existePeloValorChave(chavePix.getValorChave())){
            log.error("Chave Pix já cadastrada " + chavePix.getValorChave());
            throw new DuplicateKeyException("Chave Pix já cadastrada " + chavePix.getValorChave());
        }
    }
}