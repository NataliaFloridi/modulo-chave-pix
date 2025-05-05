package com.modulo.chave.pix.application.usecase.impl;


import java.time.LocalDateTime;

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
    public ChavePix execute(ChavePix novaChavePix) throws ValidationException, BusinessValidationException {
        try {
            log.info("Iniciando processo de alteração de conta PIX");
            var chavePixExistente = alteracaoChavePixPort.buscarPeloId(novaChavePix.getId()).orElseThrow(
                    () -> new RegistroNotFoundException("Chave PIX não encontrada pelo ID: " + novaChavePix.getId()));

            log.info("Atualizando dados da chave PIX");
            var chaveAtualizada = atualizarDadosChavePix(novaChavePix, chavePixExistente);
        
            log.info("Validando regras de negócio");
            alteracaoContaPixValidator.validate(chaveAtualizada, chavePixExistente);

            log.info("Persistindo chave PIX");
            var chaveSalva = alteracaoChavePixPort.salvarAlteracaoChavePix(chaveAtualizada);

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

    private ChavePix atualizarDadosChavePix(ChavePix novaChavePix, ChavePix chavePixExistente) {
        novaChavePix.setId(chavePixExistente.getId());
        novaChavePix.setTipoChave(chavePixExistente.getTipoChave());
        novaChavePix.setValorChave(chavePixExistente.getValorChave());
        //novaChavePix.setDataInclusao(chavePixExistente.getDataInclusao());
        novaChavePix.setDataInclusao(LocalDateTime.now());
        novaChavePix.setDataInativacao(chavePixExistente.getDataInativacao());
        novaChavePix.setTipoPessoa(chavePixExistente.getTipoPessoa());
        log.info("Dados da conta PIX atualizados com sucesso");
        return novaChavePix;
    }
}