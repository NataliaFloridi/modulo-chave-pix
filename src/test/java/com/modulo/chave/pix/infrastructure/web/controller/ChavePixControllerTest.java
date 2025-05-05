package com.modulo.chave.pix.infrastructure.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import com.modulo.chave.pix.application.dto.request.AlteracaoChavePixRequest;
import com.modulo.chave.pix.application.dto.request.AlteracaoContaPixRequest;
import com.modulo.chave.pix.application.dto.request.ConsultaChavePixRequest;
import com.modulo.chave.pix.application.dto.request.InclusaoChavePixRequest;
import com.modulo.chave.pix.application.dto.response.AlteracaoChavePixResponse;
import com.modulo.chave.pix.application.dto.response.ConsultaChavePixResponse;
import com.modulo.chave.pix.application.dto.response.InativacaoChavePixResponse;
import com.modulo.chave.pix.application.dto.response.InclusaoChavePixResponse;
import com.modulo.chave.pix.application.mappers.ChavePixMapper;
import com.modulo.chave.pix.application.usecase.AlteracaoChavePixUseCase;
import com.modulo.chave.pix.application.usecase.AlteracaoContaPixUseCase;
import com.modulo.chave.pix.application.usecase.ConsultaChavePixUseCase;
import com.modulo.chave.pix.application.usecase.InativacaoChavePixUseCase;
import com.modulo.chave.pix.application.usecase.InclusaoChavePixUseCase;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;

@ExtendWith(MockitoExtension.class)
public class ChavePixControllerTest {

    @InjectMocks
    private ChavePixController chavePixController;

    @Mock
    private InclusaoChavePixUseCase inclusaoChavePixUseCase;

    @Mock
    private AlteracaoChavePixUseCase alteracaoChavePixUseCase;

    @Mock
    private AlteracaoContaPixUseCase alteracaoContaPixUseCase;

    @Mock
    private ConsultaChavePixUseCase consultaChavePixUseCase;

    @Mock
    private InativacaoChavePixUseCase inativacaoChavePixUseCase;
        
    @Mock
    private ChavePixMapper chavePixMapper;

    @Test
    public void deveObterSucessoAoIncluirChavePix() {
        var chavePix = criarChavePix();
        InclusaoChavePixRequest request = InclusaoChavePixRequest.builder()
                .tipoChave(TipoChaveEnum.CPF)
                .valorChave("1234567890")
                .build();
        InclusaoChavePixResponse response = InclusaoChavePixResponse.builder()
                .id(UUID.randomUUID())
                .build();

        when(chavePixMapper.toCriarDomain(request)).thenReturn(chavePix);
        when(inclusaoChavePixUseCase.execute(any())).thenReturn(chavePix);
        when(chavePixMapper.toCriarResponse(any())).thenReturn(response);

        var responseEntity = chavePixController.incluirChave(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(chavePixMapper, times(1)).toCriarDomain(request);
        verify(inclusaoChavePixUseCase, times(1)).execute(any());
        verify(chavePixMapper, times(1)).toCriarResponse(any());
    }

    @Test
    public void deveObterSucessoAoAlterarChavePix() {
        AlteracaoChavePixRequest request = AlteracaoChavePixRequest.builder()
                .id(UUID.randomUUID())
                .tipoChave(TipoChaveEnum.CPF)
                .valorChave("1234567890")
                .build();
        var chavePix = criarChavePix();
        AlteracaoChavePixResponse response = AlteracaoChavePixResponse.builder()
                .id(UUID.randomUUID())
                .build();

        when(chavePixMapper.toAlterarDomain(request)).thenReturn(chavePix);
        when(alteracaoChavePixUseCase.execute(any())).thenReturn(chavePix);
        when(chavePixMapper.toAlterarResponse(chavePix)).thenReturn(response);

        var responseEntity = chavePixController.atualizarChave(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(chavePixMapper, times(1)).toAlterarDomain(request);
        verify(alteracaoChavePixUseCase, times(1)).execute(any());
        verify(chavePixMapper, times(1)).toAlterarResponse(chavePix);
    }

    @Test
    public void deveObterSucessoAoAlterarContaPix() {
        AlteracaoContaPixRequest request = AlteracaoContaPixRequest.builder()
                .id(UUID.randomUUID())
                .tipoConta(TipoContaEnum.CORRENTE)
                .numeroAgencia(1234)
                .numeroConta(123456)
                .nomeCorrentista("João")
                .sobrenomeCorrentista("Silva")
                .build();
        var chavePix = criarChavePix();
        AlteracaoChavePixResponse response = AlteracaoChavePixResponse.builder()
                .id(UUID.randomUUID())
                .build();

        when(chavePixMapper.toAlterarDomain(request)).thenReturn(chavePix);
        when(alteracaoContaPixUseCase.execute(any())).thenReturn(chavePix);
        when(chavePixMapper.toAlterarResponse(chavePix)).thenReturn(response);

        var responseEntity = chavePixController.atualizarConta(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(chavePixMapper, times(1)).toAlterarDomain(request);
        verify(alteracaoContaPixUseCase, times(1)).execute(any());
        verify(chavePixMapper, times(1)).toAlterarResponse(chavePix);
    }

    @Test
    public void deveObterSucessoAoConsultarChavePix() {
        ConsultaChavePixRequest request = ConsultaChavePixRequest.builder()
                .id(UUID.randomUUID())
                .tipoChave(TipoChaveEnum.CPF)
                .numeroAgencia(1234)
                .numeroConta(123456)
                .nomeCorrentista("João")
                .dataInclusao(LocalDateTime.now())
                .dataInativacao(LocalDateTime.now())
                .build();
        var chavePix = criarChavePix();
        ConsultaChavePixResponse response = ConsultaChavePixResponse.builder()
                .id(UUID.randomUUID())
                .tipoChave(TipoChaveEnum.CPF)
                .numeroAgencia(1234)
                .numeroConta(123456)
                .nomeCorrentista("João")
                .dataInclusao(LocalDateTime.now())
                .dataInativacao(LocalDateTime.now())
                .build();

        when(chavePixMapper.toAlterarDomain(any(ConsultaChavePixRequest.class))).thenReturn(chavePix);
        when(consultaChavePixUseCase.execute(any())).thenReturn(List.of(chavePix));
        when(chavePixMapper.toConsultaResponse(List.of(chavePix))).thenReturn(List.of(response));

        var responseEntity = chavePixController.consultarChavesPix(request.getId().toString(),
                                                                    request.getTipoChave(),
                                                                    request.getNumeroAgencia(),
                                                                    request.getNumeroConta(),
                                                                    request.getNomeCorrentista(),
                                                                    request.getDataInclusao(),
                                                                    request.getDataInativacao());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());

        verify(chavePixMapper, times(1)).toAlterarDomain(any(ConsultaChavePixRequest.class));
        verify(consultaChavePixUseCase, times(1)).execute(any());
        verify(chavePixMapper, times(1)).toConsultaResponse(List.of(chavePix));
    }

    @Test
    public void deveObterErroNaoEncontradoAoConsultarChavePix() {
        ConsultaChavePixRequest request = ConsultaChavePixRequest.builder()
                .id(UUID.randomUUID())
                .tipoChave(TipoChaveEnum.CPF)
                .numeroAgencia(1234)
                .numeroConta(123456)
                .nomeCorrentista("João")
                .dataInclusao(LocalDateTime.now())
                .dataInativacao(LocalDateTime.now())
                .build();
        var chavePix = criarChavePix();

        when(chavePixMapper.toAlterarDomain(any(ConsultaChavePixRequest.class))).thenReturn(chavePix);
        when(consultaChavePixUseCase.execute(any())).thenReturn(List.of());
        when(chavePixMapper.toConsultaResponse(List.of())).thenReturn(List.of());

        var responseEntity = chavePixController.consultarChavesPix(request.getId().toString(),
                                                                    request.getTipoChave(),
                                                                    request.getNumeroAgencia(),
                                                                    request.getNumeroConta(),
                                                                    request.getNomeCorrentista(),
                                                                    request.getDataInclusao(),
                                                                    request.getDataInativacao());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(chavePixMapper, times(1)).toAlterarDomain(any(ConsultaChavePixRequest.class));
        verify(consultaChavePixUseCase, times(1)).execute(any());
        verify(chavePixMapper, times(1)).toConsultaResponse(List.of());
    }

    @Test
    public void deveObterSucessoAoInativarChavePix() {
        UUID id = UUID.randomUUID();
        var chavePix = criarChavePix();
        InativacaoChavePixResponse response = InativacaoChavePixResponse.builder()
                .id(id)
                .build();

        when(inativacaoChavePixUseCase.execute(any())).thenReturn(chavePix);
        when(chavePixMapper.toInativacaoResponse(chavePix)).thenReturn(response);

        var responseEntity = chavePixController.inativarChavePix(id.toString());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(inativacaoChavePixUseCase, times(1)).execute(any());
        verify(chavePixMapper, times(1)).toInativacaoResponse(chavePix);
    }

    private ChavePix criarChavePix() {
        return ChavePix.builder()
                .id(UUID.randomUUID())
                .tipoChave(TipoChaveEnum.CPF)
                .valorChave("12345678900")
                .tipoConta(TipoContaEnum.CORRENTE)
                .numeroAgencia(1234)
                .numeroConta(123456)
                .nomeCorrentista("João")
                .sobrenomeCorrentista("Silva")
                .dataInclusao(LocalDateTime.now())
                .build();
    }
}
