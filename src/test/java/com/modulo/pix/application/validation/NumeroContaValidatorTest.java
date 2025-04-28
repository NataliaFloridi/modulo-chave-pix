package com.modulo.pix.application.validation;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.modulo.chave.pix.application.validation.NumeroContaValidator;
import com.modulo.chave.pix.domain.port.ChavePixPort;

@ExtendWith(MockitoExtension.class)
public class NumeroContaValidatorTest {

    @Mock
    private ChavePixPort chavePixPort;

    @InjectMocks
    private NumeroContaValidator numeroContaValidator;
    

    @Test
    public void testValidate() {
        // Arrange
        Long numeroConta = 12345678L;
        
    
   
    }
    
    
    
}