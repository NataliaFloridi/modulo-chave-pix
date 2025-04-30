package com.modulo.chave.pix.application.validation.strategy.Impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ConsultaChavePixStrategy;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.port.ConsultaChavePixPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class ConsultaPorIdStrategyImpl implements ConsultaChavePixStrategy {

    private final ConsultaChavePixPort consultaChavePixPort;
    private ChavePix chavePix;

    @Override
    public List<ChavePix> execute() {
        log.info("Executando consulta de chave PIX por ID: {}", chavePix.getId());
        
        Optional<ChavePix> chavePixEncontrada = consultaChavePixPort.findById(chavePix.getId());
        
        return chavePixEncontrada.map(Collections::singletonList)
                .orElse(Collections.emptyList());
    }

    @Override
    public boolean estaValido() {
        log.info("Verificando se o critério de consulta é válido");
        boolean valido = chavePix != null && chavePix.getId() != null;

        log.info("Critério de consulta válido: {}", valido);
        return valido;
    }
 
    public ConsultaPorIdStrategyImpl comChavePix(ChavePix chavePix) {
        this.chavePix = chavePix;
        return this;
    }

    //ConsultaPorIdStrategy strategy = new ConsultaPorIdStrategy(consultaChavePixPort)
    //.comChavePix(chavePix)
    //.comOutraConfiguracao(valor);
} 