package com.carbigdata.infrastructure.repository.Cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("cliente")
public class ClienteEntity {
    @Id
    @Column("cod_cliente")
    private Long codCliente;

    @Column("nme_cliente")
    private String nome;

    @Column("nro_cpf")
    private String cpf;

    @Column("dta_criacao")
    private LocalDateTime dataCriacao;
}
