package com.modulo.chave.pix.domain.port;

import java.util.Optional;
import java.util.UUID;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoPessoaEnum;

public interface InclusaoChavePixPort {
    ChavePix save(ChavePix chavePix);
    boolean existsByValorChave(String valorChave);
    ChavePix findByValorChave(String valorChave);
    Optional<ChavePix> findById(UUID id);
    int countByNumeroAgenciaAndNumeroConta(Integer numeroAgencia, Integer numeroConta);
    TipoPessoaEnum findTipoPessoaByNumeroAgenciaAndNumeroConta(Integer numeroAgencia, Integer numeroConta);
}
