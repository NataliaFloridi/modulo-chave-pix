package com.modulo.chave.pix.application.usecase;

import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;

public interface AlteracaoContaPixUseCase {
    ChavePix execute(ChavePix chavePix) throws ValidationException, BusinessValidationException;
}
