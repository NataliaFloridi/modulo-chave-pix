package com.modulo.pix.application.factory;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.modulo.chave.pix.application.validation.factory.ConsultaChavePixFactory;
import com.modulo.chave.pix.application.validation.factory.ConsultaPorIdFactory;
import com.modulo.chave.pix.application.validation.factory.ConsultaPorMultiplosCriteriosFactory;
import com.modulo.chave.pix.application.validation.strategy.Impl.ConsultaPorIdStrategyImpl;
import com.modulo.chave.pix.application.validation.strategy.Impl.ConsultaPorMultiplosCriteriosStrategyImpl;
import com.modulo.chave.pix.domain.exception.ValidationException;
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
    void deveRetornarErroValidacaoAoInformarIdEOutrosCriterios() {
        ChavePix chavePix = ChavePix.builder()
            .id(UUID.randomUUID())
            .tipoChave(TipoChaveEnum.CPF)
            .numeroAgencia("0000")
            .numeroConta("00000000000")
            .nomeCorrentista("João da Silva")
            .dataInclusao(LocalDateTime.now())
            .dataInativacao(LocalDateTime.now())
            .build();

        assertThrows(ValidationException.class, () -> consultaChavePixFactory.criarEstrategia(chavePix));
    }
}