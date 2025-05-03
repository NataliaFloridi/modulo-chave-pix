package com.modulo.chave.pix.application.validation.factory;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ConsultaChavePixStrategy;
import com.modulo.chave.pix.domain.model.ChavePix;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//Padrão: Factory
//Motivo: Para criar a estratégia apropriada com base nos critérios de consulta.
@Component
@RequiredArgsConstructor
@Slf4j
public class ConsultaChavePixFactory {

    private final ConsultaPorIdFactory consultaPorIdFactory;
    private final ConsultaPorMultiplosCriteriosFactory consultaPorMultiplosCriteriosFactory;

    public ConsultaChavePixStrategy criarEstrategia(ChavePix chavePix) {
        log.info("Criando estratégia de consulta para a requisição: {}", chavePix);

        // decisao de qual estratégia usar é feita por ordem de prioridade
        // primeiro verifica se a estratégia por ID é válida caso nao,
        // erifica se a estratégia por múltiplos critérios é válida
        // caso nenhuma seja válida, usa a estratégia por múltiplos critérios

        // Tenta usar estratégia por ID primeiro
        if (chavePix.getId() != null) {
            log.info("Verificando se a estratégia por ID é válida");
            var idStrategy = consultaPorIdFactory.create(chavePix);
            if (idStrategy.estaValido()) {
                log.info("Estratégia por ID selecionada");
                return idStrategy;
            }
        }

        // Usa estratégia por múltiplos critérios como fallback
        log.info("Verificando se a estratégia por múltiplos critérios é válida");
        var multiplosCriteriosStrategy = consultaPorMultiplosCriteriosFactory.create(chavePix);
        if (multiplosCriteriosStrategy.estaValido()) {
            log.info("Estratégia por múltiplos critérios selecionada");
            return multiplosCriteriosStrategy;
        }

        log.info("Nenhuma estratégia válida encontrada, usando estratégia por múltiplos critérios");
        return multiplosCriteriosStrategy;
    }
}