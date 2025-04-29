package com.modulo.chave.pix.application.usecase.impl;

import java.util.List;
import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.usecase.AlteracaoContaPixUseCase;
import com.modulo.chave.pix.application.validation.strategy.ChavePixRegraValidatorStrategy;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.port.ChavePixPort;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class AlteracaoContaPixUseCaseImpl implements AlteracaoContaPixUseCase {

    private final ChavePixPort chavePixPort;
    private final List<ChavePixRegraValidatorStrategy> regraValidators;

    @Override
    public ChavePix execute(ChavePix chavePix) throws ValidationException, BusinessValidationException {
        try {
            log.info("Iniciando processo de alteração de conta PIX");

            ChavePix chaveExistente = chavePixPort.findByValorChave(chavePix.getValorChave());
            if (chaveExistente == null) {
                throw new BusinessValidationException("Chave PIX não encontrada");
            }

            chavePix.setTipoChave(chaveExistente.getTipoChave());
            chavePix.setValorChave(chaveExistente.getValorChave());
            chavePix.setDataInclusao(chaveExistente.getDataInclusao());
            chavePix.setStatusConta(chaveExistente.isStatusConta());

            log.info("Validando regras de negócio");
            validarRegrasNegocio(chavePix);

            log.info("Persistindo chave PIX");
            ChavePix chaveSalva = chavePixPort.save(chavePix);

            log.info("Conta PIX alterada com sucesso: {}", chaveSalva);
            return chaveSalva;
        } catch (ValidationException e) {
            log.error("Erro de validação ao alterar conta pix: {}", e.getMessage(), e);
            throw e;
        } catch (BusinessValidationException e) {
            log.error("Erro de negócio ao alterar conta pix: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao alterar conta pix: {}", e.getMessage(), e);
            throw new BusinessValidationException("Erro inesperado ao alterar conta pix: " + e.getMessage());
        }
    }

    private void validarRegrasNegocio(ChavePix chavePix) throws BusinessValidationException {
        log.info("Validando regras de negócio");
        for (ChavePixRegraValidatorStrategy validator : regraValidators) {
            validator.validate(chavePix);
        }
    }
}