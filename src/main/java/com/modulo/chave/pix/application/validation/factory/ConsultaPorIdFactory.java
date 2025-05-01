package com.modulo.chave.pix.application.validation.factory;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.Impl.ConsultaPorIdStrategyImpl;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.port.ConsultaChavePixPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConsultaPorIdFactory {
    private final ConsultaChavePixPort consultaChavePixPort;

    public ConsultaPorIdStrategyImpl create(ChavePix chavePix) {
        return new ConsultaPorIdStrategyImpl(consultaChavePixPort, chavePix);
    }
} 