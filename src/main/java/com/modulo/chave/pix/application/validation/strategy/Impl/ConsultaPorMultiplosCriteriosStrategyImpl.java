package com.modulo.chave.pix.application.validation.strategy.Impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ConsultaChavePixStrategy;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.port.ConsultaChavePixPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//Padrão: Strategy
// Motivo: Para implementar a consulta por múltiplos critérios de chaves PIX.
@Component
@RequiredArgsConstructor
@Slf4j
public class ConsultaPorMultiplosCriteriosStrategyImpl implements ConsultaChavePixStrategy {

    private final ConsultaChavePixPort consultaChavePixPort;
    private ChavePix chavePix;

    @Override
    public List<ChavePix> execute() {
        log.info("Executando consulta de chaves PIX por múltiplos critérios");
        List<ChavePix> chavesPix = consultaChavePixPort.findByMultiplosCriterios(
                chavePix.getTipoChave(),
                chavePix.getNumeroAgencia(),
                chavePix.getNumeroConta(),
                chavePix.getNomeCorrentista(),
                chavePix.getDataInclusao(),
                chavePix.getDataInativacao());

        log.info("Chaves PIX encontradas: {}", chavesPix);
        return chavesPix;
    }

    @Override
    public boolean estaValido() {
        log.info("Verificando se os critérios de consulta são válidos");
        boolean valido =    chavePix != null && chavePix.getId() == null && (
                            chavePix.getTipoChave() != null ||
                            (chavePix.getNumeroAgencia() != null && chavePix.getNumeroConta() != null) ||
                            chavePix.getNomeCorrentista() != null ||
                            chavePix.getDataInclusao() != null ||
                            chavePix.getDataInativacao() != null);

        log.info("Critérios de consulta válidos: {}", valido);
        return valido;
    }
    
    public ConsultaPorMultiplosCriteriosStrategyImpl comChavePix(ChavePix ChavePix) {
        this.chavePix = ChavePix; //o mesmo que chavePix = novaChavePix (que é o parametro do metodo,so que com nome diferente);
        return this; //this retorna a instância atual do objeto nesse caso a classe ConsultaPorMultiplosCriteriosStrategy
    }
} 