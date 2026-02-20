package com.carbigdata.infrastructure.repository.endereco;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("endereco")
public class EnderecoEntity {
    @Id
    @Column("cod_endereco")
    private Long codEndereco;

    @Column("nme_logradouro")
    private String logradouro;

    @Column("nme_bairro")
    private String bairro;

    @Column("nro_cep")
    private String cep;

    @Column("nme_cidade")
    private String cidade;

    @Column("nme_estado")
    private String estado;
}
