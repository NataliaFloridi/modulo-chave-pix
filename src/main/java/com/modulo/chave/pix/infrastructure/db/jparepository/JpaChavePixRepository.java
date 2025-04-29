package com.modulo.chave.pix.infrastructure.db.jparepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.modulo.chave.pix.domain.model.enums.TipoPessoaEnum;
import com.modulo.chave.pix.infrastructure.db.entity.ChavePixEntity;

public interface JpaChavePixRepository extends JpaRepository<ChavePixEntity, UUID> {
    boolean existsByValorChave(String valorChave);

    ChavePixEntity findByValorChave(String valorChave);

    int countByNumeroAgenciaAndNumeroConta(Integer numeroAgencia, Integer numeroConta);

    @Query(value = "SELECT tipo_pessoa FROM chaves_pix WHERE numero_agencia = :numeroAgencia AND numero_conta = :numeroConta LIMIT 1", nativeQuery = true)
    TipoPessoaEnum findTipoPessoaByNumeroAgenciaAndNumeroConta(Integer numeroAgencia, Integer numeroConta);
}
