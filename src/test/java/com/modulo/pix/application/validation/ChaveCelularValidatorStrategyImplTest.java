package com.modulo.pix.application.validation;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.application.validation.strategy.Impl.ChaveCelularValidatorStrategyImpl;
import com.modulo.chave.pix.domain.exception.ValidationException;

@ExtendWith(MockitoExtension.class)
public class ChaveCelularValidatorStrategyImplTest{
    private ChaveCelularValidatorStrategyImpl chaveCelularValidator;
    private final String MODELO_VALIDO = "+55(11)91234-5678";

    @BeforeEach
    void setUp() {
        chaveCelularValidator = new ChaveCelularValidatorStrategyImpl();
    }

    @Test
    void deveObterSucessoParaCodigoPaisCom1Digito() {
        assertDoesNotThrow(() -> chaveCelularValidator.validate("+1(11)91234-5678"));
    }

    @Test
    void deveObterSucessoParaCodigoPaisCom2Digitos() {
        assertDoesNotThrow(() -> chaveCelularValidator.validate(MODELO_VALIDO));
    }

    @Test
    void deveLancarExcecaoParaCodigoPaisCom3Digitos() {
        assertThrows(ValidationException.class, 
            () -> chaveCelularValidator.validate("+123(11)91234-5678"));
    }

    @Test
    void deveLancarExcecaoParaCodigoPaisNaoNumerico() {
        assertThrows(ValidationException.class, 
            () -> chaveCelularValidator.validate("+XX(11)91234-5678"));
    }

    @Test
    void deveObterSucessoParaDDDCom1Digito() {
        assertDoesNotThrow(() -> chaveCelularValidator.validate("+55(1)91234-5678"));
    }

    @Test
    void deveObterSucessoParaDDDCom3Digitos() {
        assertDoesNotThrow(() -> chaveCelularValidator.validate("+55(111)91234-5678"));
    }

    @Test
    void deveLancarExcecaoParaDDDCom4Digitos() {
        assertThrows(ValidationException.class, 
            () -> chaveCelularValidator.validate("+55(1111)91234-5678"));
    }

    @Test
    void deveLancarExcecaoParaDDDNaoNumerico() {
        assertThrows(ValidationException.class, 
            () -> chaveCelularValidator.validate("+55(XX)91234-5678"));
    }

    @Test
    void deveLancarExcecaoParaNumeroCom8Digitos() {
        assertThrows(ValidationException.class, 
            () -> chaveCelularValidator.validate("+55(11)9123-4567"));
    }

    @Test
    void deveLancarExcecaoParaNumeroCom10Digitos() {
        assertThrows(ValidationException.class, 
            () -> chaveCelularValidator.validate("+55(11)912345-6789"));
    }

    @Test
    void deveLancarExcecaoParaNumeroNaoNumerico() {
        assertThrows(ValidationException.class, 
            () -> chaveCelularValidator.validate("+55(11)9ABCD-5678"));
    }

    @Test
    void deveValidarFormatoCorreto() {
        assertDoesNotThrow(() -> chaveCelularValidator.validate(MODELO_VALIDO));
    }

    @Test
    void deveLancarExcecaoParaFormatoSemParenteses() {
        assertThrows(ValidationException.class, 
            () -> chaveCelularValidator.validate("+551191234-5678"));
    }

    @Test
    void deveLancarExcecaoParaFormatoSemHifen() {
        assertThrows(ValidationException.class, 
            () -> chaveCelularValidator.validate("+55(11)912345678"));
    }
}
