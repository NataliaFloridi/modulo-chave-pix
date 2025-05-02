package com.modulo.chave.pix.application.validation.strategy.Impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.modulo.chave.pix.application.validation.strategy.ConsultaChavePixStrategy;
import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.port.ConsultaChavePixPort;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ConsultaPorIdStrategyImpl implements ConsultaChavePixStrategy {

    private final ConsultaChavePixPort consultaChavePixPort;
    private final ChavePix chavePix;

    public ConsultaPorIdStrategyImpl(ConsultaChavePixPort consultaChavePixPort, ChavePix chavePix) {
        this.consultaChavePixPort = consultaChavePixPort;
        this.chavePix = chavePix;
    }

    @Override
    public boolean estaValido() {
        log.info("Verificando se o critério de consulta é válido");
        
        if (chavePix == null || chavePix.getId() == null) {
            log.error("ID da chave PIX não informado");
            throw new ValidationException("ID da chave PIX não informado");
        }

        log.info("Critério de consulta válido");
        return true;
    }
    
    @Override
    public List<ChavePix> execute() {
        log.info("Executando consulta de chave PIX por ID: {}", chavePix.getId());
        
        Optional<ChavePix> chavePixEncontrada = consultaChavePixPort.findById(chavePix.getId());
        
        return chavePixEncontrada.map(chave -> List.of(chave))
            .orElse(Collections.emptyList());
    }
} 