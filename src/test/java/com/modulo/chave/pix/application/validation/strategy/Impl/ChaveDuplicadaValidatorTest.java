package com.modulo.chave.pix.application.validation.strategy.Impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.domain.exception.DuplicateKeyException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.port.InclusaoChavePixPort;

@ExtendWith(MockitoExtension.class)
public class ChaveDuplicadaValidatorTest {

    @Mock
    private InclusaoChavePixPort chavePixPort;

    @InjectMocks
    private ChaveDuplicadaValidatorStrategyImpl chaveDuplicadaValidator;

    @Test
    public void deveObterErroChaveDuplicada() {
        ChavePix chavePix = criarChavePix();

        when(chavePixPort.existePeloValorChave(any())).thenReturn(true);

        assertThrows(DuplicateKeyException.class, () -> chaveDuplicadaValidator.validate(chavePix));
    
        verify(chavePixPort, times(1)).existePeloValorChave(any());
    }

    @Test
    public void deveObterSucessoAoValidarChaveNaoDuplicada() {
        ChavePix chavePix = criarChavePix();

        when(chavePixPort.existePeloValorChave(any())).thenReturn(false);

        chaveDuplicadaValidator.validate(chavePix);

        verify(chavePixPort, times(1)).existePeloValorChave(any());
    }

    private ChavePix criarChavePix() {
        return ChavePix.builder()
                .id(UUID.randomUUID())
                .valorChave("12345678900")
                .build();
    }
    
}