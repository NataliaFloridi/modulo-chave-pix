package com.modulo.chave.pix.application.validation.strategy;

import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;

public interface ChavePixAlteracaoRegraValidatorStrategy {
    void validate(ChavePix chavePixAtual, ChavePix chavePixOriginal) throws BusinessValidationException;
}
