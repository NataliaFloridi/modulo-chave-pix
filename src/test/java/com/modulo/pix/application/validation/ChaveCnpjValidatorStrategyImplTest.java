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

import com.modulo.chave.pix.application.validation.strategy.Impl.AlteracaoContaPixValidatorStrategyImpl;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;

@ExtendWith(MockitoExtension.class)
public class ChaveCnpjValidatorStrategyImplTest {
    private AlteracaoContaPixValidatorStrategyImpl validator = new AlteracaoContaPixValidatorStrategyImpl();
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
    void validate_DeveLancarExcecaoQuandoContaInativada() {
        ChavePix chaveInativada = ChavePix.builder()
                .id(chaveOriginal.getId())
                .tipoChave(chaveOriginal.getTipoChave())
                .valorChave(chaveOriginal.getValorChave())
                .dataInclusao(chaveOriginal.getDataInclusao())
                .dataInativacao(LocalDateTime.now())
                .build();

        BusinessValidationException exception = assertThrows(BusinessValidationException.class,
                () -> validator.validate(chaveInativada, chaveOriginal));

        assertEquals("Não é permitido alterar contas inativadas", exception.getMessage());
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

        assertEquals("Não é permitido alterar o ID da conta PIX", exception.getMessage());
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
    void validate_DeveLancarExcecaoQuandoValorChaveAlterado() {
        ChavePix chaveValorAlterado = ChavePix.builder()
                .id(chaveOriginal.getId())
                .tipoChave(chaveOriginal.getTipoChave())
                .valorChave("novo@teste.com")
                .dataInclusao(chaveOriginal.getDataInclusao())
                .build();

        BusinessValidationException exception = assertThrows(BusinessValidationException.class,
                () -> validator.validate(chaveValorAlterado, chaveOriginal));

        assertEquals("Não é permitido alterar o valor da chave", exception.getMessage());
    }
}
