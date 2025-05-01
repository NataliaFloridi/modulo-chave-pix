package com.modulo.chave.pix.infrastructure.db.jparepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;
import com.modulo.chave.pix.domain.model.enums.TipoPessoaEnum;
import com.modulo.chave.pix.infrastructure.db.entity.ChavePixEntity;

@Repository
public interface JpaChavePixRepository extends JpaRepository<ChavePixEntity, UUID> {
    Optional<ChavePixEntity> findByValorChave(String valorChave);
    
    Optional<ChavePixEntity> findById(UUID id);
    
    boolean existsByValorChave(String valorChave);
    
    int countByNumeroAgenciaAndNumeroConta(Integer numeroAgencia, Integer numeroConta);

    @Query(value = "SELECT tipo_pessoa FROM chaves_pix WHERE numero_agencia = :numeroAgencia AND numero_conta = :numeroConta LIMIT 1", nativeQuery = true)
    TipoPessoaEnum findTipoPessoaByNumeroAgenciaAndNumeroConta(Integer numeroAgencia, Integer numeroConta);
    
    TipoPessoaEnum findTipoPessoaById(UUID id);

    List<ChavePixEntity> findByTipoChave(TipoChaveEnum tipoChave);

    List<ChavePixEntity> findByTipoContaAndNumeroAgenciaAndNumeroConta(TipoContaEnum tipoConta, Integer numeroAgencia, Integer numeroConta);

    List<ChavePixEntity> findByNomeCorrentista(String nomeCorrentista);

    List<ChavePixEntity> findByDataInclusao(LocalDateTime dataInclusao);

    List<ChavePixEntity> findByDataInativacao(LocalDateTime dataInativacao);

    @Query("SELECT cpe FROM ChavePixEntity cpe WHERE " +
           "(:tipoChave IS NULL OR cpe.tipoChave = :tipoChave) AND " +
           "(:numeroAgencia IS NULL OR cpe.numeroAgencia = :numeroAgencia) AND " +
           "(:numeroConta IS NULL OR cpe.numeroConta = :numeroConta) AND " +
           "(:nomeCorrentista IS NULL OR cpe.nomeCorrentista = :nomeCorrentista) AND " +
           "(:dataInclusao IS NULL OR cpe.dataInclusao >= :dataInclusao) AND " +
           "(:dataInativacao IS NULL OR cpe.dataInativacao <= :dataInativacao)")
    List<ChavePixEntity> findByMultiplosCriterios(
            @Param("tipoChave") TipoChaveEnum tipoChave,
            @Param("numeroAgencia") Integer numeroAgencia,
            @Param("numeroConta") Integer numeroConta,
            @Param("nomeCorrentista") String nomeCorrentista,
            @Param("dataInclusao") LocalDateTime dataInclusao,
            @Param("dataInativacao") LocalDateTime dataInativacao);

    
}
