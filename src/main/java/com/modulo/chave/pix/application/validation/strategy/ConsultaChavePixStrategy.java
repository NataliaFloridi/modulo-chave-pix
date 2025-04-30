package com.modulo.chave.pix.application.validation.strategy;

import java.util.List;

import com.modulo.chave.pix.domain.model.ChavePix;

// Padrão: Strategy
// Motivo: Para implementar diferentes estratégias de consulta de chaves PIX,
// permitindo reutilização de código e extensibilidade.
public interface ConsultaChavePixStrategy {
    List<ChavePix> execute();
    boolean estaValido();
}