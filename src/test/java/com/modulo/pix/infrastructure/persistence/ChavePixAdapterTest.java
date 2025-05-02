package com.modulo.pix.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.modulo.chave.pix.application.mappers.ChavePixMapper;
import com.modulo.chave.pix.domain.exception.RegistroNotFoundException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;
import com.modulo.chave.pix.domain.model.enums.TipoPessoaEnum;
import com.modulo.chave.pix.infrastructure.db.entity.ChavePixEntity;
import com.modulo.chave.pix.infrastructure.db.jparepository.JpaChavePixRepository;
import com.modulo.chave.pix.infrastructure.persistence.ChavePixAdapter;

@ExtendWith(MockitoExtension.class)
public class ChavePixAdapterTest {
    
    @InjectMocks
    private ChavePixAdapter chavePixAdpter;

    @Mock
    private JpaChavePixRepository jpaChavePixRepository;
    
    @Mock
    private ChavePixMapper chavePixMapper;

    @Test
    public void deveObterSucessoAoSalvarChavePix() {
        ChavePixEntity chavePixEntity = ChavePixEntity.builder().build();
        ChavePix chavePix = ChavePix.builder().build();

        when(chavePixMapper.toCriarEntity(any())).thenReturn(chavePixEntity);
        when(jpaChavePixRepository.save(any())).thenReturn(chavePixEntity);
        when(chavePixMapper.toCriarDomain(chavePixEntity)).thenReturn(chavePix);

        ChavePix result = chavePixAdpter.save(chavePix);

        assertEquals(chavePix, result);

        verify(chavePixMapper, times(1)).toCriarEntity(any());
        verify(jpaChavePixRepository, times(1)).save(any());
    }

    @Test
    public void deveObterSucessoAoVerificarExistenciaChavePix() {
        when(jpaChavePixRepository.existsByValorChave(any())).thenReturn(true);

        boolean result = chavePixAdpter.existsByValorChave(any());

        assertEquals(true, result);

        verify(jpaChavePixRepository, times(1)).existsByValorChave(any());
    }

    @Test
    public void deveObterSucessoAoBuscarChavePixPorValor() {
        ChavePixEntity chavePixEntity = ChavePixEntity.builder().build();
        ChavePix chavePix = ChavePix.builder().build();

        when(jpaChavePixRepository.findByValorChave(any())).thenReturn(Optional.of(chavePixEntity));
        when(chavePixMapper.toCriarDomain(chavePixEntity)).thenReturn(chavePix);

        ChavePix result = chavePixAdpter.findByValorChave("1234567890");

        assertEquals(chavePix, result);

        verify(chavePixMapper, times(1)).toCriarDomain(chavePixEntity);
        verify(jpaChavePixRepository, times(1)).findByValorChave(any());
    }

    @Test
    public void deveObterErroRegistroNotFoundExceptionAoBuscarChavePixPorValor() {
        when(jpaChavePixRepository.findByValorChave(any())).thenReturn(Optional.empty());

        RegistroNotFoundException result = assertThrows(RegistroNotFoundException.class, () -> {
            chavePixAdpter.findByValorChave("1234567890");
        });

        assertEquals("Chave PIX não encontrada pelo valor: 1234567890", result.getMessage());

        verify(jpaChavePixRepository, times(1)).findByValorChave(any());
    }

    @Test
    public void deveObterSucessoAoContarChavePixPorAgenciaEConta() {
        when(jpaChavePixRepository.countByNumeroAgenciaAndNumeroConta(any(), any())).thenReturn(1);

        int result = chavePixAdpter.countByNumeroAgenciaAndNumeroConta("1234", "567890");

        assertEquals(1, result);

        verify(jpaChavePixRepository, times(1)).countByNumeroAgenciaAndNumeroConta(any(), any());
    }
   
    @Test
    public void deveObterSucessoAoBuscarTipoPessoaPorAgenciaEConta() {
        when(jpaChavePixRepository.findTipoPessoaByNumeroAgenciaAndNumeroConta(any(), any())).thenReturn(TipoPessoaEnum.FISICA);

        TipoPessoaEnum result = chavePixAdpter.findTipoPessoaByNumeroAgenciaAndNumeroConta("1234", "567890");

        assertEquals(TipoPessoaEnum.FISICA, result);

        verify(jpaChavePixRepository, times(1)).findTipoPessoaByNumeroAgenciaAndNumeroConta(any(), any());
    }

    @Test
    public void deveObterSucessoAoBuscarChavePixPorId() {
        ChavePixEntity chavePixEntity = ChavePixEntity.builder().build();
        ChavePix chavePix = ChavePix.builder().build();

        when(jpaChavePixRepository.findById(any())).thenReturn(Optional.of(chavePixEntity));
        when(chavePixMapper.toCriarDomain(chavePixEntity)).thenReturn(chavePix);

        Optional<ChavePix> result = chavePixAdpter.findById(UUID.randomUUID());

        assertEquals(Optional.of(chavePix), result);

        verify(chavePixMapper, times(1)).toCriarDomain(chavePixEntity);
        verify(jpaChavePixRepository, times(1)).findById(any());
    }

    @Test
    public void deveObterSucessoAoBuscarChavePixPorTipoChave() {
        ChavePixEntity chavePixEntity = ChavePixEntity.builder().build();
        ChavePix chavePix = ChavePix.builder().build();

        when(jpaChavePixRepository.findByTipoChave(any())).thenReturn(List.of(chavePixEntity));
        when(chavePixMapper.toCriarDomainList(List.of(chavePixEntity))).thenReturn(List.of(chavePix));
 
        List<ChavePix> result = chavePixAdpter.findByTipoChave(TipoChaveEnum.CPF);
        
        assertEquals(List.of(chavePix), result);

        verify(chavePixMapper, times(1)).toCriarDomainList(List.of(chavePixEntity));
        verify(jpaChavePixRepository, times(1)).findByTipoChave(any());
    }

    @Test
    public void deveObterSucessoAoBuscarChavePixPorAgenciaEConta() {
        ChavePixEntity chavePixEntity = ChavePixEntity.builder().build();
        ChavePix chavePix = ChavePix.builder().build();

        when(jpaChavePixRepository.findByTipoContaAndNumeroAgenciaAndNumeroConta(any(), any(), any())).thenReturn(List.of(chavePixEntity));
        when(chavePixMapper.toCriarDomainList(List.of(chavePixEntity))).thenReturn(List.of(chavePix));

        List<ChavePix> result = chavePixAdpter.findByAgenciaAndConta(TipoContaEnum.CORRENTE, "1234", "567890");

        assertEquals(List.of(chavePix), result);

        verify(chavePixMapper, times(1)).toCriarDomainList(List.of(chavePixEntity));
    }

    @Test
    public void deveObterSucessoAoBuscarChavePixPorNomeCorrentista() {
        ChavePixEntity chavePixEntity = ChavePixEntity.builder().build();
        ChavePix chavePix = ChavePix.builder().build();

        when(jpaChavePixRepository.findByNomeCorrentista(any())).thenReturn(List.of(chavePixEntity));
        when(chavePixMapper.toCriarDomainList(List.of(chavePixEntity))).thenReturn(List.of(chavePix));

        List<ChavePix> result = chavePixAdpter.findByNomeCorrentista("João da Silva");

        assertEquals(List.of(chavePix), result);

        verify(chavePixMapper, times(1)).toCriarDomainList(List.of(chavePixEntity));
        verify(jpaChavePixRepository, times(1)).findByNomeCorrentista(any());
    }

    @Test
    public void deveObterSucessoAoBuscarChavePixPorDataInclusao() {
        ChavePixEntity chavePixEntity = ChavePixEntity.builder().build();
        ChavePix chavePix = ChavePix.builder().build();

        when(jpaChavePixRepository.findByDataInclusao(any())).thenReturn(List.of(chavePixEntity));
        when(chavePixMapper.toCriarDomainList(List.of(chavePixEntity))).thenReturn(List.of(chavePix));

        List<ChavePix> result = chavePixAdpter.findByDataInclusao(LocalDateTime.now());

        assertEquals(List.of(chavePix), result);

        verify(chavePixMapper, times(1)).toCriarDomainList(List.of(chavePixEntity));
        verify(jpaChavePixRepository, times(1)).findByDataInclusao(any());
    }

    @Test
    public void deveObterSucessoAoBuscarChavePixPorDataInativacao() {
        ChavePixEntity chavePixEntity = ChavePixEntity.builder().build();
        ChavePix chavePix = ChavePix.builder().build();

        when(jpaChavePixRepository.findByDataInativacao(any())).thenReturn(List.of(chavePixEntity));
        when(chavePixMapper.toCriarDomainList(List.of(chavePixEntity))).thenReturn(List.of(chavePix));

        List<ChavePix> result = chavePixAdpter.findByDataInativacao(LocalDateTime.now());

        assertEquals(List.of(chavePix), result);

        verify(chavePixMapper, times(1)).toCriarDomainList(List.of(chavePixEntity));
        verify(jpaChavePixRepository, times(1)).findByDataInativacao(any());
    }

    @Test
    public void deveObterSucessoAoBuscarChavePixPorMultiplosCriterios() {
        ChavePixEntity chavePixEntity = ChavePixEntity.builder().build();
        ChavePix chavePix = ChavePix.builder().build();

        when(jpaChavePixRepository.findByMultiplosCriterios(any(), any(), any(), any(), any(), any())).thenReturn(List.of(chavePixEntity));
        when(chavePixMapper.toCriarDomainList(List.of(chavePixEntity))).thenReturn(List.of(chavePix));

        List<ChavePix> result = chavePixAdpter.findByMultiplosCriterios(TipoChaveEnum.CPF, "1234", "567890", "João da Silva", LocalDateTime.now(), LocalDateTime.now());

        assertEquals(List.of(chavePix), result);

        verify(chavePixMapper, times(1)).toCriarDomainList(List.of(chavePixEntity));
        verify(jpaChavePixRepository, times(1)).findByMultiplosCriterios(any(), any(), any(), any(), any(), any());
    }

    @Test
    public void deveObterErroContaAoBuscarChavePixPorMultiplosCriterios() {
        List<ChavePix> result = chavePixAdpter.findByMultiplosCriterios(null, "1234", null, null, null, null);

        assertEquals(List.of(), result);
        verify(jpaChavePixRepository, times(1)).findByMultiplosCriterios(null, 1234, null, null, null, null);
    }
}
