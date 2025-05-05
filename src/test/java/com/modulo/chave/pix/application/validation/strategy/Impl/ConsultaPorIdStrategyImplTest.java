package com.modulo.chave.pix.application.validation.strategy.Impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
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
    void deveLancarExcecaoQuandoIdMaisOutrosCriteriosSaoInformados() {
        chavePix.setId(UUID.randomUUID());
        chavePix.setTipoChave(TipoChaveEnum.CPF);
        chavePix.setNumeroAgencia(1234);
        chavePix.setNumeroConta(12345678);
        chavePix.setNomeCorrentista("João");
        chavePix.setDataInclusao(LocalDateTime.now());

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

        when(consultaChavePixPort.buscarPeloId(any(UUID.class))).thenReturn(Optional.of(chaveEsperada));

        List<ChavePix> resultado = consultaPorIdStrategyImpl.execute();

        assertEquals(1, resultado.size());
        assertEquals(chaveEsperada, resultado.get(0));
    }

    @Test
    void deveRetornarListaVaziaQuandoChaveNaoEncontrada() {
        UUID id = UUID.randomUUID();
        chavePix.setId(id);

        when(consultaChavePixPort.buscarPeloId(any(UUID.class))).thenReturn(Optional.empty());

        List<ChavePix> resultado = consultaPorIdStrategyImpl.execute();

        assertEquals(0, resultado.size());
    }
}