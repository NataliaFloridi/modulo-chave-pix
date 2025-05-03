package com.modulo.chave.pix.application.usecase;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.modulo.chave.pix.application.usecase.impl.AlteracaoContaPixUseCaseImpl;
import com.modulo.chave.pix.application.validation.strategy.Impl.AlteracaoContaPixValidatorStrategyImpl;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.exception.RegistroNotFoundException;
import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;
import com.modulo.chave.pix.domain.port.AlteracaoChavePixPort;

@ExtendWith(MockitoExtension.class)
public class AlteracaoContaPixUseCaseImplTest {

    @InjectMocks
    private AlteracaoContaPixUseCaseImpl alteracaoContaPixUseCase;

    @Mock
    private AlteracaoChavePixPort alteracaoChavePixPort;

    @Mock
    private AlteracaoContaPixValidatorStrategyImpl alteracaoContaPixValidator;

    @Test
    public void deveObterSucessoAoExecutarAlteracaoContaPix() {
        ChavePix chavePix = criarChavePix();

        when(alteracaoChavePixPort.findById(any())).thenReturn(Optional.of(chavePix));
        when(alteracaoChavePixPort.save(any())).thenReturn(chavePix);

        ChavePix resultado = alteracaoContaPixUseCase.execute(chavePix);

        assertEquals(chavePix, resultado);

        verify(alteracaoChavePixPort, times(1)).findById(any());
        verify(alteracaoChavePixPort, times(1)).save(any());
    }

    @Test
    public void deveObterErroRegistroNotFoundExceptionAoExecutarAlteracaoContaPix() {
        ChavePix chavePix = criarChavePix();

        when(alteracaoChavePixPort.findById(any())).thenReturn(Optional.empty());

        RegistroNotFoundException resultado = assertThrows(RegistroNotFoundException.class, () -> {
            alteracaoContaPixUseCase.execute(chavePix);
        });

        assertEquals("Chave PIX não encontrada pelo ID: " + chavePix.getId(), resultado.getMessage());

        verify(alteracaoChavePixPort, times(1)).findById(any());
        verify(alteracaoChavePixPort, never()).save(any());
    }

    @Test
    public void deveObterErroBusinessValidationExceptionAoExecutarAlteracaoContaPix() {
        ChavePix chavePix = criarChavePix();

        when(alteracaoChavePixPort.findById(any())).thenReturn(Optional.of(chavePix));
        doThrow(new BusinessValidationException("Não é permitido alterar contas inativadas"))
            .when(alteracaoContaPixValidator).validate(any(), any());

        BusinessValidationException resultado = assertThrows(BusinessValidationException.class, () -> {
            alteracaoContaPixUseCase.execute(chavePix);
        });

        assertEquals("Não é permitido alterar contas inativadas", resultado.getMessage());

        verify(alteracaoChavePixPort, times(1)).findById(any());
        verify(alteracaoChavePixPort, never()).save(any());
    }

    @Test
    public void deveObterErroSalvarAoExecutarAlteracaoContaPix() {
        ChavePix chavePix = criarChavePix();

        when(alteracaoChavePixPort.findById(any())).thenReturn(Optional.of(chavePix));
        when(alteracaoChavePixPort.save(any())).thenThrow(new BusinessValidationException("Erro inesperado ao alterar conta pix: Erro ao salvar"));

        BusinessValidationException resultado = assertThrows(BusinessValidationException.class, () -> {
            alteracaoContaPixUseCase.execute(chavePix);
        });

        assertEquals("Erro inesperado ao alterar conta pix: Erro ao salvar", resultado.getMessage());

        verify(alteracaoChavePixPort, times(1)).findById(any());
        verify(alteracaoChavePixPort, times(1)).save(any());
    }

    @Test
    public void deveObterErroValidationExceptionAoExecutarAlteracaoContaPix() {
        ChavePix chavePix = criarChavePix();

        when(alteracaoChavePixPort.findById(any())).thenReturn(Optional.of(chavePix));
        doThrow(new ValidationException("Erro de validação"))
            .when(alteracaoContaPixValidator).validate(any(), any());

        ValidationException resultado = assertThrows(ValidationException.class, () -> {
            alteracaoContaPixUseCase.execute(chavePix);
        });

        assertEquals("Erro de validação", resultado.getMessage());

        verify(alteracaoChavePixPort, times(1)).findById(any());
        verify(alteracaoChavePixPort, never()).save(any());
    }

    @Test
    public void deveObterErroInesperadoAoExecutarAlteracaoContaPix() {
        ChavePix chavePix = criarChavePix();

        when(alteracaoChavePixPort.findById(any())).thenReturn(Optional.of(chavePix));
        doThrow(new RuntimeException("Erro inesperado"))
            .when(alteracaoContaPixValidator).validate(any(), any());

        BusinessValidationException resultado = assertThrows(BusinessValidationException.class, () -> {
            alteracaoContaPixUseCase.execute(chavePix);
        });

        assertEquals("Erro inesperado ao alterar conta pix: Erro inesperado", resultado.getMessage());

        verify(alteracaoChavePixPort, times(1)).findById(any());
        verify(alteracaoChavePixPort, never()).save(any());
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