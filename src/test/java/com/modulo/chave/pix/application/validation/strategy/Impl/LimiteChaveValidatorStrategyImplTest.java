package com.modulo.chave.pix.application.validation.strategy.Impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoPessoaEnum;
import com.modulo.chave.pix.domain.port.InclusaoChavePixPort;

@ExtendWith(MockitoExtension.class)
public class LimiteChaveValidatorStrategyImplTest {

    @Mock
    private InclusaoChavePixPort chavePixPort;

    @InjectMocks
    private LimiteChaveValidatorStrategyImpl limiteChaveValidator;

    @Test
    public void deveObterSucessoAoValidarLimiteChave() {
        ChavePix chavePix = criarChavePixFisica();

        when(chavePixPort.contarPeloNumeroAgenciaEConta(any(), any())).thenReturn(1);

        limiteChaveValidator.validate(chavePix);

        verify(chavePixPort, times(1)).contarPeloNumeroAgenciaEConta(any(), any());
    }

    @Test
    public void deveObterSucessoAoValidarLimiteChaveJuridica() {
        ChavePix chavePix = criarChavePixJuridica();

        when(chavePixPort.contarPeloNumeroAgenciaEConta(any(), any())).thenReturn(1);

        limiteChaveValidator.validate(chavePix);

        verify(chavePixPort, times(1)).contarPeloNumeroAgenciaEConta(any(), any());
    }

    @Test
    public void deveObterErroLimiteChaveExcedidoPessoaFisica() {
        ChavePix chavePix = criarChavePixFisica();

        when(chavePixPort.contarPeloNumeroAgenciaEConta(any(), any())).thenReturn(5);

        assertThrows(BusinessValidationException.class, () -> limiteChaveValidator.validate(chavePix));

        verify(chavePixPort, times(1)).contarPeloNumeroAgenciaEConta(any(), any());
    }

    @Test
    public void deveObterErroLimiteChaveExcedidoPessoaJuridica() {
        ChavePix chavePix = criarChavePixJuridica();

        when(chavePixPort.contarPeloNumeroAgenciaEConta(any(), any())).thenReturn(20);
        assertThrows(BusinessValidationException.class, () -> limiteChaveValidator.validate(chavePix));

        verify(chavePixPort, times(1)).contarPeloNumeroAgenciaEConta(any(), any());
    }

    @Test
    public void deveObterErroLimiteChaveExcedidoPessoaFisicaQuantidadeMaior() {
        ChavePix chavePix = criarChavePixFisica();

        when(chavePixPort.contarPeloNumeroAgenciaEConta(any(), any())).thenReturn(20);

        assertThrows(BusinessValidationException.class, () -> limiteChaveValidator.validate(chavePix));

        verify(chavePixPort, times(1)).contarPeloNumeroAgenciaEConta(any(), any());
    }

    @Test
    public void deveObterErroLimiteChaveExcedidoPessoaJuridicaQuantidadeMaior() {
        ChavePix chavePix = criarChavePixJuridica();

        when(chavePixPort.contarPeloNumeroAgenciaEConta(any(), any())).thenReturn(22);

        assertThrows(BusinessValidationException.class, () -> limiteChaveValidator.validate(chavePix));

        verify(chavePixPort, times(1)).contarPeloNumeroAgenciaEConta(any(), any());
    }

    
    private ChavePix criarChavePixFisica() {
        return ChavePix.builder()
                .tipoPessoa(TipoPessoaEnum.FISICA)
                .build();
    }

    private ChavePix criarChavePixJuridica() {
        return ChavePix.builder()
                .tipoPessoa(TipoPessoaEnum.JURIDICA)
                .build();
    }
}