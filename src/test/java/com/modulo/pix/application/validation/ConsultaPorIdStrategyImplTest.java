package com.modulo.pix.application.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.application.validation.strategy.Impl.ConsultaPorIdStrategyImpl;
import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.port.ConsultaChavePixPort;

@ExtendWith(MockitoExtension.class)
public class ConsultaPorIdStrategyImplTest {

    @Mock
    private ConsultaChavePixPort consultaChavePixPort;

    @InjectMocks
    private ConsultaPorIdStrategyImpl consultaPorIdStrategyImpl;
    private ChavePix chavePix;

    @BeforeEach
    void setUp() {
        chavePix = new ChavePix();
        consultaPorIdStrategyImpl = new ConsultaPorIdStrategyImpl(consultaChavePixPort, chavePix);
    }

    @Test
    void deveLancarExcecaoQuandoIdNaoEhInformado() {
        chavePix.setId(null);

        assertThrows(ValidationException.class, () -> consultaPorIdStrategyImpl.estaValido());
    }

    @Test
    void deveValidarQuandoIdEhInformado() {
        UUID id = UUID.randomUUID();
        chavePix.setId(id);

        boolean resultado = consultaPorIdStrategyImpl.estaValido();

        assertEquals(true, resultado);
    }

    @Test
    void deveExecutarConsultaComSucesso() {
        UUID id = UUID.randomUUID();
        chavePix.setId(id);
        ChavePix chaveEsperada = new ChavePix();

        when(consultaChavePixPort.findById(any(UUID.class))).thenReturn(Optional.of(chaveEsperada));

        List<ChavePix> resultado = consultaPorIdStrategyImpl.execute();

        assertEquals(1, resultado.size());
        assertEquals(chaveEsperada, resultado.get(0));
    }

    @Test
    void deveRetornarListaVaziaQuandoChaveNaoEncontrada() {
        UUID id = UUID.randomUUID();
        chavePix.setId(id);

        when(consultaChavePixPort.findById(any(UUID.class))).thenReturn(Optional.empty());

        List<ChavePix> resultado = consultaPorIdStrategyImpl.execute();

        assertEquals(0, resultado.size());
    }
}