package com.modulo.pix.application.validation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.application.validation.strategy.Impl.LimiteChaveValidatorStrategyImpl;
import com.modulo.chave.pix.domain.port.InclusaoChavePixPort;

@ExtendWith(MockitoExtension.class)
public class LimiteChaveValidatorTest{

    @Mock
    private InclusaoChavePixPort chavePixPort;

    @InjectMocks
    private LimiteChaveValidatorStrategyImpl limiteChaveValidator;
}