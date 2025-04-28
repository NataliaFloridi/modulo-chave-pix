package com.modulo.chave.pix.application.usecase;

import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;

public interface InclusaoChavePixUseCase {
    ChavePix execute(ChavePix chavePix) throws ValidationException, BusinessValidationException;
}
