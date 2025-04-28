package com.modulo.chave.pix.infrastructure.db.jparepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modulo.chave.pix.infrastructure.db.entity.ChavePixEntity;

public interface JpaChavePixRepository extends JpaRepository<ChavePixEntity, UUID> {
    boolean existsByValorChave(String valorChave);

    ChavePixEntity findByValorChave(String valorChave);

    int countByNumeroAgenciaAndNumeroConta(Integer numeroAgencia, Integer numeroConta);
}
