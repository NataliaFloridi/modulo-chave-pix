package com.modulo.chave.pix.application.validation;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ChavePixRegraValidatorStrategy;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.port.ChavePixPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class LimiteChaveValidator implements ChavePixRegraValidatorStrategy {

    private static final int LIMITE_CHAVE_PJ = 20;
    private static final int LIMITE_CHAVE_PF = 5;

    private final ChavePixPort chavePixPort;

    @Override
    public void validate(ChavePix chavePix) throws BusinessValidationException {
        log.info("Validando limite de chaves");
        int contaAtual = chavePixPort.countByNumeroAgenciaAndNumeroConta(
            chavePix.getNumeroAgencia(),
            chavePix.getNumeroConta()
        );

        if (pessoaFisica(chavePix.getTipoChave()) && contaAtual >= LIMITE_CHAVE_PF) {
            log.error("Limite de 5 chaves para Pessoa Física excedido");
            throw new BusinessValidationException("Limite de 5 chaves para Pessoa Física excedido");
        }

        if (pessoaJuridica(chavePix.getTipoChave()) && contaAtual >= LIMITE_CHAVE_PJ) {
            log.error("Limite de 20 chaves para Pessoa Jurídica excedido");
            throw new BusinessValidationException("Limite de 20 chaves para Pessoa Jurídica excedido");
        }

    }

    private boolean pessoaFisica(TipoChaveEnum tipoChave) {
        return tipoChave == TipoChaveEnum.CPF || 
               tipoChave == TipoChaveEnum.EMAIL || 
               tipoChave == TipoChaveEnum.CELULAR;
    }

    private boolean pessoaJuridica(TipoChaveEnum tipoChave) {
        return tipoChave == TipoChaveEnum.CNPJ;
    }
}