package com.modulo.pix.application.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.application.validation.strategy.Impl.ChaveAleatoriaValidatorStrategyImpl;
import com.modulo.chave.pix.domain.exception.ValidationException;

@ExtendWith(MockitoExtension.class)
public class ChaveAleatoriaValidatorStrategyImplTest {
    private ChaveAleatoriaValidatorStrategyImpl chaveAleatoriaValidator;

    @BeforeEach
    void setUp() {
        chaveAleatoriaValidator = new ChaveAleatoriaValidatorStrategyImpl();
    }

    @Test
    public void deveObterSucessoAoValidarChaveAleatoria() {
        String chaveAleatoria = "1234567890";
        assertDoesNotThrow(() -> chaveAleatoriaValidator.validate(chaveAleatoria));
    }

    @Test
    public void deveLancarExcecaoQuandoChaveAleatoriaInvalida() {
        String chaveAleatoria = "1234567890123456789012345678901234567890";
        assertThrows(ValidationException.class, () -> chaveAleatoriaValidator.validate(chaveAleatoria));
    }

}
