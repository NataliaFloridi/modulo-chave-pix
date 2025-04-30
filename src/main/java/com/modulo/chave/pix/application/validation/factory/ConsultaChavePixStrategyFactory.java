package com.modulo.chave.pix.application.validation.factory;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ConsultaChavePixStrategy;
import com.modulo.chave.pix.application.validation.strategy.Impl.ConsultaPorIdStrategyImpl;
import com.modulo.chave.pix.application.validation.strategy.Impl.ConsultaPorMultiplosCriteriosStrategyImpl;
import com.modulo.chave.pix.domain.model.ChavePix;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


//Padrão: Factory
//Motivo: Para criar a estratégia apropriada com base nos critérios de consulta.
@Component
@RequiredArgsConstructor
@Slf4j
public class ConsultaChavePixStrategyFactory {

    private final ConsultaPorIdStrategyImpl consultaPorIdStrategy;
    private final ConsultaPorMultiplosCriteriosStrategyImpl consultaPorMultiplosCriteriosStrategy;

    public ConsultaChavePixStrategy createStrategy(ChavePix chavePix) {
        log.info("Criando estratégia de consulta para a requisição: {}", chavePix);
        
        log.info("Verificando se a estratégia por ID é válida");
        var idStrategy = consultaPorIdStrategy.comChavePix(chavePix);
        if (idStrategy.estaValido()) {
            log.info("Estratégia por ID selecionada");
            return idStrategy;
        }
        
        log.info("Verificando se a estratégia por múltiplos critérios é válida");
        var multiplosCriteriosStrategy = consultaPorMultiplosCriteriosStrategy.comChavePix(chavePix);
        if (multiplosCriteriosStrategy.estaValido()) {
            log.info("Estratégia por múltiplos critérios selecionada");
            return multiplosCriteriosStrategy;
        }
        
        log.info("Nenhuma estratégia válida encontrada, usando estratégia por múltiplos critérios");
        return multiplosCriteriosStrategy;
    }
} 