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
    List<ChavePix> findByAgenciaAndConta(TipoContaEnum tipoConta, String numeroAgencia, String numeroConta);
    List<ChavePix> findByNomeCorrentista(String nomeCorrentista);
    List<ChavePix> findByDataInclusao(LocalDateTime dataInicio, LocalDateTime dataFim);
    List<ChavePix> findByDataInativacao(LocalDateTime dataInicio, LocalDateTime dataFim);
    List<ChavePix> findByMultiplosCriterios(
            TipoChaveEnum tipoChave,
            String numeroAgencia,
            String numeroConta,
            String nomeCorrentista,
            LocalDateTime dataInclusao,
            LocalDateTime dataInativacao);
}
