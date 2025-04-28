package com.modulo.chave.pix.domain.port;


import com.modulo.chave.pix.domain.model.ChavePix;

public interface ChavePixPort {
    ChavePix save(ChavePix chavePix);
    boolean existsByValorChave(String valorChave);
    ChavePix findByValorChave(String valorChave);
    int countByNumeroAgenciaAndNumeroConta(Integer numeroAgencia, Integer numeroConta);
}
