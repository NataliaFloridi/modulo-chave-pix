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
    Optional<ChavePix> buscarPeloId(UUID id);
    List<ChavePix> buscarPeloTipoChave(TipoChaveEnum tipoChave);
    List<ChavePix> buscarPorAgenciaEConta(TipoContaEnum tipoConta, Integer numeroAgencia, Integer numeroConta);
    List<ChavePix> buscarPeloNomeCorrentista(String nomeCorrentista);
    List<ChavePix> buscarPelaDataInclusao(LocalDateTime dataInclusao);
    List<ChavePix> buscarPelaDataInativacao(LocalDateTime dataInativacao);
    List<ChavePix> buscarPorMultiplosCriterios(
            TipoChaveEnum tipoChave,
            Integer numeroAgencia,
            Integer numeroConta,
            String nomeCorrentista,
            LocalDateTime dataInclusao,
            LocalDateTime dataInativacao);
}
