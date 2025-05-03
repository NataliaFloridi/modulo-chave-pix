package com.modulo.chave.pix.application.validation.strategy.Impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.domain.exception.ValidationException;

@ExtendWith(MockitoExtension.class)
public class ChaveCnpjValidatorStrategyImplTest {
        private ChaveCnpjValidatorStrategyImpl validator;

        @BeforeEach
        void setUp() {
            validator = new ChaveCnpjValidatorStrategyImpl();
        }
    
        @Test
        void deveObterSucessoAoValidarCnpjValido() {
            String cnpjValido = "54359687000185";
            assertDoesNotThrow(() -> validator.validate(cnpjValido));
        }
    
        @Test
        void deveLancarExcecaoQuandoCnpjTemMaisDe14Digitos() {
            String cnpjInvalido = "12345678901234567890";
            assertThrows(ValidationException.class, () -> validator.validate(cnpjInvalido));
        }
    
        @Test
        void deveLancarExcecaoQuandoCnpjContemLetras() {
            String cnpjInvalido = "1234567890a";
            assertThrows(ValidationException.class, () -> validator.validate(cnpjInvalido));
        }
    
        @Test
        void deveLancarExcecaoQuandoCnpjInvalido() {
            String cnpjInvalido = "11111111111111";
            assertThrows(ValidationException.class, () -> validator.validate(cnpjInvalido));
        }
    
        @Test
        void deveLancarExcecaoQuandoCnpjVazio() {
            String cnpjVazio = "";
            assertThrows(ValidationException.class, () -> validator.validate(cnpjVazio));
        }
}
