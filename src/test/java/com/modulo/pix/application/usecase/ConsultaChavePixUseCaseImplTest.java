package com.modulo.pix.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.application.usecase.impl.ConsultaChavePixUseCaseImpl;
import com.modulo.chave.pix.application.validation.factory.ConsultaChavePixFactory;
import com.modulo.chave.pix.application.validation.factory.ConsultaPorIdFactory;
import com.modulo.chave.pix.application.validation.factory.ConsultaPorMultiplosCriteriosFactory;
import com.modulo.chave.pix.application.validation.strategy.ConsultaChavePixStrategy;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;

@ExtendWith(MockitoExtension.class)
public class ConsultaChavePixUseCaseImplTest {

    @InjectMocks
    private ConsultaChavePixUseCaseImpl consultaChavePixUseCase;

    @Mock
    private ConsultaChavePixFactory strategyFactory;

    @Mock
    private ConsultaPorIdFactory consultaPorIdFactory;

    @Mock
    private ConsultaPorMultiplosCriteriosFactory consultaPorMultiplosCriteriosFactory;

    @Mock
    private ConsultaChavePixStrategy consultaChavePixStrategy;
    
    @Test
    public void deveObterSucessoAoExecutarConsultaChavePix() {
        ChavePix chavePix = criarChavePix();

        when(strategyFactory.criarEstrategia(any())).thenReturn(consultaChavePixStrategy);
        when(consultaChavePixStrategy.execute()).thenReturn(List.of(chavePix));

        List<ChavePix> resultado = consultaChavePixUseCase.execute(chavePix);

        assertEquals(1, resultado.size());
        assertEquals(chavePix, resultado.get(0));

        verify(strategyFactory, times(1)).criarEstrategia(any());
        verify(consultaChavePixStrategy, times(1)).execute();
    }

    private ChavePix criarChavePix() {
        return ChavePix.builder()
                .id(UUID.randomUUID())
                .tipoChave(TipoChaveEnum.CPF)
                .valorChave("12345678900")
                .tipoConta(TipoContaEnum.CORRENTE)
                .numeroAgencia("1234")
                .numeroConta("123456")
                .nomeCorrentista("João")
                .sobrenomeCorrentista("Silva")
                .dataInclusao(LocalDateTime.now())
                .build();
    }
    
}