package com.modulo.pix.application.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.modulo.chave.pix.application.validation.strategy.Impl.AlteracaoChavePixValidatorStrategyImpl;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;

@ExtendWith(MockitoExtension.class)
public class AlteracaoChavePixValidatorStrategyImplTest {

    private AlteracaoChavePixValidatorStrategyImpl validator = new AlteracaoChavePixValidatorStrategyImpl();
    private ChavePix chaveOriginal;
    private final UUID uuid = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        chaveOriginal = ChavePix.builder()
                .id(uuid)
                .tipoChave(TipoChaveEnum.EMAIL)
                .valorChave("teste@teste.com")
                .dataInclusao(LocalDateTime.now())
                .build();
    }

    @Test
    void validate_DevePassarQuandoTodosCamposValidos() {
        ChavePix chaveAtual = ChavePix.builder()
                .id(uuid)
                .tipoChave(TipoChaveEnum.EMAIL)
                .valorChave("teste@teste.com")
                .dataInclusao(LocalDateTime.now())
                .build();
        assertDoesNotThrow(() -> validator.validate(chaveAtual, chaveOriginal));
    }

    @Test
    void validate_DeveLancarExcecaoQuandoChaveInativada() {
        ChavePix chaveInativada = ChavePix.builder()
                .id(chaveOriginal.getId())
                .tipoChave(chaveOriginal.getTipoChave())
                .valorChave(chaveOriginal.getValorChave())
                .dataInclusao(chaveOriginal.getDataInclusao())
                .dataInativacao(LocalDateTime.now())
                .build();

        BusinessValidationException exception = assertThrows(BusinessValidationException.class,
                () -> validator.validate(chaveInativada, chaveOriginal));

        assertEquals("Não é permitido alterar chaves inativadas", exception.getMessage());
    }

    @Test
    void validate_DeveLancarExcecaoQuandoIdAlterado() {
        ChavePix chaveIdAlterado = ChavePix.builder()
                .id(UUID.randomUUID())
                .tipoChave(chaveOriginal.getTipoChave())
                .valorChave(chaveOriginal.getValorChave())
                .dataInclusao(chaveOriginal.getDataInclusao())
                .build();

        BusinessValidationException exception = assertThrows(BusinessValidationException.class,
                () -> validator.validate(chaveIdAlterado, chaveOriginal));

        assertEquals("Não é permitido alterar o ID da chave", exception.getMessage());
    }

    @Test
    void validate_DeveLancarExcecaoQuandoTipoChaveAlterado() {
        ChavePix chaveTipoAlterado = ChavePix.builder()
                .id(chaveOriginal.getId())
                .tipoChave(TipoChaveEnum.CELULAR)
                .valorChave(chaveOriginal.getValorChave())
                .dataInclusao(chaveOriginal.getDataInclusao())
                .build();

        BusinessValidationException exception = assertThrows(BusinessValidationException.class,
                () -> validator.validate(chaveTipoAlterado, chaveOriginal));

        assertEquals("Não é permitido alterar o tipo da chave", exception.getMessage());
    }

    @Test
    void validate_DeveLancarExcecaoQuandoAlteraChaveAleatoria() {
        ChavePix chaveAleatoriaOriginal = ChavePix.builder()
                .id(chaveOriginal.getId())
                .tipoChave(TipoChaveEnum.ALEATORIO)
                .valorChave(chaveOriginal.getValorChave())
                .dataInclusao(chaveOriginal.getDataInclusao())
                .build();
        
        ChavePix chaveAleatoriaAtual = ChavePix.builder()
                .id(chaveOriginal.getId())
                .tipoChave(TipoChaveEnum.ALEATORIO)
                .valorChave("novo-valor")
                .dataInclusao(chaveOriginal.getDataInclusao())
                .build();

        BusinessValidationException exception = assertThrows(BusinessValidationException.class,
                () -> validator.validate(chaveAleatoriaAtual, chaveAleatoriaOriginal));

        assertEquals("Não é permitido alterar o valor da chave aleatória", exception.getMessage());
    }
}