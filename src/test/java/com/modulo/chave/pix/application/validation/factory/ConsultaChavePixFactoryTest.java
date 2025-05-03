package com.modulo.chave.pix.application.validation.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.application.validation.strategy.ConsultaChavePixStrategy;
import com.modulo.chave.pix.application.validation.strategy.Impl.ConsultaPorIdStrategyImpl;
import com.modulo.chave.pix.application.validation.strategy.Impl.ConsultaPorMultiplosCriteriosStrategyImpl;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.port.ConsultaChavePixPort;

@ExtendWith(MockitoExtension.class)
public class ConsultaChavePixFactoryTest {

    @InjectMocks
    private ConsultaChavePixFactory consultaChavePixFactory;

    @Mock
    private ConsultaPorIdStrategyImpl consultaPorIdStrategy;

    @Mock
    private ConsultaChavePixPort consultaChavePixPort;

    @Mock
    private ConsultaPorIdFactory consultaPorIdFactory;

    @Mock
    private ConsultaPorMultiplosCriteriosFactory consultaPorMultiplosCriteriosFactory;

    @Mock
    private ConsultaPorMultiplosCriteriosStrategyImpl consultaPorMultiplosCriteriosStrategy;

    @Test
    public void deveObterSucessoAoCriarEstrategiaPorId() {
        ChavePix chavePix = ChavePix.builder().id(UUID.randomUUID()).build();
        
        when(consultaPorIdFactory.create(chavePix)).thenReturn(consultaPorIdStrategy);
        when(consultaPorIdStrategy.estaValido()).thenReturn(true);

        ConsultaChavePixStrategy estrategia = consultaChavePixFactory.criarEstrategia(chavePix);

        assertNotNull(estrategia);
        assertEquals(ConsultaPorIdStrategyImpl.class, estrategia.getClass());
    }

    @Test
    public void deveObterSucessoAoCriarEstrategiaPorMultiplosCriterios() {
        ChavePix chavePix = ChavePix.builder().tipoChave(TipoChaveEnum.CPF).build();
       
        when(consultaPorMultiplosCriteriosFactory.create(chavePix)).thenReturn(consultaPorMultiplosCriteriosStrategy);
        when(consultaPorMultiplosCriteriosStrategy.estaValido()).thenReturn(true);

        ConsultaChavePixStrategy estrategia = consultaChavePixFactory.criarEstrategia(chavePix);

        assertNotNull(estrategia);
        assertEquals(ConsultaPorMultiplosCriteriosStrategyImpl.class, estrategia.getClass());
    }
}