package com.modulo.chave.pix.infrastructure.persistence;

import org.springframework.stereotype.Component;

import com.modulo.chave.pix.application.mappers.ChavePixMapper;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.port.ChavePixPort;
import com.modulo.chave.pix.infrastructure.db.entity.ChavePixEntity;
import com.modulo.chave.pix.infrastructure.db.jparepository.JpaChavePixRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChavePixAdapter implements ChavePixPort {
    
    private final JpaChavePixRepository jpaChavePixRepository;
    private final ChavePixMapper chavePixMapper;
    
    @Override
    public ChavePix save(ChavePix chavePix) {
        log.info("Salvando chave pix");
        ChavePixEntity chavePixEntity = chavePixMapper.domainToEntity(chavePix);
        ChavePixEntity savedChavePix = jpaChavePixRepository.save(chavePixEntity);
  
        log.info("Chave pix salva com sucesso");
        return chavePixMapper.entityToDomain(savedChavePix);
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
        ChavePixEntity chavePixEntity = jpaChavePixRepository.findByValorChave(valorChave);
        log.info("Chave pix encontrada: {}", chavePixEntity);
        return chavePixMapper.entityToDomain(chavePixEntity);
    }

    @Override
    public int countByNumeroAgenciaAndNumeroConta(Integer numeroAgencia, Integer numeroConta) {
        log.info("Contando chaves pix por número de agência e conta");
        int count = jpaChavePixRepository.countByNumeroAgenciaAndNumeroConta(numeroAgencia, numeroConta);
        log.info("Total de chaves pix: {}", count);
        return count;
    }
    
}
