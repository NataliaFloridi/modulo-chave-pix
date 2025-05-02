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
    int countByNumeroAgenciaAndNumeroConta(String numeroAgencia, String numeroConta);
    TipoPessoaEnum findTipoPessoaByNumeroAgenciaAndNumeroConta(String numeroAgencia, String numeroConta);
}
