package com.modulo.chave.pix.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.mappers.ChavePixMapper;
import com.modulo.chave.pix.domain.exception.RegistroNotFoundException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;
import com.modulo.chave.pix.domain.model.enums.TipoPessoaEnum;
import com.modulo.chave.pix.domain.port.AlteracaoChavePixPort;
import com.modulo.chave.pix.domain.port.ConsultaChavePixPort;
import com.modulo.chave.pix.domain.port.InclusaoChavePixPort;
import com.modulo.chave.pix.infrastructure.db.entity.ChavePixEntity;
import com.modulo.chave.pix.infrastructure.db.jparepository.JpaChavePixRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChavePixAdapter implements InclusaoChavePixPort, AlteracaoChavePixPort, ConsultaChavePixPort {

    private final JpaChavePixRepository jpaChavePixRepository;
    private final ChavePixMapper chavePixMapper;

    @Override
    public ChavePix save(ChavePix chavePix) {
        log.info("Salvando chave pix");
        ChavePixEntity chavePixEntity = chavePixMapper.toCriarEntity(chavePix);
        ChavePixEntity savedChavePix = jpaChavePixRepository.save(chavePixEntity);

        log.info("Chave pix salva com sucesso");
        return chavePixMapper.toCriarDomain(savedChavePix);
    }

    @Override
    public boolean existsByValorChave(String valorChave) {
        log.info("Verificando se a chave pix existe");
        boolean exists = jpaChavePixRepository.existsByValorChave(valorChave);
        log.info("Chave pix existe: {}", exists);
        return exists;
    }

    @Override
    public ChavePix findByValorChave(String valorChave) {
        log.info("Buscando chave pix por valor");
        ChavePixEntity chavePixEntity = jpaChavePixRepository.findByValorChave(valorChave)
                .orElseThrow(() -> new RegistroNotFoundException("Chave PIX não encontrada pelo valor: " + valorChave));
        log.info("Chave pix encontrada: {}", chavePixEntity);
        return chavePixMapper.toCriarDomain(chavePixEntity);
    }

    @Override
    public int countByNumeroAgenciaAndNumeroConta(String numeroAgencia, String numeroConta) {
        log.info("Contando chaves pix por número de agência e conta");
        int count = jpaChavePixRepository.countByNumeroAgenciaAndNumeroConta(
                Integer.parseInt(numeroAgencia),
                Integer.parseInt(numeroConta));
        log.info("Total de chaves pix: {}", count);
        return count;
    }

    @Override
    public TipoPessoaEnum findByNumeroAgenciaAndNumeroConta(String numeroAgencia, String numeroConta) {
        log.info("Buscando tipo de pessoa por número de agência e conta");
        TipoPessoaEnum tipoPessoa = jpaChavePixRepository.findTipoPessoaByNumeroAgenciaAndNumeroConta(
                Integer.parseInt(numeroAgencia),
                Integer.parseInt(numeroConta));
        log.info("Tipo de pessoa encontrado: {}", tipoPessoa);
        return tipoPessoa;
    }

    @Override
    public Optional<ChavePix> findById(UUID id) {
        log.info("Buscando chave pix por valor");
        ChavePixEntity chavePixEntity = jpaChavePixRepository.findById(id)
                .orElseThrow(() -> new RegistroNotFoundException("Chave PIX não encontrada pelo ID: " + id));
        log.info("Chave pix encontrada: {}", chavePixEntity);
        return Optional.of(chavePixMapper.toCriarDomain(chavePixEntity));
    }

    @Override
    public List<ChavePix> findByTipoChave(TipoChaveEnum tipoChave) {
        log.info("Buscando chaves pix por tipo de chave");
        List<ChavePixEntity> chavePixEntityList = jpaChavePixRepository.findByTipoChave(tipoChave);
        
        log.info("Chaves pix encontradas: {}", chavePixEntityList);
        return chavePixMapper.toCriarDomain(chavePixEntityList);
    }

    @Override
    public List<ChavePix> findByAgenciaAndConta(TipoContaEnum tipoConta, String numeroAgencia, String numeroConta) {
        log.info("Buscando chaves pix por tipo de conta, número de agência e número de conta");
        List<ChavePixEntity> chavePixEntityList = jpaChavePixRepository.findByTipoContaAndNumeroAgenciaAndNumeroConta(tipoConta, Integer.parseInt(numeroAgencia), Integer.parseInt(numeroConta));
        log.info("Chaves pix encontradas: {}", chavePixEntityList);
        return chavePixMapper.toCriarDomain(chavePixEntityList);
    }

    @Override
    public List<ChavePix> findByNomeCorrentista(String nomeCorrentista) {
        log.info("Buscando chaves pix por nome do correntista");
        List<ChavePixEntity> chavePixEntityList = jpaChavePixRepository.findByNomeCorrentista(nomeCorrentista);
        
        log.info("Chaves pix encontradas: {}", chavePixEntityList);
        return chavePixMapper.toCriarDomain(chavePixEntityList);
    }

    @Override
    public List<ChavePix> findByDataInclusao(LocalDateTime dataInicio, LocalDateTime dataFim) {
        log.info("Buscando chaves pix por data de inclusão");
        List<ChavePixEntity> chavePixEntityList = jpaChavePixRepository.findByDataInclusao(dataInicio);
        
        log.info("Chaves pix encontradas: {}", chavePixEntityList);
        return chavePixMapper.toCriarDomain(chavePixEntityList);
    }

    @Override
    public List<ChavePix> findByDataInativacao(LocalDateTime dataInicio, LocalDateTime dataFim) {
        log.info("Buscando chaves pix por data de inativação");
        List<ChavePixEntity> chavePixEntityList = jpaChavePixRepository.findByDataInativacao(dataInicio);
        
        log.info("Chaves pix encontradas: {}", chavePixEntityList);
        return chavePixMapper.toCriarDomain(chavePixEntityList);
    }

    @Override
    public List<ChavePix> findByMultiplosCriterios(
            TipoChaveEnum tipoChave,
            String numeroAgencia,
            String numeroConta,
            String nomeCorrentista,
            LocalDateTime dataInclusao,
            LocalDateTime dataInativacao) {
        log.info("Buscando chaves pix por múltiplos critérios");
        
        Integer agencia = null;
        Integer conta = null;
        
        try {
            if (numeroAgencia != null && !numeroAgencia.isEmpty()) {
                agencia = Integer.parseInt(numeroAgencia);
            }
            if (numeroConta != null && !numeroConta.isEmpty()) {
                conta = Integer.parseInt(numeroConta);
            }
        } catch (NumberFormatException e) {
            log.warn("Erro ao converter número de agência/conta: {}", e.getMessage());
            return List.of();
        }
        
        List<ChavePixEntity> chavePixEntityList = jpaChavePixRepository.findByMultiplosCriterios(
                tipoChave,
                agencia,
                conta,
                nomeCorrentista,
                dataInclusao,
                dataInativacao);
        
        log.info("Chaves pix encontradas: {}", chavePixEntityList);
        return chavePixMapper.toCriarDomain(chavePixEntityList);
    }
}
