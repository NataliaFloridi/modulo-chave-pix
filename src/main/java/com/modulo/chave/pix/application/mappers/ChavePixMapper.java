package com.modulo.chave.pix.application.mappers;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.dto.request.AlterarChavePixRequest;
import com.modulo.chave.pix.application.dto.request.CriarChavePixRequest;
import com.modulo.chave.pix.application.dto.response.AlterarChavePixResponse;
import com.modulo.chave.pix.application.dto.response.CriarChavePixResponse;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.infrastructure.db.entity.ChavePixEntity;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChavePixMapper {
    
    public CriarChavePixResponse toCriarResponse(ChavePix chavePix) {
        return CriarChavePixResponse.builder()
            .id(chavePix.getId())
            .build();
    }

    public AlterarChavePixResponse toAlterarResponse(ChavePix chavePix) {
        return AlterarChavePixResponse.builder()
            .id(chavePix.getId())
            .tipoChave(chavePix.getTipoChave())
            .valorChave(chavePix.getValorChave())
            .tipoConta(chavePix.getTipoConta())
            .numeroAgencia(chavePix.getNumeroAgencia())
            .numeroConta(chavePix.getNumeroConta())
            .nomeCorrentista(chavePix.getNomeCorrentista())
            .sobrenomeCorrentista(chavePix.getSobrenomeCorrentista())
            .dataInclusao(chavePix.getDataInclusao())
            .build();
    }

    public ChavePixEntity toCriarEntity(ChavePix chavePix) {
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
            .dataInclusao(chavePix.getDataInclusao())
            .build();
        } catch (NumberFormatException e) {
           log.error("Erro ao converter número de agência ou conta para inteiro: {}", e.getMessage());
           throw e;
        }
    }

    public ChavePix toCriarDomain(CriarChavePixRequest request) {
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
                .dataInclusao(LocalDateTime.now())
                .build();
        } catch (Exception e) {
            log.error("Erro ao converter request para domínio: {}", e.getMessage());
            throw e;
        }
    }

    public ChavePix toCriarDomain(ChavePixEntity savedChavePix) {
        return ChavePix.builder()
            .id(savedChavePix.getId())
            .tipoChave(savedChavePix.getTipoChave())
            .valorChave(savedChavePix.getValorChave())
            .tipoConta(savedChavePix.getTipoConta())
            .numeroAgencia(String.valueOf(savedChavePix.getNumeroAgencia()))
            .numeroConta(String.valueOf(savedChavePix.getNumeroConta()))
            .nomeCorrentista(savedChavePix.getNomeCorrentista())
            .sobrenomeCorrentista(savedChavePix.getSobrenomeCorrentista())
            .dataInclusao(savedChavePix.getDataInclusao())
            .build();
    }

    public ChavePix toAlterarDomain(AlterarChavePixRequest request) {
        return ChavePix.builder()
            .id(request.getId())
            .tipoConta(request.getTipoConta())
            .numeroAgencia(request.getNumeroAgencia())
            .numeroConta(request.getNumeroConta())
            .nomeCorrentista(request.getNomeCorrentista())
            .sobrenomeCorrentista(request.getSobrenomeCorrentista())
            .build();
    }

}
