package com.modulo.pix.application.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.application.validation.strategy.Impl.ChaveCpfValidatorStrategyImpl;
import com.modulo.chave.pix.domain.exception.ValidationException;

@ExtendWith(MockitoExtension.class)
public class ChaveCpfValidatorStrategyImplTest {
    private ChaveCpfValidatorStrategyImpl validator;

    @BeforeEach
    void setUp() {
        validator = new ChaveCpfValidatorStrategyImpl();
    }

    @Test
    void deveObterSucessoAoValidarCpfValido() {
        String cpfValido = "12345678909";
        assertDoesNotThrow(() -> validator.validate(cpfValido));
    }

    @Test
    void deveLancarExcecaoQuandoCpfTemMaisDe11Digitos() {
        String cpfInvalido = "123456789012";
        assertThrows(ValidationException.class, () -> validator.validate(cpfInvalido));
    }

    @Test
    void deveLancarExcecaoQuandoCpfContemLetras() {
        String cpfInvalido = "1234567890a";
        assertThrows(ValidationException.class, () -> validator.validate(cpfInvalido));
    }

    @Test
    void deveLancarExcecaoQuandoCpfInvalido() {
        String cpfInvalido = "11111111111";
        assertThrows(ValidationException.class, () -> validator.validate(cpfInvalido));
    }

    @Test
    void deveLancarExcecaoQuandoCpfVazio() {
        String cpfVazio = "";
        assertThrows(ValidationException.class, () -> validator.validate(cpfVazio));
    }
}