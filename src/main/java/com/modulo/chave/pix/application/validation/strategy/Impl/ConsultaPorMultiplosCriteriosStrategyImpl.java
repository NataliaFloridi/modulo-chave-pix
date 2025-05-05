package com.modulo.chave.pix.application.validation.strategy.Impl;

import java.util.List;

import com.modulo.chave.pix.application.validation.strategy.ConsultaChavePixStrategy;
import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.port.ConsultaChavePixPort;

import lombok.extern.slf4j.Slf4j;

//Padrão: Strategy
// Motivo: Para implementar a consulta por múltiplos critérios de chaves PIX.
@Slf4j
public class ConsultaPorMultiplosCriteriosStrategyImpl implements ConsultaChavePixStrategy {

    private final ConsultaChavePixPort consultaChavePixPort;
    private final ChavePix chavePix;

    public ConsultaPorMultiplosCriteriosStrategyImpl(ConsultaChavePixPort consultaChavePixPort, ChavePix chavePix) {
        this.consultaChavePixPort = consultaChavePixPort;
        this.chavePix = chavePix;
    }

    @Override
    public boolean estaValido() {
        log.info("Verificando se os critérios de consulta são válidos");
        boolean temTipoChave = chavePix.getTipoChave() != null;
        boolean temAgencia = chavePix.getNumeroAgencia() != null;
        boolean temConta = chavePix.getNumeroConta() != null;
        boolean temNomeCorrentista = chavePix.getNomeCorrentista() != null;
        boolean temDataInclusao = chavePix.getDataInclusao() != null;
        boolean temDataInativacao = chavePix.getDataInativacao() != null;

        log.info("Verificando se data de inclusão e data de inativação foram informadas juntas");
        if (temDataInclusao && temDataInativacao) {
            log.error("Não é permitido informar data de inclusão e data de inativação juntas. Use apenas um dos critérios.");
            throw new ValidationException("Não é permitido informar data de inclusão e data de inativação juntas. Use apenas um dos critérios.");
        }

        log.info("Verificando se agência e conta foram informadas juntas");
        if (temAgencia != temConta) {
            log.error("Agência e conta devem ser informadas juntas.");
            throw new ValidationException("Agência e conta devem ser informadas juntas.");
        }

        log.info("Verificando se os critérios de consulta são válidos");
        boolean valido = temTipoChave || 
                        (temAgencia && temConta) || 
                        temNomeCorrentista || 
                        temDataInclusao || 
                        temDataInativacao;

        if (!valido) {
            log.error("É necessário informar pelo menos um critério de busca válido: tipo de chave, agência/conta, nome do correntista, data de inclusão ou data de inativação.");
            throw new ValidationException("É necessário informar pelo menos um critério de busca válido: tipo de chave, agência/conta, nome do correntista, data de inclusão ou data de inativação.");
        }

        log.info("Critérios de consulta válidos: {}", valido);
        return valido;
    }

    @Override
    public List<ChavePix> execute() {
        log.info("Executando consulta por múltiplos critérios");
        return consultaChavePixPort.buscarPorMultiplosCriterios(
                chavePix.getTipoChave(),
                chavePix.getNumeroAgencia(),
                chavePix.getNumeroConta(),
                chavePix.getNomeCorrentista(),
                chavePix.getDataInclusao(),
                chavePix.getDataInativacao());
    }
} 