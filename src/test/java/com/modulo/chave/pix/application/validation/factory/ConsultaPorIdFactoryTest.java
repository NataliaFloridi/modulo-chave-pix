package com.modulo.chave.pix.application.validation.factory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.application.validation.strategy.Impl.ConsultaPorIdStrategyImpl;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.port.ConsultaChavePixPort;

@ExtendWith(MockitoExtension.class)
public class ConsultaPorIdFactoryTest {
    @Mock
    private ConsultaChavePixPort consultaChavePixPort;

    @InjectMocks
    private ConsultaPorIdFactory consultaPorIdFactory;

    @Test
    public void deveObterSucessoAoCriarEstrategia() {
        ChavePix chavePix = ChavePix.builder()
            .id(UUID.randomUUID())
            .build();

        ConsultaPorIdStrategyImpl consultaPorIdStrategyImpl = consultaPorIdFactory.create(chavePix);

        assertNotNull(consultaPorIdStrategyImpl);
    }
}