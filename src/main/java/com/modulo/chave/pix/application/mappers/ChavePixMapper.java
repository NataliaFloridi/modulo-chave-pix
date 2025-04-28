package com.modulo.chave.pix.application.mappers;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.dto.request.CriarChavePixRequest;
import com.modulo.chave.pix.application.dto.response.CriarChavePixResponse;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.infrastructure.db.entity.ChavePixEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChavePixMapper {
    
    public CriarChavePixResponse domainToResponse(ChavePix chavePix) {
        return CriarChavePixResponse.builder()
            .id(chavePix.getId())
            .dataCriacao(chavePix.getDataCriacao())
            .build();
    }

    public ChavePixEntity domainToEntity(ChavePix chavePix) {
        return ChavePixEntity.builder()
            .id(chavePix.getId())
            .tipoChave(chavePix.getTipoChave())
            .valorChave(chavePix.getValorChave())
            .tipoConta(chavePix.getTipoConta())
            .numeroAgencia(chavePix.getNumeroAgencia())
            .numeroConta(chavePix.getNumeroConta())
            .nomeCorrentista(chavePix.getNomeCorrentista())
            .sobrenomeCorrentista(chavePix.getSobrenomeCorrentista())
            .dataCriacao(chavePix.getDataCriacao())
            .build();
    }

    public ChavePix requestToDomain(CriarChavePixRequest request) {
        return ChavePix.builder()
            .id(UUID.randomUUID())
            .tipoChave(request.getTipoChave())
            .valorChave(request.getValorChave())
            .tipoConta(request.getTipoConta())
            .numeroAgencia(Integer.parseInt(request.getNumeroAgencia()))
            .numeroConta(Integer.parseInt(request.getNumeroConta()))
            .nomeCorrentista(request.getNomeCorrentista())
            .sobrenomeCorrentista(request.getSobrenomeCorrentista())
            .dataCriacao(LocalDateTime.now())
            .build();
    }

    public ChavePix entityToDomain(ChavePixEntity savedChavePix) {
        return ChavePix.builder()
            .id(savedChavePix.getId())
            .tipoChave(savedChavePix.getTipoChave())
            .valorChave(savedChavePix.getValorChave())
            .tipoConta(savedChavePix.getTipoConta())
            .numeroAgencia(savedChavePix.getNumeroAgencia())
            .numeroConta(savedChavePix.getNumeroConta())
            .nomeCorrentista(savedChavePix.getNomeCorrentista())
            .sobrenomeCorrentista(savedChavePix.getSobrenomeCorrentista())
            .dataCriacao(savedChavePix.getDataCriacao())
            .build();
    }

}
