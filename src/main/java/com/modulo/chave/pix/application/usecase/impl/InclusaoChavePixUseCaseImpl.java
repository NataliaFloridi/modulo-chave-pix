package com.modulo.chave.pix.application.usecase.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.usecase.InclusaoChavePixUseCase;
import com.modulo.chave.pix.application.validation.factory.ChavePixValidationFactory;
import com.modulo.chave.pix.application.validation.strategy.ChavePixRegraValidatorStrategy;
import com.modulo.chave.pix.application.validation.strategy.ContasValidatorStrategy;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.port.ChavePixPort;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class InclusaoChavePixUseCaseImpl implements InclusaoChavePixUseCase {

    private final ChavePixPort chavePixPort;
    private final ChavePixValidationFactory validationFactory;
    private final List<ChavePixRegraValidatorStrategy> regraValidators;
    private final List<ContasValidatorStrategy> contasValidators;

    public ChavePix execute(ChavePix chavePix)
            throws ValidationException, BusinessValidationException {
        try {
            log.info("Iniciando processo de criação de chave PIX");
            if (validationFactory.getTipoChave(chavePix.getTipoChave()).validate(chavePix.getValorChave())) {
                throw new ValidationException("Validação da chave falhou");
            }

            log.info("Validando dados da conta");
            validarDadosConta(chavePix.getNumeroAgencia(), chavePix.getNumeroConta());

            log.info("Criando entidade");
            ChavePix novaChavePix = criarChavePix(chavePix);

            log.info("Validando regras de negócio");
            validarRegrasNegocio(novaChavePix);

            log.info("Persistindo chave PIX");
            ChavePix chaveSalva = chavePixPort.save(novaChavePix);

            log.info("Chave PIX criada com sucesso");
            return chaveSalva;
        } catch (Exception e) {
            log.error("Erro ao criar chave pix", e);
            throw new BusinessValidationException("Erro ao criar chave pix");
        }
    }

    private void validarRegrasNegocio(ChavePix chavePix) throws BusinessValidationException {
        log.info("Validando regras de negócio");
        for (ChavePixRegraValidatorStrategy validator : regraValidators) {
            validator.validate(chavePix);
        }
    }

    private void validarDadosConta(Integer numeroAgencia, Integer numeroConta) throws BusinessValidationException {
        log.info("Validando dados da conta");
        for (ContasValidatorStrategy validator : contasValidators) {
            validator.validate(numeroAgencia);
            validator.validate(numeroConta);
        }
    }

    private ChavePix criarChavePix(ChavePix chavePix) {
        log.info("Criando entidade");
        return ChavePix.builder()
                .id(UUID.randomUUID())
                .tipoChave(chavePix.getTipoChave())
                .valorChave(chavePix.getValorChave())
                .tipoConta(chavePix.getTipoConta())
                .numeroAgencia(chavePix.getNumeroAgencia())
                .numeroConta(chavePix.getNumeroConta())
                .nomeCorrentista(chavePix.getNomeCorrentista())
                .sobrenomeCorrentista(chavePix.getSobrenomeCorrentista())
                .dataCriacao(LocalDateTime.now())
                .build();
    }
}