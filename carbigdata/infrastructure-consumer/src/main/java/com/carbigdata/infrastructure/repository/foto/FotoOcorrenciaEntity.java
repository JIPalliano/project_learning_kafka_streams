package com.carbigdata.infrastructure.repository.foto;

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
@Table("foto_ocorrencia")
public class FotoOcorrenciaEntity {
    @Id
    @Column("cod_foto_ocorrencia")
    private Long codFotoOcorrencia;

    @Column("cod_ocorrencia")
    private Long codOcorrencia;

    @Column("dta_criacao")
    private LocalDateTime dataCriacao;

    @Column("dsc_path_bucket")
    private String pathBucket;

    @Column("dsc_hash")
    private String hash;
}
