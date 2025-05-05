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
import com.modulo.chave.pix.domain.port.InativacaoChavePixPort;
import com.modulo.chave.pix.domain.port.InclusaoChavePixPort;
import com.modulo.chave.pix.infrastructure.db.entity.ChavePixEntity;
import com.modulo.chave.pix.infrastructure.db.jparepository.JpaChavePixRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChavePixAdapter implements InclusaoChavePixPort, AlteracaoChavePixPort, ConsultaChavePixPort, InativacaoChavePixPort {

    private final JpaChavePixRepository jpaChavePixRepository;
    private final ChavePixMapper chavePixMapper;

    @Override
    public ChavePix salvarInclusaoChavePix(ChavePix chavePix) {
        log.info("Salvando chave pix");
        var chavePixEntity = chavePixMapper.toEntity(chavePix);
        var chavePixSalva = jpaChavePixRepository.save(chavePixEntity);

        log.info("Chave pix salva com sucesso");
        return chavePixMapper.toDomain(chavePixSalva);
    }

    @Override
    public boolean existePeloValorChave(String valorChave) {
        log.info("Verificando se a chave pix existe");
        boolean existe = jpaChavePixRepository.existsByValorChave(valorChave);
        log.info("Chave pix existe: {}", existe);
        return existe;
    }

    @Override
    public ChavePix buscarPeloValorChave(String valorChave) {
        log.info("Buscando chave pix por valor");
        var chavePixEntity = jpaChavePixRepository.findByValorChave(valorChave)
                .orElseThrow(() -> new RegistroNotFoundException("Chave PIX não encontrada pelo valor: " + valorChave));
        log.info("Chave pix encontrada: {}", chavePixEntity);
        return chavePixMapper.toDomain(chavePixEntity);
    }

    @Override
    public int contarPeloNumeroAgenciaEConta(Integer numeroAgencia, Integer numeroConta) {
        log.info("Contando chaves pix por número de agência e conta");
        int quantidade = jpaChavePixRepository.countByNumeroAgenciaAndNumeroConta(
                numeroAgencia,
                numeroConta);
        log.info("Total de chaves pix: {}", quantidade);
        return quantidade;
    }

    @Override
    public Optional<ChavePix> buscarPeloId(UUID id) {
        log.info("Buscando chave pix por ID");
        var chavePixEntity = jpaChavePixRepository.findById(id)
                .orElseThrow(() -> new RegistroNotFoundException("Chave PIX não encontrada pelo ID: " + id));
        log.info("Chave pix encontrada: {}", chavePixEntity);
        return Optional.of(chavePixMapper.toDomain(chavePixEntity));
    }

    @Override
    public List<ChavePix> buscarPeloTipoChave(TipoChaveEnum tipoChave) {
        log.info("Buscando chaves pix por tipo de chave");
        var chavePixEntityList = jpaChavePixRepository.findByTipoChave(tipoChave);
        
        log.info("Chaves pix encontradas: {}", chavePixEntityList);
        return chavePixMapper.toCriarDomainList(chavePixEntityList);
    }

    @Override
    public List<ChavePix> buscarPorAgenciaEConta(TipoContaEnum tipoConta, Integer numeroAgencia, Integer numeroConta) {
        log.info("Buscando chaves pix por tipo de conta, número de agência e número de conta");
        var chavePixEntityList = jpaChavePixRepository.findByTipoContaAndNumeroAgenciaAndNumeroConta(tipoConta, numeroAgencia, numeroConta);
        log.info("Chaves pix encontradas: {}", chavePixEntityList);
        return chavePixMapper.toCriarDomainList(chavePixEntityList);
    }

    @Override
    public List<ChavePix> buscarPeloNomeCorrentista(String nomeCorrentista) {
        log.info("Buscando chaves pix por nome do correntista");
        var chavePixEntityList = jpaChavePixRepository.findByNomeCorrentista(nomeCorrentista);
        
        log.info("Chaves pix encontradas: {}", chavePixEntityList);
        return chavePixMapper.toCriarDomainList(chavePixEntityList);
    }

    @Override
    public List<ChavePix> buscarPelaDataInclusao(LocalDateTime dataInclusao) {
        log.info("Buscando chaves pix por data de inclusão");
        var chavePixEntityList = jpaChavePixRepository.findByDataInclusao(dataInclusao);
        
        log.info("Chaves pix encontradas: {}", chavePixEntityList);
        return chavePixMapper.toCriarDomainList(chavePixEntityList);
    }

    @Override
    public List<ChavePix> buscarPelaDataInativacao(LocalDateTime dataInativacao) {
        log.info("Buscando chaves pix por data de inativação");
        var chavePixEntityList = jpaChavePixRepository.findByDataInativacao(dataInativacao);
        
        log.info("Chaves pix encontradas: {}", chavePixEntityList);
        return chavePixMapper.toCriarDomainList(chavePixEntityList);
    }

    @Override
    public List<ChavePix> buscarPorMultiplosCriterios(
            TipoChaveEnum tipoChave,
            Integer numeroAgencia,
            Integer numeroConta,
            String nomeCorrentista,
            LocalDateTime dataInclusao,
            LocalDateTime dataInativacao) {
        log.info("Buscando chaves pix por múltiplos critérios");
        
        var chavePixEntityList = jpaChavePixRepository.findByMultiplosCriterios(
                tipoChave,
                numeroAgencia,
                numeroConta,
                nomeCorrentista,
                dataInclusao,
                dataInativacao);
        
        log.info("Chaves pix encontradas: {}", chavePixEntityList);
        return chavePixMapper.toCriarDomainList(chavePixEntityList);
    }

    public TipoPessoaEnum buscarTipoPessoaPeloNumeroAgenciaEConta(Integer numeroAgencia, Integer numeroConta) {
        log.info("Buscando tipo de pessoa por número de agência e conta");
        TipoPessoaEnum tipoPessoa = jpaChavePixRepository.findTipoPessoaByNumeroAgenciaAndNumeroConta(numeroAgencia, numeroConta);
        log.info("Tipo de pessoa encontrado: {}", tipoPessoa);
        return tipoPessoa;
    }

    @Override
    public ChavePix salvarAlteracaoChavePix(ChavePix chavePix) {
        log.info("Salvando alteração da chave pix");
        var chavePixEntity = chavePixMapper.toEntity(chavePix);
        var chavePixSalva = jpaChavePixRepository.save(chavePixEntity);
        log.info("Chave pix alterada com sucesso");
        return chavePixMapper.toDomain(chavePixSalva);
    }

    @Override
    public ChavePix salvarInativacaoChavePix(ChavePix chavePix) {
        log.info("Salvando inativação da chave pix");
        var chavePixEntity = chavePixMapper.toEntity(chavePix);
        var chavePixSalva = jpaChavePixRepository.save(chavePixEntity);
        log.info("Chave pix inativada com sucesso");
        return chavePixMapper.toDomain(chavePixSalva);
    }
}
