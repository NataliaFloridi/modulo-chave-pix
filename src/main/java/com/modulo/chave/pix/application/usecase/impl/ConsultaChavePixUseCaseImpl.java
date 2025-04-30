package com.modulo.chave.pix.application.usecase.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.dto.request.ConsultaChavePixRequest;
import com.modulo.chave.pix.application.dto.response.ConsultaChavePixResponse;
import com.modulo.chave.pix.application.usecase.ConsultaChavePixUseCase;
import com.modulo.chave.pix.application.validation.factory.ConsultaChavePixStrategyFactory;
import com.modulo.chave.pix.domain.model.ChavePix;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementação do caso de uso de consulta de chaves PIX.
 * 
 * Padrão: Use Case
 * Motivo: Para implementar o caso de uso de consulta de chaves PIX.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ConsultaChavePixUseCaseImpl implements ConsultaChavePixUseCase {

    private final ConsultaChavePixStrategyFactory strategyFactory;

    @Override
    public List<ChavePix> execute(ChavePix chavePix) {
        log.info("Executando consulta de chaves PIX");
        
        // Cria a estratégia apropriada
        var strategy = strategyFactory.createStrategy(chavePix);
        
        // Executa a estratégia
        List<ChavePix> chavesPixList = strategy.execute();
  
        
        log.info("Consulta de chaves PIX concluída. Encontradas {} chaves", chavesPixList.size());
        return chavesPixList;
    }
}
