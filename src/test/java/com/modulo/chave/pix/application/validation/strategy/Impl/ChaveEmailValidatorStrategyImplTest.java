package com.modulo.chave.pix.application.validation.strategy.Impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.domain.exception.ValidationException;

@ExtendWith(MockitoExtension.class)
public class ChaveEmailValidatorStrategyImplTest {
    private ChaveEmailValidatorStrategyImpl chaveEmailValidator;

    @BeforeEach
    void setUp() {
        chaveEmailValidator = new ChaveEmailValidatorStrategyImpl();
    }

    @Test
    public void deveObterSucessoAoValidarChaveEmail() {
        String chaveEmail = "teste@teste.com";
        assertDoesNotThrow(() -> chaveEmailValidator.validate(chaveEmail));
    }

    @Test
    public void deveLancarExcecaoQuandoChaveEmailInvalida() {
        String chaveEmail = "teste@teste";
        assertThrows(ValidationException.class, () -> chaveEmailValidator.validate(chaveEmail));
    }

    @Test
    public void deveLancarExcecaoQuandoChaveEmailVazia() {
        String chaveEmail = "";
        assertThrows(ValidationException.class, () -> chaveEmailValidator.validate(chaveEmail));
    }

    @Test
    public void deveLancarExcecaoQuandoChaveEmailMuitoLonga() {
        String chaveEmail = "ahsbdcujnnnnnnnnnnnnnnnnnnnnnnnnnnscquwiydsqyfdxuywsfdfdfdfdfddddddudscgdws@ddd.com";
        assertThrows(ValidationException.class, () -> chaveEmailValidator.validate(chaveEmail));
    }
}
