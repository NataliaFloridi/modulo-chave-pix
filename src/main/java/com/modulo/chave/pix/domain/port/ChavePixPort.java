package com.modulo.chave.pix.domain.port;


import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoPessoaEnum;

public interface ChavePixPort {
    ChavePix save(ChavePix chavePix);
    boolean existsByValorChave(String valorChave);
    ChavePix findByValorChave(String valorChave);
    int countByNumeroAgenciaAndNumeroConta(String numeroAgencia, String numeroConta);
    TipoPessoaEnum findByNumeroAgenciaAndNumeroConta(String numeroAgencia, String numeroConta);
}
