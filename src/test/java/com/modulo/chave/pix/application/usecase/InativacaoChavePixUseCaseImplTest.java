package com.modulo.chave.pix.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.modulo.chave.pix.application.usecase.impl.InativacaoChavePixUseCaseImpl;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;
import com.modulo.chave.pix.domain.port.InativacaoChavePixPort;

@ExtendWith(MockitoExtension.class)
public class InativacaoChavePixUseCaseImplTest {

    @InjectMocks
    private InativacaoChavePixUseCaseImpl inativacaoChavePixUseCase;

    @Mock
    private InativacaoChavePixPort inativacaoChavePixPort;

    private ChavePix chavePix;

    @BeforeEach
    void setUp() {
        chavePix = criarChavePix();
    }

    @Test
    public void deveObterSucessoAoExecutarInativacaoChavePix() {
        when(inativacaoChavePixPort.buscarPeloId(any())).thenReturn(Optional.of(chavePix));
        when(inativacaoChavePixPort.salvarInativacaoChavePix(any())).thenReturn(chavePix);

        ChavePix resultado = inativacaoChavePixUseCase.execute(chavePix.getId().toString());

        assertNotNull(resultado);
        assertNotNull(resultado.getDataInativacao());
        assertEquals(chavePix, resultado);

        verify(inativacaoChavePixPort, times(1)).buscarPeloId(any());
        verify(inativacaoChavePixPort, times(1)).salvarInativacaoChavePix(any());
    }

    @Test
    public void deveObterErroRegistroNotFoundAoExecutarInativacaoChavePix() {
        String id = chavePix.getId().toString();
        when(inativacaoChavePixPort.buscarPeloId(any())).thenReturn(Optional.empty());

        BusinessValidationException resultado = assertThrows(BusinessValidationException.class, () -> {
            inativacaoChavePixUseCase.execute(id);
        });

        assertEquals("Erro ao inativar chave pix: Chave PIX não encontrada pelo ID: " + id, resultado.getMessage());

        verify(inativacaoChavePixPort, times(1)).buscarPeloId(any());
        verify(inativacaoChavePixPort, never()).salvarInativacaoChavePix(any());
    }

    @Test
    public void deveObterErroBusinessValidationExceptionAoExecutarInativacaoChavePix() {
        chavePix.setDataInativacao(LocalDateTime.now());
        String id = chavePix.getId().toString();
        when(inativacaoChavePixPort.buscarPeloId(any())).thenReturn(Optional.of(chavePix));

        BusinessValidationException resultado = assertThrows(BusinessValidationException.class, () -> {
            inativacaoChavePixUseCase.execute(id);
        });

        assertEquals("Chave PIX já inativada", resultado.getMessage());

        verify(inativacaoChavePixPort, times(1)).buscarPeloId(any());
        verify(inativacaoChavePixPort, never()).salvarInativacaoChavePix(any());
    }

    @Test
    public void deveObterErroSalvarAoExecutarInativacaoChavePix() {
        when(inativacaoChavePixPort.buscarPeloId(any())).thenReturn(Optional.of(chavePix));
        when(inativacaoChavePixPort.salvarInativacaoChavePix(any()))
            .thenThrow(new BusinessValidationException("Erro ao salvar inativação"));

        BusinessValidationException resultado = assertThrows(BusinessValidationException.class, () -> {
            inativacaoChavePixUseCase.execute(chavePix.getId().toString());
        });

        assertEquals("Erro ao salvar inativação", resultado.getMessage());

        verify(inativacaoChavePixPort, times(1)).buscarPeloId(any());
        verify(inativacaoChavePixPort, times(1)).salvarInativacaoChavePix(any());
    }

    @Test
    public void deveObterErroQuandoIdForInvalido() {
        String idInvalido = "id-invalido";

        BusinessValidationException resultado = assertThrows(BusinessValidationException.class, () -> {
            inativacaoChavePixUseCase.execute(idInvalido);
        });

        assertEquals("ID inválido: " + idInvalido, resultado.getMessage());

        verify(inativacaoChavePixPort, never()).buscarPeloId(any());
        verify(inativacaoChavePixPort, never()).salvarInativacaoChavePix(any());
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