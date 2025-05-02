package com.modulo.pix.application.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;

@ExtendWith(MockitoExtension.class)
public class AlteracaoContaPixValidatorStrategyImplTest {
    private AlteracaoContaPixValidatorStrategyImpl alteracaoContaPixValidatorStrategyImpl;

    @BeforeEach
    void setUp() {
        alteracaoContaPixValidatorStrategyImpl = new AlteracaoContaPixValidatorStrategyImpl();
    }

    @Test
    public void deveObterSucessoAoValidarAlteracaoContaPix() {
        ChavePix chavePixAtual = criarChaveValida();
        ChavePix chavePixValida = criarChaveValida();
        assertDoesNotThrow(() -> alteracaoContaPixValidatorStrategyImpl.validate(chavePixAtual, chavePixValida));
    }

    @Test
    public void deveLancarExcecaoQuandoChaveForInativa() {
        ChavePix chavePixAtual = criarChavePixAtual();
        assertThrows(BusinessValidationException.class,
                () -> alteracaoContaPixValidatorStrategyImpl.validate(chavePixAtual, null));
    }

    @Test
    public void deveLancarExcecaoQuandoIdExistir() {
        ChavePix chavePixAtual = criarChavePixAtual();
        assertThrows(BusinessValidationException.class,
                () -> alteracaoContaPixValidatorStrategyImpl.validate(chavePixAtual, chavePixAtual));
    }

    @Test
    public void deveLancarExcecaoQuandoTipoChaveForDiferente() {
        ChavePix chavePixAtual = criarChavePixAtual();
        ChavePix chavePixNova = criarChavePixNova();
        assertThrows(BusinessValidationException.class,
                () -> alteracaoContaPixValidatorStrategyImpl.validate(chavePixAtual, chavePixNova));
    }

    @Test
    public void deveLancarExcecaoQuandoAlterarValorChave() {
        ChavePix chavePixAtual = criarChavePixAtual();
        ChavePix chavePixNova = criarChavePixNova();
        assertThrows(BusinessValidationException.class,
                () -> alteracaoContaPixValidatorStrategyImpl.validate(chavePixAtual, chavePixNova));
    }

    private ChavePix criarChavePixAtual() {
        ChavePix chavePix = ChavePix.builder()
                .id(UUID.randomUUID())
                .tipoChave(TipoChaveEnum.EMAIL)
                .tipoConta(TipoContaEnum.CORRENTE)
                .valorChave("teste@teste.com")
                .numeroAgencia("1234")
                .numeroConta("1234567890")
                .nomeCorrentista("Teste")
                .dataInativacao(LocalDateTime.now())
                .build();
        return chavePix;
    }

    private ChavePix criarChavePixNova() {
        ChavePix chavePix = ChavePix.builder()
                .id(UUID.randomUUID())
                .valorChave("testes@teste.com")
                .build();
        return chavePix;
    }

    private ChavePix criarChaveValida() {
        ChavePix chavePix = ChavePix.builder()
                .id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                .tipoChave(TipoChaveEnum.EMAIL)
                .tipoConta(TipoContaEnum.CORRENTE)
                .valorChave("teste@teste.com")
                .numeroAgencia("1234")
                .numeroConta("1234567890")
                .nomeCorrentista("Teste")
                .dataInativacao(null)
                .build();
        return chavePix;
    }
}