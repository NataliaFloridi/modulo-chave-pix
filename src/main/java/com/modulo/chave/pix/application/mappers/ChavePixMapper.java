package com.modulo.chave.pix.application.mappers;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.dto.request.CriarChavePixRequest;
import com.modulo.chave.pix.application.dto.response.CriarChavePixResponse;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.infrastructure.db.entity.ChavePixEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChavePixMapper {
    
    public CriarChavePixResponse domainToResponse(ChavePix chavePix) {
        return CriarChavePixResponse.builder()
            .id(chavePix.getId())
            .dataCriacao(chavePix.getDataCriacao())
            .build();
    }

    public ChavePixEntity domainToEntity(ChavePix chavePix) {
        try {
            return ChavePixEntity.builder()
            .id(chavePix.getId())
            .tipoChave(chavePix.getTipoChave())
            .valorChave(chavePix.getValorChave())
            .tipoConta(chavePix.getTipoConta())
            .numeroAgencia(Integer.parseInt(chavePix.getNumeroAgencia()))
            .numeroConta(Integer.parseInt(chavePix.getNumeroConta()))
            .nomeCorrentista(chavePix.getNomeCorrentista())
            .sobrenomeCorrentista(chavePix.getSobrenomeCorrentista())
            .dataCriacao(chavePix.getDataCriacao())
            .build();
        } catch (IllegalArgumentException e) {
           log.error("Erro ao converter chave pix para entidade: {}", e.getMessage());
           throw e;
        }
        
    }

    public ChavePix requestToDomain(CriarChavePixRequest request) {
        try {
            return ChavePix.builder()
                .id(UUID.randomUUID())
                .tipoChave(request.getTipoChave())
                .valorChave(request.getValorChave())
                .tipoConta(request.getTipoConta())
                .numeroAgencia(request.getNumeroAgencia())
                .numeroConta(request.getNumeroConta())
                .nomeCorrentista(request.getNomeCorrentista())
                .sobrenomeCorrentista(request.getSobrenomeCorrentista())
                .dataCriacao(LocalDateTime.now())
                .build();
        } catch (NumberFormatException e) {
            log.error("Erro ao converter request para domínio: {}", e.getMessage());
            throw e;
        }
    }

    public ChavePix entityToDomain(ChavePixEntity savedChavePix) {
        return ChavePix.builder()
            .id(savedChavePix.getId())
            .tipoChave(savedChavePix.getTipoChave())
            .valorChave(savedChavePix.getValorChave())
            .tipoConta(savedChavePix.getTipoConta())
            .numeroAgencia(savedChavePix.getNumeroAgencia().toString())
            .numeroConta(savedChavePix.getNumeroConta().toString())
            .nomeCorrentista(savedChavePix.getNomeCorrentista())
            .sobrenomeCorrentista(savedChavePix.getSobrenomeCorrentista())
            .dataCriacao(savedChavePix.getDataCriacao())
            .build();
    }

}
