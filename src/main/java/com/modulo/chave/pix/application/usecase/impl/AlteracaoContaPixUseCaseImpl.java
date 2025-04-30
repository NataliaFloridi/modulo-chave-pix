package com.modulo.chave.pix.application.usecase.impl;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.usecase.AlteracaoContaPixUseCase;
import com.modulo.chave.pix.application.validation.strategy.Impl.AlteracaoContaPixValidatorStrategyImpl;
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
public class AlteracaoContaPixUseCaseImpl implements AlteracaoContaPixUseCase {

    private final AlteracaoChavePixPort alteracaoChavePixPort;
    private final AlteracaoContaPixValidatorStrategyImpl alteracaoContaPixValidator;

    @Override
    public ChavePix execute(ChavePix chavePix) throws ValidationException, BusinessValidationException {
        try {
            log.info("Iniciando processo de alteração de conta PIX");
            ChavePix chaveExistente = alteracaoChavePixPort.findById(chavePix.getId());
            if (chaveExistente == null) {
                throw new RegistroNotFoundException("Chave PIX não encontrada pelo ID: " + chavePix.getId());
            }

            log.info("Validando regras de negócio");
            alteracaoContaPixValidator.validate(chavePix, chaveExistente);

            log.info("Atualizando dados da chave PIX");
            atualizarDadosChavePix(chavePix, chaveExistente);

            log.info("Persistindo chave PIX");
            ChavePix chaveSalva = alteracaoChavePixPort.save(chavePix);

            log.info("Conta PIX alterada com sucesso: {}", chaveSalva);
            return chaveSalva;
        } catch (ValidationException e) {
            log.error("Erro de validação ao alterar conta pix: {}", e.getMessage(), e);
            throw e;
        } catch (BusinessValidationException e) {
            log.error("Erro de negócio ao alterar conta pix: {}", e.getMessage(), e);
            throw e;
        } catch (RegistroNotFoundException e) {
            log.error("Chave PIX não encontrada: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao alterar conta pix: {}", e.getMessage(), e);
            throw new BusinessValidationException("Erro inesperado ao alterar conta pix: " + e.getMessage());
        }
    }

    private void atualizarDadosChavePix(ChavePix chavePix, ChavePix chaveExistente) {
        chavePix.setId(chaveExistente.getId());
        chavePix.setTipoChave(chaveExistente.getTipoChave());
        chavePix.setValorChave(chaveExistente.getValorChave());
        chavePix.setDataInclusao(chaveExistente.getDataInclusao());
        chavePix.setDataInativacao(chaveExistente.getDataInativacao());
        log.info("Dados da conta PIX atualizados com sucesso");
    }
}