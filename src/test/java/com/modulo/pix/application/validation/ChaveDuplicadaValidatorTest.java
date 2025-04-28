package com.modulo.pix.application.validation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.application.validation.ChaveDuplicadaValidator;
import com.modulo.chave.pix.domain.port.ChavePixPort;

@ExtendWith(MockitoExtension.class)
public class ChaveDuplicadaValidatorTest {

    @Mock
    private ChavePixPort chavePixPort;

    @InjectMocks
    private ChaveDuplicadaValidator chaveDuplicadaValidator;
    
    
}