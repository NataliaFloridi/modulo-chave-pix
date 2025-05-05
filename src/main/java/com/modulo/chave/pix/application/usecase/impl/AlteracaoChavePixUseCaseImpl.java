package com.modulo.chave.pix.application.usecase.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.usecase.AlteracaoChavePixUseCase;
import com.modulo.chave.pix.application.validation.factory.ChavePixValidationFactory;
import com.modulo.chave.pix.application.validation.strategy.ChavePixRegraValidatorStrategy;
import com.modulo.chave.pix.application.validation.strategy.Impl.AlteracaoChavePixValidatorStrategyImpl;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.exception.RegistroNotFoundException;
import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.port.AlteracaoChavePixPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class AlteracaoChavePixUseCaseImpl implements AlteracaoChavePixUseCase {

    private final AlteracaoChavePixPort alteracaoChavePixPort;
    private final AlteracaoChavePixValidatorStrategyImpl alteracaoChavePixValidator;
    private final ChavePixValidationFactory validationFactory;
    private final List<ChavePixRegraValidatorStrategy> regraValidators;

    @Override
    public ChavePix execute(ChavePix novaChavePix) throws ValidationException, BusinessValidationException {
        try {
            log.info("Iniciando processo de alteração de chave PIX");
            var chaveExistente = alteracaoChavePixPort.buscarPeloId(novaChavePix.getId()).orElseThrow(
                    () -> new RegistroNotFoundException("Chave PIX não encontrada pelo ID: " + novaChavePix.getId()));
            
            log.info("Atualizando dados da chave PIX");
            var chaveAtualizada = atualizarDadosChavePix(novaChavePix, chaveExistente);

            log.info("Validando regras de negócio");
            alteracaoChavePixValidator.validate(chaveAtualizada, chaveExistente);

            log.info("Validando tipo de chave");
            var validator = validationFactory.getTipoChave(chaveAtualizada.getTipoChave());
            if (!validator.validate(chaveAtualizada.getValorChave())) {
                log.error("Falha na validação da chave: {}", chaveAtualizada.getValorChave());
                throw new ValidationException("Validação da chave falhou");
            }

            log.info("Validando regras de negócio");
            validarRegrasNegocio(chaveAtualizada);

            log.info("Persistindo chave PIX");
            ChavePix chaveSalva = alteracaoChavePixPort.salvarAlteracaoChavePix(chaveAtualizada);

            log.info("Chave PIX alterada com sucesso: {}", chaveSalva);
            return chaveSalva;
        } catch (ValidationException e) {
            log.error("Erro de validação ao alterar chave pix: {}", e.getMessage(), e);
            throw e;
        } catch (BusinessValidationException e) {
            log.error("Erro de negócio ao alterar chave pix: {}", e.getMessage(), e);
            throw e;
        } catch (RegistroNotFoundException e) {
            log.error("Chave PIX não encontrada: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao alterar chave pix: {}", e.getMessage(), e);
            throw new BusinessValidationException("Erro inesperado ao alterar chave pix: " + e.getMessage());
        }
    }
    private void validarRegrasNegocio(ChavePix chavePix) throws BusinessValidationException {
        log.info("Validando regras de negócio");
        for (ChavePixRegraValidatorStrategy validator : regraValidators) {
            validator.validate(chavePix);
        }
    }

    private ChavePix atualizarDadosChavePix(ChavePix novaChavePix, ChavePix chaveExistente) {
        novaChavePix.setId(chaveExistente.getId());
        novaChavePix.setDataInclusao(chaveExistente.getDataInclusao());
        //novaChavePix.setDataInclusao(LocalDateTime.now());
        novaChavePix.setDataInativacao(chaveExistente.getDataInativacao());
        novaChavePix.setTipoPessoa(chaveExistente.getTipoPessoa());
        log.info("Dados da chave PIX atualizados com sucesso");
        return novaChavePix;
    }
}