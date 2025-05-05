package com.modulo.chave.pix.application.usecase;

import java.util.List;

import com.modulo.chave.pix.domain.model.ChavePix;

public interface ConsultaChavePixUseCase {
    List<ChavePix> execute(ChavePix chavePix);
} 
