package com.modulo.chave.pix.domain.port;

import java.util.Optional;
import java.util.UUID;
import com.modulo.chave.pix.domain.model.ChavePix;

public interface InativacaoChavePixPort {
    Optional<ChavePix> buscarPeloId(UUID id);
    ChavePix salvarInativacaoChavePix(ChavePix chavePix);
} 