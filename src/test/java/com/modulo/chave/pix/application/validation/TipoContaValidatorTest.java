package com.modulo.chave.pix.application.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;

@ExtendWith(MockitoExtension.class)
public class TipoContaValidatorTest {

    @InjectMocks
    private TipoContaValidator tipoContaValidator;

    @Test
    public void deveObterSucessoAoValidarTipoConta() {
        tipoContaValidator.validate(TipoContaEnum.CORRENTE);
    }

    @Test
    public void deveObterErroTipoContaNulo() {
        assertThrows(ValidationException.class, () -> tipoContaValidator.validate(null));
    }

    @Test
    public void deveObterErroTipoContaInvalido() {
        TipoContaEnum tipoContaInvalido = mock(TipoContaEnum.class);
        
        ValidationException exception = assertThrows(ValidationException.class, 
            () -> tipoContaValidator.validate(tipoContaInvalido));
        
        assertEquals("Tipo de conta inválido: " + tipoContaInvalido, exception.getMessage());
    }
}