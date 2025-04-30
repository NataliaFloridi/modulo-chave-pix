package com.modulo.chave.pix.application.usecase;

import java.util.List;

import com.modulo.chave.pix.domain.model.ChavePix;

// Padrão: Use Case
// Motivo: Para definir o contrato do caso de uso de consulta de chaves PIX.
public interface ConsultaChavePixUseCase {
    List<ChavePix> execute(ChavePix chavePix);
} 
