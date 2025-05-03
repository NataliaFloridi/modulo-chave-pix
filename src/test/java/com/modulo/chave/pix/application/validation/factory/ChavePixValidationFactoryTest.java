package com.modulo.chave.pix.application.validation.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.modulo.chave.pix.application.validation.strategy.ChavePixTipoValidatorStrategy;
import com.modulo.chave.pix.application.validation.strategy.Impl.ChaveAleatoriaValidatorStrategyImpl;
import com.modulo.chave.pix.application.validation.strategy.Impl.ChaveCelularValidatorStrategyImpl;
import com.modulo.chave.pix.application.validation.strategy.Impl.ChaveCnpjValidatorStrategyImpl;
import com.modulo.chave.pix.application.validation.strategy.Impl.ChaveCpfValidatorStrategyImpl;
import com.modulo.chave.pix.application.validation.strategy.Impl.ChaveEmailValidatorStrategyImpl;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;

@ExtendWith(MockitoExtension.class)
public class ChavePixValidationFactoryTest {

    @InjectMocks
    private ChavePixValidationFactory chavePixValidationFactory;

    @Mock
    private ChaveCelularValidatorStrategyImpl chaveCelularValidator;

    @Mock
    private ChaveEmailValidatorStrategyImpl chaveEmailValidator;

    @Mock
    private ChaveCpfValidatorStrategyImpl chaveCpfValidator;

    @Mock
    private ChaveCnpjValidatorStrategyImpl chaveCnpjValidator;

    @Mock
    private ChaveAleatoriaValidatorStrategyImpl chaveAleatoriaValidator;

    @Test
    public void deveObterSucessoAoObterTipoChaveCPF() {
        TipoChaveEnum tipoChave = TipoChaveEnum.CPF;

        ChavePixTipoValidatorStrategy resultado = chavePixValidationFactory.getTipoChave(tipoChave);

        assertNotNull(resultado);
        assertEquals(chaveCpfValidator, resultado);
    }

    @Test
    public void deveObterSucessoAoObterTipoChaveCelular() {
        TipoChaveEnum tipoChave = TipoChaveEnum.CELULAR;

        ChavePixTipoValidatorStrategy resultado = chavePixValidationFactory.getTipoChave(tipoChave);

        assertNotNull(resultado);
        assertEquals(chaveCelularValidator, resultado);
    }

    @Test
    public void deveObterSucessoAoObterTipoChaveEmail() {
        TipoChaveEnum tipoChave = TipoChaveEnum.EMAIL;

        ChavePixTipoValidatorStrategy resultado = chavePixValidationFactory.getTipoChave(tipoChave);

        assertNotNull(resultado);
        assertEquals(chaveEmailValidator, resultado);
    }

    @Test
    public void deveObterSucessoAoObterTipoChaveCNPJ() {
        TipoChaveEnum tipoChave = TipoChaveEnum.CNPJ;

        ChavePixTipoValidatorStrategy resultado = chavePixValidationFactory.getTipoChave(tipoChave);

        assertNotNull(resultado);
        assertEquals(chaveCnpjValidator, resultado);
    }

    @Test
    public void deveObterSucessoAoObterTipoChaveAleatorio() {
        TipoChaveEnum tipoChave = TipoChaveEnum.ALEATORIO;

        ChavePixTipoValidatorStrategy resultado = chavePixValidationFactory.getTipoChave(tipoChave);

        assertNotNull(resultado);
        assertEquals(chaveAleatoriaValidator, resultado);
    }

    @Test
    public void deveObterErroTipoInvalidoAoObterTipoChave() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> chavePixValidationFactory.getTipoChave(TipoChaveEnum.valueOf("INVALIDO")));
        
        assertEquals("No enum constant com.modulo.chave.pix.domain.model.enums.TipoChaveEnum.INVALIDO", exception.getMessage());
    }
}
