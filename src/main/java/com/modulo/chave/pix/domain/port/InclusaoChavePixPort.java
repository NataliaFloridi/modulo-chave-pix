package com.modulo.chave.pix.domain.port;

import java.util.Optional;
import java.util.UUID;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoPessoaEnum;

public interface InclusaoChavePixPort {
    ChavePix salvarInclusaoChavePix(ChavePix chavePix);
    boolean existePeloValorChave(String valorChave);
    ChavePix buscarPeloValorChave(String valorChave);
    Optional<ChavePix> buscarPeloId(UUID id);
    int contarPeloNumeroAgenciaEConta(Integer numeroAgencia, Integer numeroConta);
    TipoPessoaEnum buscarTipoPessoaPeloNumeroAgenciaEConta(Integer numeroAgencia, Integer numeroConta);
}
