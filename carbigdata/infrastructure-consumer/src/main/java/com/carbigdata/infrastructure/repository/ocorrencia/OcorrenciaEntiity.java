package com.carbigdata.infrastructure.repository.ocorrencia;

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
@Table("ocorrencia")
public class OcorrenciaEntiity {
    @Id
    @Column("cod_ocorrencia")
    private Long codOcorrencia;

    @Column("cod_cliente")
    private Long codCliente;

    @Column("cod_endereco")
    private Long codEndereco;

    @Column("dta_ocorrencia")
    private LocalDateTime dataOcorrencia;

    @Column("sta_ocorrencia")
    private String statusOcorrencia;
}
