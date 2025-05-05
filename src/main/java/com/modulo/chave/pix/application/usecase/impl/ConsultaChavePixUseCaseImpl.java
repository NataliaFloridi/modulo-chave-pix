package com.modulo.chave.pix.application.usecase.impl;

import java.util.List;
import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.usecase.ConsultaChavePixUseCase;
import com.modulo.chave.pix.application.validation.factory.ConsultaChavePixFactory;
import com.modulo.chave.pix.domain.model.ChavePix;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsultaChavePixUseCaseImpl implements ConsultaChavePixUseCase {

    private final ConsultaChavePixFactory strategyFactory;

    @Override
    public List<ChavePix> execute(ChavePix chavePix) {
        log.info("Executando consulta de chaves PIX");
        
        // Cria a estratégia apropriada e executa estratégia
        var strategy = strategyFactory.criarEstrategia(chavePix);
        var chavesPixList = strategy.execute();
  
        log.info("Consulta de chaves PIX concluída. Encontradas {} chaves", chavesPixList.size());
        return chavesPixList;
    }
}
