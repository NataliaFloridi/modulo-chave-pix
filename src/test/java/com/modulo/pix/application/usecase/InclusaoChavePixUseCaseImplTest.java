package com.modulo.pix.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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

import com.modulo.chave.pix.application.usecase.impl.InclusaoChavePixUseCaseImpl;
import com.modulo.chave.pix.application.validation.factory.ChavePixValidationFactory;
import com.modulo.chave.pix.application.validation.strategy.ChavePixRegraValidatorStrategy;
import com.modulo.chave.pix.application.validation.strategy.ChavePixTipoValidatorStrategy;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;
import com.modulo.chave.pix.domain.port.InclusaoChavePixPort;

@ExtendWith(MockitoExtension.class)
public class InclusaoChavePixUseCaseImplTest {
    
    @InjectMocks
    private InclusaoChavePixUseCaseImpl inclusaoChavePixUseCase;

    @Mock
    private ChavePixValidationFactory validationFactory;

    @Mock
    private List<ChavePixRegraValidatorStrategy> regraValidators;

    @Mock
    private InclusaoChavePixPort chavePixPort;

    @Mock
    private ChavePixTipoValidatorStrategy validator;

    @Test
    public void deveObterSucessoAoExecutarInclusaoChavePix() {
        ChavePix chavePix = criarChavePixBasica();
        ChavePix chaveSalva = criarChavePixCompleta();

        when(validationFactory.getTipoChave(any())).thenReturn(validator);
        when(validator.validate(any())).thenReturn(true);
        when(chavePixPort.save(any())).thenReturn(chaveSalva);
        when(regraValidators.iterator()).thenReturn(List.<ChavePixRegraValidatorStrategy>of().iterator());

        ChavePix resultado = inclusaoChavePixUseCase.execute(chavePix);
        
        assertEquals(chaveSalva, resultado);
        verify(chavePixPort, times(1)).save(any(ChavePix.class));
    }

    @Test
    public void deveObterErroValidationExceptionAoExecutarInclusaoChavePix() {
        ChavePix chavePix = criarChavePixBasica();

        when(validationFactory.getTipoChave(any())).thenReturn(validator);
        when(validator.validate(any())).thenReturn(false);

        ValidationException e = assertThrows(ValidationException.class, 
            () -> inclusaoChavePixUseCase.execute(chavePix));
        
        assertEquals("Validação da chave falhou", e.getMessage());
        verify(chavePixPort, never()).save(any(ChavePix.class));
    }

    @Test
    public void deveObterErroBusinessValidationExceptionAoExecutarInclusaoChavePix() {
        ChavePix chavePix = criarChavePixBasica();
        ChavePixRegraValidatorStrategy regraValidator = mock(ChavePixRegraValidatorStrategy.class);

        when(validationFactory.getTipoChave(any())).thenReturn(validator);
        when(validator.validate(any())).thenReturn(true);
        when(regraValidators.iterator()).thenReturn(List.of(regraValidator).iterator());
        doThrow(new BusinessValidationException("Erro de regra de negócio")).when(regraValidator).validate(any());

        BusinessValidationException e = assertThrows(BusinessValidationException.class, 
            () -> inclusaoChavePixUseCase.execute(chavePix));
        
        assertEquals("Erro de regra de negócio", e.getMessage());
        verify(chavePixPort, never()).save(any(ChavePix.class));
    }

    @Test
    public void deveObterErroSalvarAoExecutarInclusaoChavePix() {
        ChavePix chavePix = criarChavePixBasica();

        when(validationFactory.getTipoChave(any())).thenReturn(validator);
        when(validator.validate(any())).thenReturn(true);
        when(regraValidators.iterator()).thenReturn(List.<ChavePixRegraValidatorStrategy>of().iterator());
        when(chavePixPort.save(any())).thenThrow(new RuntimeException("Erro ao salvar"));

        BusinessValidationException e = assertThrows(BusinessValidationException.class, 
            () -> inclusaoChavePixUseCase.execute(chavePix));
        
        assertEquals("Erro inesperado ao criar chave pix: Erro ao salvar", e.getMessage());
        verify(chavePixPort, times(1)).save(any(ChavePix.class));
    }

    private ChavePix criarChavePixBasica() {
        return ChavePix.builder()
                .tipoChave(TipoChaveEnum.CPF)
                .valorChave("12345678900")
                .build();
    }

    private ChavePix criarChavePixCompleta() {
        return ChavePix.builder()
                .id(UUID.randomUUID())
                .tipoChave(TipoChaveEnum.CPF)
                .valorChave("12345678900")
                .tipoConta(TipoContaEnum.CORRENTE)
                .numeroAgencia("1234")
                .numeroConta("123456")
                .nomeCorrentista("João")
                .sobrenomeCorrentista("Silva")
                .dataInclusao(LocalDateTime.now())
                .build();
    }
}
