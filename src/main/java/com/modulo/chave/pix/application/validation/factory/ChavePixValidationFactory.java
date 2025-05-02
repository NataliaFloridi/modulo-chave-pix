package com.modulo.chave.pix.application.validation.factory;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ChavePixTipoValidatorStrategy;
import com.modulo.chave.pix.application.validation.strategy.Impl.ChaveAleatoriaValidatorStrategyImpl;
import com.modulo.chave.pix.application.validation.strategy.Impl.ChaveCelularValidatorStrategyImpl;
import com.modulo.chave.pix.application.validation.strategy.Impl.ChaveCnpjValidatorStrategyImpl;
import com.modulo.chave.pix.application.validation.strategy.Impl.ChaveCpfValidatorStrategyImpl;
import com.modulo.chave.pix.application.validation.strategy.Impl.ChaveEmailValidatorStrategyImpl;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//Factory para validar as chaves pix
//diferentes estratégias de validação
//Motivo: Para implementar as diferentes validações de chaves (celular, email, CPF). Cada tipo de validação terá sua própria estratégia implementando uma interface comum.
@Component
@Slf4j
@RequiredArgsConstructor
public class ChavePixValidationFactory {

    private final ChaveCelularValidatorStrategyImpl chaveCelularValidator;
    private final ChaveEmailValidatorStrategyImpl chaveEmailValidator;
    private final ChaveCpfValidatorStrategyImpl chaveCpfValidator;
    private final ChaveCnpjValidatorStrategyImpl chaveCnpjValidator;
    private final ChaveAleatoriaValidatorStrategyImpl chaveAleatoriaValidator;

    public ChavePixTipoValidatorStrategy getTipoChave(TipoChaveEnum tipoChave) {
        log.info("Obtendo estratégia de validação para o tipo de chave: {}", tipoChave);
        
        switch (tipoChave) {
            case CELULAR:
                log.info("Retornando estratégia de validação para o tipo de chave: {}", tipoChave);
                return chaveCelularValidator;
            case EMAIL:
                log.info("Retornando estratégia de validação para o tipo de chave: {}", tipoChave);
                return chaveEmailValidator;
            case CPF:
                log.info("Retornando estratégia de validação para o tipo de chave: {}", tipoChave);
                return chaveCpfValidator;
            case CNPJ:
                log.info("Retornando estratégia de validação para o tipo de chave: {}", tipoChave);
                return chaveCnpjValidator;
            case ALEATORIO:
                log.info("Retornando estratégia de validação para o tipo de chave: {}", tipoChave);
                return chaveAleatoriaValidator;
            default:
                log.error("Tipo de chave inválido: {}", tipoChave);
                throw new IllegalArgumentException("Tipo de chave inválido");
        }
    }
}
