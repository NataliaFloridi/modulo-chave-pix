package com.modulo.chave.pix.infrastructure.persistence;

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

        when(chavePixMapper.toEntity(any())).thenReturn(chavePixEntity);
        when(jpaChavePixRepository.save(any())).thenReturn(chavePixEntity);
        when(chavePixMapper.toDomain(chavePixEntity)).thenReturn(chavePix);

        ChavePix result = chavePixAdpter.salvarInclusaoChavePix(chavePix);

        assertEquals(chavePix, result);

        verify(chavePixMapper, times(1)).toEntity(any());
        verify(jpaChavePixRepository, times(1)).save(any());
    }

    @Test
    public void deveObterSucessoAoVerificarExistenciaChavePix() {
        when(jpaChavePixRepository.existsByValorChave(any())).thenReturn(true);

        boolean result = chavePixAdpter.existePeloValorChave(any());

        assertEquals(true, result);

        verify(jpaChavePixRepository, times(1)).existsByValorChave(any());
    }

    @Test
    public void deveObterSucessoAoBuscarChavePixPorValor() {
        ChavePixEntity chavePixEntity = ChavePixEntity.builder().build();
        ChavePix chavePix = ChavePix.builder().build();

        when(jpaChavePixRepository.findByValorChave(any())).thenReturn(Optional.of(chavePixEntity));
        when(chavePixMapper.toDomain(chavePixEntity)).thenReturn(chavePix);

        ChavePix result = chavePixAdpter.buscarPeloValorChave(any());

        assertEquals(chavePix, result);

        verify(chavePixMapper, times(1)).toDomain(chavePixEntity);
        verify(jpaChavePixRepository, times(1)).findByValorChave(any());
    }

    @Test
    public void deveObterErroRegistroNotFoundExceptionAoBuscarChavePixPorValor() {
        String valorChave = "1234567890";
        when(jpaChavePixRepository.findByValorChave(valorChave)).thenReturn(Optional.empty());

        RegistroNotFoundException result = assertThrows(RegistroNotFoundException.class, () -> {
            chavePixAdpter.buscarPeloValorChave(valorChave);
        });

        assertEquals("Chave PIX não encontrada pelo valor: " + valorChave, result.getMessage());

        verify(jpaChavePixRepository, times(1)).findByValorChave(valorChave);
    }

    @Test
    public void deveObterSucessoAoContarChavePixPorAgenciaEConta() {
        when(jpaChavePixRepository.countByNumeroAgenciaAndNumeroConta(any(), any())).thenReturn(1);

        int result = chavePixAdpter.contarPeloNumeroAgenciaEConta(1234, 567890);

        assertEquals(1, result);

        verify(jpaChavePixRepository, times(1)).countByNumeroAgenciaAndNumeroConta(any(), any());
    }
   
    @Test
    public void deveObterSucessoAoBuscarChavePixPorId() {
        ChavePixEntity chavePixEntity = ChavePixEntity.builder().build();
        ChavePix chavePix = ChavePix.builder().build();

        when(jpaChavePixRepository.findById(any())).thenReturn(Optional.of(chavePixEntity));
        when(chavePixMapper.toDomain(chavePixEntity)).thenReturn(chavePix);

        Optional<ChavePix> result = chavePixAdpter.buscarPeloId(UUID.randomUUID());

        assertEquals(Optional.of(chavePix), result);

        verify(chavePixMapper, times(1)).toDomain(chavePixEntity);
        verify(jpaChavePixRepository, times(1)).findById(any());
    }

    @Test
    public void deveObterSucessoAoBuscarChavePixPorTipoChave() {
        ChavePixEntity chavePixEntity = ChavePixEntity.builder().build();
        ChavePix chavePix = ChavePix.builder().build();

        when(jpaChavePixRepository.findByTipoChave(any())).thenReturn(List.of(chavePixEntity));
        when(chavePixMapper.toCriarDomainList(List.of(chavePixEntity))).thenReturn(List.of(chavePix));
 
        List<ChavePix> result = chavePixAdpter.buscarPeloTipoChave(TipoChaveEnum.CPF);
        
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

        List<ChavePix> result = chavePixAdpter.buscarPorAgenciaEConta(TipoContaEnum.CORRENTE, 1234, 567890);

        assertEquals(List.of(chavePix), result);

        verify(chavePixMapper, times(1)).toCriarDomainList(List.of(chavePixEntity));
    }

    @Test
    public void deveObterSucessoAoBuscarChavePixPorNomeCorrentista() {
        ChavePixEntity chavePixEntity = ChavePixEntity.builder().build();
        ChavePix chavePix = ChavePix.builder().build();

        when(jpaChavePixRepository.findByNomeCorrentista(any())).thenReturn(List.of(chavePixEntity));
        when(chavePixMapper.toCriarDomainList(List.of(chavePixEntity))).thenReturn(List.of(chavePix));

        List<ChavePix> result = chavePixAdpter.buscarPeloNomeCorrentista("João da Silva");

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

        List<ChavePix> result = chavePixAdpter.buscarPelaDataInclusao(LocalDateTime.now());

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

        List<ChavePix> result = chavePixAdpter.buscarPelaDataInativacao(LocalDateTime.now());

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

        List<ChavePix> result = chavePixAdpter.buscarPorMultiplosCriterios(TipoChaveEnum.CPF, 1234, 567890, "João da Silva", LocalDateTime.now(), LocalDateTime.now());

        assertEquals(List.of(chavePix), result);

        verify(chavePixMapper, times(1)).toCriarDomainList(List.of(chavePixEntity));
        verify(jpaChavePixRepository, times(1)).findByMultiplosCriterios(any(), any(), any(), any(), any(), any());
    }

    @Test
    public void deveObterSucessoAoBuscarTipoPessoaPorAgenciaEConta() {
        when(jpaChavePixRepository.findTipoPessoaByNumeroAgenciaAndNumeroConta(any(), any())).thenReturn(TipoPessoaEnum.FISICA);

        TipoPessoaEnum result = chavePixAdpter.buscarTipoPessoaPeloNumeroAgenciaEConta(1234, 567890);

        assertEquals(TipoPessoaEnum.FISICA, result);
        
        verify(jpaChavePixRepository, times(1)).findTipoPessoaByNumeroAgenciaAndNumeroConta(any(), any());
    }

    @Test
    public void deveObterSucessoAoSalvarAlteracaoChavePix() {
        ChavePixEntity chavePixEntity = ChavePixEntity.builder().build();
        ChavePix chavePix = ChavePix.builder().build();

        when(chavePixMapper.toEntity(any())).thenReturn(chavePixEntity);
        when(jpaChavePixRepository.save(any())).thenReturn(chavePixEntity);
        when(chavePixMapper.toDomain(chavePixEntity)).thenReturn(chavePix);

        ChavePix result = chavePixAdpter.salvarAlteracaoChavePix(chavePix);

        assertEquals(chavePix, result);

        verify(chavePixMapper, times(1)).toEntity(any());
        verify(jpaChavePixRepository, times(1)).save(any());
    }

    @Test
    public void deveObterSucessoAoSalvarInativacaoChavePix() {
        ChavePixEntity chavePixEntity = ChavePixEntity.builder().build();
        ChavePix chavePix = ChavePix.builder().build();

        when(chavePixMapper.toEntity(any())).thenReturn(chavePixEntity);
        when(jpaChavePixRepository.save(any())).thenReturn(chavePixEntity);
        when(chavePixMapper.toDomain(chavePixEntity)).thenReturn(chavePix);

        ChavePix result = chavePixAdpter.salvarInativacaoChavePix(chavePix);

        assertEquals(chavePix, result);
        
        verify(chavePixMapper, times(1)).toEntity(any());
        verify(jpaChavePixRepository, times(1)).save(any());
    }
    
}
