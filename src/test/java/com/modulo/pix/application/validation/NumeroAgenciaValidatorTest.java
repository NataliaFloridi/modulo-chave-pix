package com.modulo.pix.application.validation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.modulo.chave.pix.application.validation.NumeroAgenciaValidator;
import com.modulo.chave.pix.domain.port.ChavePixPort;

@ExtendWith(MockitoExtension.class)
public class NumeroAgenciaValidatorTest {

    @Mock
    private ChavePixPort chavePixPort;

    @InjectMocks
    private NumeroAgenciaValidator numeroAgenciaValidator;
    
    
}