package com.modulo.chave.pix.domain.port;


import java.util.UUID;

import com.modulo.chave.pix.domain.model.ChavePix;

public interface AlteracaoChavePixPort {    
    ChavePix findById(UUID id);
    ChavePix save(ChavePix chavePix);
}
