package com.modulo.chave.pix.domain.port;

import java.util.Optional;
import java.util.UUID;
import com.modulo.chave.pix.domain.model.ChavePix;

public interface InclusaoChavePixPort {
    ChavePix save(ChavePix chavePix);
    boolean existsByValorChave(String valorChave);
    ChavePix findByValorChave(String valorChave);
    Optional<ChavePix> findById(UUID id);
    int countByNumeroAgenciaAndNumeroConta(Integer numeroAgencia, Integer numeroConta);
    }
