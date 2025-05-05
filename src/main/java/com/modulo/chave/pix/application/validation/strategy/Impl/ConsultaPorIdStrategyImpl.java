package com.modulo.chave.pix.application.validation.strategy.Impl;

import java.util.Collections;
import java.util.List;

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
        if (chavePix.getId() != null
                && (chavePix.getTipoChave() != null
                        || chavePix.getNumeroAgencia() != null
                        || chavePix.getNumeroConta() != null
                        || chavePix.getNomeCorrentista() != null
                        || chavePix.getDataInclusao() != null
                        || chavePix.getDataInativacao() != null)) {
            log.error("Para consulta por ID, não é permitido informar outros critérios de busca");
            throw new ValidationException("Para consulta por ID, não é permitido informar outros critérios de busca");
        }
        
        log.info("Critério de consulta válido");
        return true;
    }

    @Override
    public List<ChavePix> execute() {
        log.info("Executando consulta de chave PIX por ID: {}", chavePix.getId());

        var chavePixEncontrada = consultaChavePixPort.buscarPeloId(chavePix.getId());

        //essa parte  procura a chave e transforma um Optional<ChavePix> em um List<ChavePix>
        //Se encontrar a chave, retorna uma lista com ela
        //Se não encontrar, retorna uma lista vazia
        return chavePixEncontrada.map(chave -> List.of(chave))
                .orElse(Collections.emptyList());
    }
}