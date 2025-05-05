package com.modulo.chave.pix.application.usecase.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.modulo.chave.pix.application.usecase.InativacaoChavePixUseCase;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.exception.RegistroNotFoundException;
import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.port.InativacaoChavePixPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class InativacaoChavePixUseCaseImpl implements InativacaoChavePixUseCase {

    private final InativacaoChavePixPort inativacaoChavePixPort;

    @Override
    @Transactional
    public ChavePix execute(String id) {
        try {
            log.info("Iniciando processo de inativação de chave PIX");
            UUID uuid;
            try {
                uuid = UUID.fromString(id);
            } catch (IllegalArgumentException e) {
                log.error("ID inválido: {}", id, e);
                throw new BusinessValidationException("ID inválido: " + id);
            }

            log.info("Buscando chave PIX pelo ID: {}", uuid);
            var chavePixExistente = inativacaoChavePixPort.buscarPeloId(uuid).orElseThrow(
                () -> new RegistroNotFoundException("Chave PIX não encontrada pelo ID: " + id));

            if (chavePixExistente.getDataInativacao() != null) {
                log.error("Chave PIX já inativada");
                throw new BusinessValidationException("Chave PIX já inativada");
            }

            log.info("Inativando chave PIX");
            chavePixExistente.setDataInativacao(LocalDateTime.now());
            var chavePixInativada = inativacaoChavePixPort.salvarInativacaoChavePix(chavePixExistente);

            log.info("Chave PIX inativada com sucesso: {}", chavePixInativada);
            return chavePixInativada;
        } catch (ValidationException e) {
            log.error("Erro de validação ao inativar chave pix: {}", e.getMessage(), e);
            throw e;
        } catch (BusinessValidationException e) {
            log.error("Erro de negócio ao inativar chave pix: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Erro ao inativar chave pix: {}", e.getMessage(), e);
            throw new BusinessValidationException("Erro ao inativar chave pix: " + e.getMessage());
        }
    }
}