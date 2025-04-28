package com.modulo.chave.pix.application.validation.strategy;

import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;

//Padrão: Strategy
//Tem uma interface comum (RegraValidator) que define um contrato (validate).
//Depois cria-se  implementações específicas (LimiteContaValidator, ChaveExistenteValidator) 
//que seguem esse contrato, mas aplicam estratégias diferentes de validação.
//Motivo: Para implementar as diferentes validações de chaves (celular, email, CPF). Cada tipo de validação terá sua própria estratégia implementando uma interface comum.
public interface ChavePixRegraValidatorStrategy {
    void validate(ChavePix chavePix) throws BusinessValidationException;
}
