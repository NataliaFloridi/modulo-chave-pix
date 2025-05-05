package com.modulo.chave.pix.application.mappers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.dto.request.AlteracaoChavePixRequest;
import com.modulo.chave.pix.application.dto.request.AlteracaoContaPixRequest;
import com.modulo.chave.pix.application.dto.request.ConsultaChavePixRequest;
import com.modulo.chave.pix.application.dto.request.InclusaoChavePixRequest;
import com.modulo.chave.pix.application.dto.response.AlteracaoChavePixResponse;
import com.modulo.chave.pix.application.dto.response.ConsultaChavePixResponse;
import com.modulo.chave.pix.application.dto.response.InclusaoChavePixResponse;
import com.modulo.chave.pix.application.dto.response.InativacaoChavePixResponse;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.model.enums.TipoPessoaEnum;
import com.modulo.chave.pix.infrastructure.db.entity.ChavePixEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChavePixMapper {

    public InclusaoChavePixResponse toCriarResponse(ChavePix chavePix) {
        return InclusaoChavePixResponse.builder()
                .id(chavePix.getId())
                .build();
    }

    public AlteracaoChavePixResponse toAlterarResponse(ChavePix chavePixAtualizada) {
        return AlteracaoChavePixResponse.builder()
                .id(chavePixAtualizada.getId())
                .tipoChave(chavePixAtualizada.getTipoChave())
                .valorChave(chavePixAtualizada.getValorChave())
                .tipoConta(chavePixAtualizada.getTipoConta())
                .numeroAgencia(chavePixAtualizada.getNumeroAgencia())
                .numeroConta(chavePixAtualizada.getNumeroConta())
                .nomeCorrentista(chavePixAtualizada.getNomeCorrentista())
                .sobrenomeCorrentista(chavePixAtualizada.getSobrenomeCorrentista() != null
                        ? chavePixAtualizada.getSobrenomeCorrentista()
                        : "")
                .dataInclusao(chavePixAtualizada.getDataInclusao())
                .build();
    }

    public ChavePixEntity toEntity(ChavePix chavePix) {
        return ChavePixEntity.builder()
                .id(chavePix.getId())
                .tipoChave(chavePix.getTipoChave())
                .valorChave(chavePix.getValorChave())
                .tipoConta(chavePix.getTipoConta())
                .numeroAgencia(chavePix.getNumeroAgencia())
                .numeroConta(chavePix.getNumeroConta())
                .nomeCorrentista(chavePix.getNomeCorrentista())
                .sobrenomeCorrentista(chavePix.getSobrenomeCorrentista())
                .dataInclusao(chavePix.getDataInclusao())
                .dataInativacao(chavePix.getDataInativacao())
                .tipoPessoa(chavePix.getTipoPessoa())
                .build();
    }

    public ChavePix toCriarDomain(InclusaoChavePixRequest request) {
        try {
            TipoPessoaEnum tipoPessoa = request.getTipoChave() == TipoChaveEnum.CNPJ ? TipoPessoaEnum.JURIDICA
                    : TipoPessoaEnum.FISICA;

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
                    .tipoPessoa(tipoPessoa)
                    .build();
        } catch (Exception e) {
            log.error("Erro ao converter request para domínio: {}", e.getMessage());
            throw e;
        }
    }

    public ChavePix toDomain(ChavePixEntity savedChavePix) {
        return ChavePix.builder()
                .id(savedChavePix.getId())
                .tipoChave(savedChavePix.getTipoChave())
                .valorChave(savedChavePix.getValorChave())
                .tipoConta(savedChavePix.getTipoConta())
                .numeroAgencia(savedChavePix.getNumeroAgencia())
                .numeroConta(savedChavePix.getNumeroConta())
                .nomeCorrentista(savedChavePix.getNomeCorrentista())
                .sobrenomeCorrentista(savedChavePix.getSobrenomeCorrentista())
                .dataInclusao(savedChavePix.getDataInclusao())
                .dataInativacao(savedChavePix.getDataInativacao())
                .tipoPessoa(savedChavePix.getTipoPessoa())
                .build();
    }

    public ChavePix toAlterarDomain(AlteracaoContaPixRequest request) {
        return ChavePix.builder()
                .id(request.getId())
                .tipoConta(request.getTipoConta())
                .numeroAgencia(request.getNumeroAgencia())
                .numeroConta(request.getNumeroConta())
                .nomeCorrentista(request.getNomeCorrentista())
                .sobrenomeCorrentista(request.getSobrenomeCorrentista())
                .build();
    }

    public ChavePix toAlterarDomain(AlteracaoChavePixRequest request) {
        return ChavePix.builder()
                .id(request.getId())
                .tipoConta(request.getTipoConta())
                .numeroAgencia(request.getNumeroAgencia())
                .numeroConta(request.getNumeroConta())
                .nomeCorrentista(request.getNomeCorrentista())
                .sobrenomeCorrentista(request.getSobrenomeCorrentista())
                .tipoChave(request.getTipoChave())
                .valorChave(request.getValorChave())
                .build();
    }

    public ChavePix toAlterarDomain(ConsultaChavePixRequest chavePixRequest) {
        return ChavePix.builder()
                .id(chavePixRequest.getId())
                .tipoChave(chavePixRequest.getTipoChave())
                .numeroAgencia(chavePixRequest.getNumeroAgencia())
                .numeroConta(chavePixRequest.getNumeroConta())
                .nomeCorrentista(chavePixRequest.getNomeCorrentista())
                .dataInclusao(chavePixRequest.getDataInclusao())
                .dataInativacao(chavePixRequest.getDataInativacao())
                .build();
    }

    public ConsultaChavePixResponse toConsultaResponse(ChavePix chavePix) {
        return ConsultaChavePixResponse.builder()
                .id(chavePix.getId())
                .tipoChave(chavePix.getTipoChave())
                .valorChave(chavePix.getValorChave())
                .tipoConta(chavePix.getTipoConta())
                .numeroAgencia(chavePix.getNumeroAgencia())
                .numeroConta(chavePix.getNumeroConta())
                .nomeCorrentista(chavePix.getNomeCorrentista())
                .sobrenomeCorrentista(
                        chavePix.getSobrenomeCorrentista() != null ? chavePix.getSobrenomeCorrentista() : "")
                .dataInclusao(chavePix.getDataInclusao())
                .dataInativacao(chavePix.getDataInativacao())
                .build();
    }

    public List<ConsultaChavePixResponse> toConsultaResponse(List<ChavePix> chavePixList) {
        return chavePixList.stream()
                .map(this::toConsultaResponse)
                // .map(chavePix -> toConsultaResponse(chavePix))
                .collect(Collectors.toList());
    }

    public List<ChavePix> toCriarDomainList(List<ChavePixEntity> chavePixEntityList) {
        return chavePixEntityList.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public InativacaoChavePixResponse toInativacaoResponse(ChavePix chavePix) {
        return InativacaoChavePixResponse.builder()
                .id(chavePix.getId())
                .tipoChave(chavePix.getTipoChave())
                .valorChave(chavePix.getValorChave())
                .tipoConta(chavePix.getTipoConta())
                .numeroAgencia(chavePix.getNumeroAgencia())
                .numeroConta(chavePix.getNumeroConta())
                .nomeCorrentista(chavePix.getNomeCorrentista())
                .sobrenomeCorrentista(
                        chavePix.getSobrenomeCorrentista() != null ? chavePix.getSobrenomeCorrentista() : "")
                .dataInclusao(chavePix.getDataInclusao())
                .dataInativacao(chavePix.getDataInativacao())
                .build();
    }
}
