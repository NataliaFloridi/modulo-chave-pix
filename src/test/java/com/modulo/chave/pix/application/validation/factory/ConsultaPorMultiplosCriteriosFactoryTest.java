package com.modulo.chave.pix.application.validation.factory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.application.validation.strategy.Impl.ConsultaPorMultiplosCriteriosStrategyImpl;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.port.ConsultaChavePixPort;

@ExtendWith(MockitoExtension.class)
public class ConsultaPorMultiplosCriteriosFactoryTest {
    @Mock
    private ConsultaChavePixPort consultaChavePixPort;

    @InjectMocks
    private ConsultaPorMultiplosCriteriosFactory consultaPorMultiplosCriteriosFactory;

    @Test
    public void deveObterSucessoAoCriarEstrategia() {
        ChavePix chavePix = ChavePix.builder()
            .numeroAgencia(0000)
            .numeroConta(00000000000)
            .nomeCorrentista("João da Silva")
            .dataInclusao(LocalDateTime.now())
            .build();

        ConsultaPorMultiplosCriteriosStrategyImpl consultaPorMultiplosCriteriosStrategyImpl = consultaPorMultiplosCriteriosFactory.create(chavePix);

        assertNotNull(consultaPorMultiplosCriteriosStrategyImpl);
    }
} 