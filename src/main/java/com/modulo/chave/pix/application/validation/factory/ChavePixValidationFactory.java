package com.modulo.chave.pix.application.validation.factory;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.ChaveAleatoriaValidator;
import com.modulo.chave.pix.application.validation.ChaveCelularValidator;
import com.modulo.chave.pix.application.validation.ChaveCnpjValidator;
import com.modulo.chave.pix.application.validation.ChaveCpfValidator;
import com.modulo.chave.pix.application.validation.ChaveEmailValidator;
import com.modulo.chave.pix.application.validation.strategy.ChavePixValidatorStrategy;
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

    private final ChaveCelularValidator chaveCelularValidator;
    private final ChaveEmailValidator chaveEmailValidator;
    private final ChaveCpfValidator chaveCpfValidator;
    private final ChaveCnpjValidator chaveCnpjValidator;
    private final ChaveAleatoriaValidator chaveAleatoriaValidator;

    public ChavePixValidatorStrategy getTipoChave(TipoChaveEnum tipoChave) {
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
