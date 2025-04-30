package com.modulo.chave.pix.infrastructure.db.jparepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;
import com.modulo.chave.pix.domain.model.enums.TipoPessoaEnum;
import com.modulo.chave.pix.infrastructure.db.entity.ChavePixEntity;

public interface JpaChavePixRepository extends JpaRepository<ChavePixEntity, UUID> {
    boolean existsByValorChave(String valorChave);

    Optional<ChavePixEntity> findByValorChave(String valorChave);

    Optional<ChavePixEntity> findById(UUID id);

    int countByNumeroAgenciaAndNumeroConta(Integer numeroAgencia, Integer numeroConta);

    @Query(value = "SELECT tipo_pessoa FROM chaves_pix WHERE numero_agencia = :numeroAgencia AND numero_conta = :numeroConta LIMIT 1", nativeQuery = true)
    TipoPessoaEnum findTipoPessoaByNumeroAgenciaAndNumeroConta(Integer numeroAgencia, Integer numeroConta);

    List<ChavePixEntity> findByTipoChave(TipoChaveEnum tipoChave);

    List<ChavePixEntity> findByAgenciaConta(TipoContaEnum tipoConta, String numeroAgencia, String numeroConta);

    List<ChavePixEntity> findByNomeCorrentista(String nomeCorrentista);

    List<ChavePixEntity> findByDataInclusao(LocalDateTime dataInicio, LocalDateTime dataFim);

    List<ChavePixEntity> findByMultiplosCriterios(TipoChaveEnum tipoChave, String numeroAgencia, String numeroConta,
            String nomeCorrentista, LocalDateTime dataInclusao, LocalDateTime dataInativacao);
}
