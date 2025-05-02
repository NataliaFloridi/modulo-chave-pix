package com.modulo.chave.pix.application.validation.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.application.validation.strategy.ConsultaChavePixStrategy;
import com.modulo.chave.pix.application.validation.strategy.Impl.ConsultaPorIdStrategyImpl;
import com.modulo.chave.pix.application.validation.strategy.Impl.ConsultaPorMultiplosCriteriosStrategyImpl;
import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;

@ExtendWith(MockitoExtension.class)
class ConsultaChavePixFactoryTest {

    @Mock
    private ConsultaPorIdFactory consultaPorIdFactory;

    @Mock
    private ConsultaPorMultiplosCriteriosFactory consultaPorMultiplosCriteriosFactory;

    @Mock
    private ConsultaPorIdStrategyImpl idStrategy;

    @Mock
    private ConsultaPorMultiplosCriteriosStrategyImpl multiplosCriteriosStrategy;

    @InjectMocks
    private ConsultaChavePixFactory factory;

    private ChavePix chavePix;

    @BeforeEach
    void setUp() {
        chavePix = ChavePix.builder()
                .id(UUID.randomUUID())
                .tipoChave(TipoChaveEnum.CPF)
                .valorChave("12345678900")
                .numeroAgencia("1234")
                .numeroConta("12345678")
                .nomeCorrentista("João")
                .dataInclusao(LocalDateTime.now())
                .build();
    }

    @Test
    void deveLancarExcecaoQuandoIdEOutrosCriteriosSaoInformados() {
        chavePix.setId(UUID.randomUUID());
        chavePix.setTipoChave(TipoChaveEnum.CPF);
        chavePix.setNumeroAgencia("1234");
        chavePix.setNumeroConta("12345678");
        chavePix.setNomeCorrentista("João");
        chavePix.setDataInclusao(LocalDateTime.now());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            factory.criarEstrategia(chavePix);
        });

        assertEquals("Para consulta por ID, não é permitido informar outros critérios de busca", exception.getMessage());
    }

    @Test
    void deveObterSucessoQuandoEstrategiaPorIdEhValida() {
        chavePix = ChavePix.builder()
                .id(UUID.randomUUID())
                .build();

        when(consultaPorIdFactory.create(chavePix)).thenReturn(idStrategy);
        when(idStrategy.estaValido()).thenReturn(true);

        ConsultaChavePixStrategy strategy = factory.criarEstrategia(chavePix);

        assertEquals(idStrategy, strategy);
    }

    @Test
    void deveObterSucessoQuandoEstrategiaPorMultiplosCriteriosEhValida() {
       chavePix.setId(null);
        when(consultaPorIdFactory.create(chavePix)).thenReturn(idStrategy);
        when(idStrategy.estaValido()).thenReturn(false);
        when(consultaPorMultiplosCriteriosFactory.create(chavePix)).thenReturn(multiplosCriteriosStrategy);
        when(multiplosCriteriosStrategy.estaValido()).thenReturn(true);

        ConsultaChavePixStrategy strategy = factory.criarEstrategia(chavePix);

        assertEquals(multiplosCriteriosStrategy, strategy);
    }
} 