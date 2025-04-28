package com.modulo.chave.pix.infrastructure.db.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
import com.modulo.chave.pix.domain.model.enums.TipoContaEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chaves_pix")
public class ChavePixEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_chave", nullable = false)
    private TipoChaveEnum tipoChave;

    @Column(name = "valor_chave", unique = true, nullable = false, length = 77)
    private String valorChave;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta", nullable = false)
    private TipoContaEnum tipoConta;

    @Column(name = "numero_agencia", nullable = false, length = 4)
    private Integer numeroAgencia;

    @Column(name = "numero_conta", nullable = false, length = 8)
    private Integer numeroConta;

    @Column(name = "nome_correntista", nullable = false, length = 30)
    private String nomeCorrentista;

    @Column(name = "sobrenome_correntista", length = 45)
    private String sobrenomeCorrentista;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
}
