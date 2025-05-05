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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.application.usecase.impl.AlteracaoChavePixUseCaseImpl;
import com.modulo.chave.pix.application.validation.factory.ChavePixValidationFactory;
import com.modulo.chave.pix.application.validation.strategy.ChavePixRegraValidatorStrategy;
import com.modulo.chave.pix.application.validation.strategy.ChavePixTipoValidatorStrategy;
import com.modulo.chave.pix.application.validation.strategy.Impl.AlteracaoChavePixValidatorStrategyImpl;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.exception.RegistroNotFoundException;
import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;
import com.modulo.chave.pix.domain.port.AlteracaoChavePixPort;

@ExtendWith(MockitoExtension.class)
public class AlteracaoChavePixUseCaseImplTest {

    @InjectMocks
    private AlteracaoChavePixUseCaseImpl alteracaoChavePixUseCase;

    @Mock
    private AlteracaoChavePixPort alteracaoChavePixPort;

    @Mock
    private AlteracaoChavePixValidatorStrategyImpl alteracaoChavePixValidator;

    @Mock
    private ChavePixValidationFactory validationFactory;

    @Mock
    private ChavePixTipoValidatorStrategy validator;

    @Mock
    private List<ChavePixRegraValidatorStrategy> regraValidators;

    @Test
    public void deveObterSucessoAoExecutarAlteracaoChavePix() {
        ChavePix chavePix = criarChavePix();
        ChavePix chaveExistente = criarChavePix();
        ChavePix chaveSalva = criarChavePix();

        when(alteracaoChavePixPort.buscarPeloId(any())).thenReturn(Optional.of(chaveExistente));
        when(validationFactory.getTipoChave(any())).thenReturn(validator);
        when(validator.validate(any())).thenReturn(true);
        when(regraValidators.iterator()).thenReturn(List.<ChavePixRegraValidatorStrategy>of().iterator());
        when(alteracaoChavePixPort.salvarAlteracaoChavePix(any())).thenReturn(chaveSalva);

        ChavePix resultado = alteracaoChavePixUseCase.execute(chavePix);

        assertEquals(chaveSalva, resultado);
        verify(alteracaoChavePixPort, times(1)).buscarPeloId(any());
        verify(alteracaoChavePixPort, times(1)).salvarAlteracaoChavePix(any());
    }

    @Test
    public void deveObterErroRegistroNotFoundExceptionAoExecutarAlteracaoChavePix() {
        ChavePix chavePix = criarChavePix();

        when(alteracaoChavePixPort.buscarPeloId(any())).thenReturn(Optional.empty());

        RegistroNotFoundException resultado = assertThrows(RegistroNotFoundException.class, () -> {
            alteracaoChavePixUseCase.execute(chavePix);
        });

        assertEquals("Chave PIX não encontrada pelo ID: " + chavePix.getId(), resultado.getMessage());

        verify(alteracaoChavePixPort, times(1)).buscarPeloId(any());
        verify(alteracaoChavePixPort, never()).salvarAlteracaoChavePix(any());
    }

    @Test
    public void deveObterErroBusinessValidationExceptionAoExecutarAlteracaoChavePix() {
        ChavePix chavePix = criarChavePix();

        when(alteracaoChavePixPort.buscarPeloId(any())).thenReturn(Optional.of(chavePix));
        doThrow(new BusinessValidationException("Não é permitido alterar contas inativadas"))
            .when(alteracaoChavePixValidator).validate(any(), any());

        BusinessValidationException resultado = assertThrows(BusinessValidationException.class, () -> {
            alteracaoChavePixUseCase.execute(chavePix);
        });

        assertEquals("Não é permitido alterar contas inativadas", resultado.getMessage());

        verify(alteracaoChavePixPort, times(1)).buscarPeloId(any());
        verify(alteracaoChavePixPort, never()).salvarAlteracaoChavePix(any());
    }

    @Test
    public void deveObterErroSalvarAoExecutarAlteracaoChavePix() {
        ChavePix chavePix = criarChavePix();
        ChavePix chaveExistente = criarChavePix();

        when(alteracaoChavePixPort.buscarPeloId(any())).thenReturn(Optional.of(chaveExistente));
        when(validationFactory.getTipoChave(any())).thenReturn(validator);
        when(validator.validate(any())).thenReturn(true);
        when(regraValidators.iterator()).thenReturn(List.<ChavePixRegraValidatorStrategy>of().iterator());
        when(alteracaoChavePixPort.salvarAlteracaoChavePix(any())).thenThrow(new RuntimeException("Erro ao salvar"));

        BusinessValidationException resultado = assertThrows(BusinessValidationException.class, () -> {
            alteracaoChavePixUseCase.execute(chavePix);
        });

        assertEquals("Erro inesperado ao alterar chave pix: Erro ao salvar", resultado.getMessage());

        verify(alteracaoChavePixPort, times(1)).buscarPeloId(any());
        verify(alteracaoChavePixPort, times(1)).salvarAlteracaoChavePix(any());
    }

    @Test
    public void deveObterErroValidationExceptionAoExecutarAlteracaoChavePix() {
        ChavePix chavePix = criarChavePix();

        when(alteracaoChavePixPort.buscarPeloId(any())).thenReturn(Optional.of(chavePix));
        doThrow(new ValidationException("Erro de validação"))
            .when(alteracaoChavePixValidator).validate(any(), any());

        ValidationException resultado = assertThrows(ValidationException.class, () -> {
            alteracaoChavePixUseCase.execute(chavePix);
        });

        assertEquals("Erro de validação", resultado.getMessage());

        verify(alteracaoChavePixPort, times(1)).buscarPeloId(any());
        verify(alteracaoChavePixPort, never()).salvarAlteracaoChavePix(any());
    }

    @Test
    public void deveObterErroInesperadoAoExecutarAlteracaoChavePix() {
        ChavePix chavePix = criarChavePix();

        when(alteracaoChavePixPort.buscarPeloId(any())).thenReturn(Optional.of(chavePix));
        doThrow(new RuntimeException("Erro inesperado"))
            .when(alteracaoChavePixValidator).validate(any(), any());

        BusinessValidationException resultado = assertThrows(BusinessValidationException.class, () -> {
            alteracaoChavePixUseCase.execute(chavePix);
        });

        assertEquals("Erro inesperado ao alterar chave pix: Erro inesperado", resultado.getMessage());

        verify(alteracaoChavePixPort, times(1)).buscarPeloId(any());
        verify(alteracaoChavePixPort, never()).salvarAlteracaoChavePix(any());
    }

    @Test
    public void deveObterErroValidacaoChaveAoExecutarAlteracaoChavePix() {
        ChavePix chavePix = criarChavePix();
        ChavePix chaveExistente = criarChavePix();

        when(alteracaoChavePixPort.buscarPeloId(any())).thenReturn(Optional.of(chaveExistente));
        when(validationFactory.getTipoChave(any())).thenReturn(validator);
        when(validator.validate(any())).thenReturn(false);

        ValidationException resultado = assertThrows(ValidationException.class, () -> {
            alteracaoChavePixUseCase.execute(chavePix);
        });

        assertEquals("Validação da chave falhou", resultado.getMessage());

        verify(alteracaoChavePixPort, times(1)).buscarPeloId(any());
        verify(alteracaoChavePixPort, never()).salvarAlteracaoChavePix(any());
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