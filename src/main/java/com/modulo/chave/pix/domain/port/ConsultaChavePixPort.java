package com.modulo.chave.pix.domain.port;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;


//Padrão: Ports and Adapters (Hexagonal)
// Motivo: Para definir a interface de consulta de chaves PIX, permitindo diferentes implementações.
public interface ConsultaChavePixPort {
    Optional<ChavePix> findById(UUID id);
    List<ChavePix> findByTipoChave(TipoChaveEnum tipoChave);
    List<ChavePix> findByAgenciaAndConta(TipoContaEnum tipoConta, Integer numeroAgencia, Integer numeroConta);
    List<ChavePix> findByNomeCorrentista(String nomeCorrentista);
    List<ChavePix> findByDataInclusao(LocalDateTime dataInclusao);
    List<ChavePix> findByDataInativacao(LocalDateTime dataInativacao);
    List<ChavePix> findByMultiplosCriterios(
            TipoChaveEnum tipoChave,
            Integer numeroAgencia,
            Integer numeroConta,
            String nomeCorrentista,
            LocalDateTime dataInclusao,
            LocalDateTime dataInativacao);
}
