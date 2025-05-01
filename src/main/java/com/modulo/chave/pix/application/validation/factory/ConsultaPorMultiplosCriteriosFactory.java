package com.modulo.chave.pix.application.validation.factory;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.Impl.ConsultaPorMultiplosCriteriosStrategyImpl;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.port.ConsultaChavePixPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConsultaPorMultiplosCriteriosFactory {
    private final ConsultaChavePixPort consultaChavePixPort;

    public ConsultaPorMultiplosCriteriosStrategyImpl create(ChavePix chavePix) {
        return new ConsultaPorMultiplosCriteriosStrategyImpl(consultaChavePixPort, chavePix);
    }
} 