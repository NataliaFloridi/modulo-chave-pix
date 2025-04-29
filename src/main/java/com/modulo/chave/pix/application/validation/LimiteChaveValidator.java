package com.modulo.chave.pix.application.validation;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.validation.strategy.ChavePixRegraValidatorStrategy;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoPessoaEnum;
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

        TipoPessoaEnum tipoPessoa = buscarTipoPessoa(chavePix.getNumeroAgencia(), chavePix.getNumeroConta());

        int quantidadeChaves = chavePixPort.countByNumeroAgenciaAndNumeroConta(
                chavePix.getNumeroAgencia(),
                chavePix.getNumeroConta());

        if (validarLimiteChave(tipoPessoa, quantidadeChaves)) {
            log.error("Limite de chaves excedido");
            throw new BusinessValidationException("Limite de chaves excedido");
        }
    }

    private TipoPessoaEnum buscarTipoPessoa(String numeroAgencia, String numeroConta) {
        return chavePixPort.findByNumeroAgenciaAndNumeroConta(numeroAgencia, numeroConta);
    }

    private boolean validarLimiteChave(TipoPessoaEnum tipoPessoa, int quantidadeChaves) {
        if (tipoPessoa.equals(TipoPessoaEnum.FISICA) && quantidadeChaves >= LIMITE_CHAVE_PF) {
            return true;
        }
        if (tipoPessoa.equals(TipoPessoaEnum.JURIDICA) && quantidadeChaves >= LIMITE_CHAVE_PJ) {
            return true;
        }
        return false;
    }
}