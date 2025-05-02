package com.modulo.pix.application.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.application.validation.strategy.Impl.ConsultaPorMultiplosCriteriosStrategyImpl;
import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.port.ConsultaChavePixPort;

@ExtendWith(MockitoExtension.class)
public class ConsultaPorMultiplosCriteriosStrategyImplTest {

    @Mock
    private ConsultaChavePixPort consultaChavePixPort;
    @InjectMocks
    private ConsultaPorMultiplosCriteriosStrategyImpl consultaPorMultiplosCriteriosStrategyImpl;
    
    private ChavePix chavePix;

    @BeforeEach
    void setUp() {
        chavePix = new ChavePix();
        consultaPorMultiplosCriteriosStrategyImpl = new ConsultaPorMultiplosCriteriosStrategyImpl(consultaChavePixPort, chavePix);
    }

    @Test
    void deveLancarExcecaoQuandoDataInclusaoEDataInativacaoSaoInformadasJuntas() {
        chavePix.setDataInclusao(LocalDateTime.now());
        chavePix.setDataInativacao(LocalDateTime.now());

        assertThrows(ValidationException.class, () -> consultaPorMultiplosCriteriosStrategyImpl.estaValido());
    }

    @Test
    void deveLancarExcecaoQuandoAgenciaOuContaSaoInformadasSeparadamente() {
        chavePix.setNumeroAgencia("1234");

        assertThrows(ValidationException.class, () -> consultaPorMultiplosCriteriosStrategyImpl.estaValido());
    }

    @Test
    void deveLancarExcecaoQuandoNenhumCriterioEhInformado() {
        assertThrows(ValidationException.class, () -> consultaPorMultiplosCriteriosStrategyImpl.estaValido());
    }

    @Test
    void deveValidarQuandoApenasTipoChaveEhInformado() {
        chavePix.setTipoChave(TipoChaveEnum.CPF);

        boolean resultado = consultaPorMultiplosCriteriosStrategyImpl.estaValido();

        assertEquals(true, resultado);
    }

    @Test
    void deveValidarQuandoAgenciaEContaSaoInformadasJuntas() {
        chavePix.setNumeroAgencia("1234");
        chavePix.setNumeroConta("5678");

        boolean resultado = consultaPorMultiplosCriteriosStrategyImpl.estaValido();

        assertEquals(true, resultado);
    }

    @Test
    void deveValidarQuandoApenasNomeCorrentistaEhInformado() {
        chavePix.setNomeCorrentista("João Silva");

        boolean resultado = consultaPorMultiplosCriteriosStrategyImpl.estaValido();

        assertEquals(true, resultado);
    }

    @Test
    void deveValidarQuandoApenasDataInclusaoEhInformada() {
        chavePix.setDataInclusao(LocalDateTime.now());

        boolean resultado = consultaPorMultiplosCriteriosStrategyImpl.estaValido();

        assertEquals(true, resultado);
    }

    @Test
    void deveValidarQuandoApenasDataInativacaoEhInformada() {
        chavePix.setDataInativacao(LocalDateTime.now());

        boolean resultado = consultaPorMultiplosCriteriosStrategyImpl.estaValido();

        assertEquals(true, resultado);
    }

    @Test
    void deveExecutarConsultaComSucesso() {
        List<ChavePix> chavesEsperadas = Arrays.asList(new ChavePix(), new ChavePix());
        when(consultaChavePixPort.findByMultiplosCriterios(any(), any(), any(), any(), any(), any()))
                .thenReturn(chavesEsperadas);

        List<ChavePix> resultado = consultaPorMultiplosCriteriosStrategyImpl.execute();

        assertEquals(chavesEsperadas, resultado);
    }
}