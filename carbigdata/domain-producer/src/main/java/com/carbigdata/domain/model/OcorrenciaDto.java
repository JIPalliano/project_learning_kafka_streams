package com.carbigdata.domain.model;

import lombok.Builder;

@Builder
public record OcorrenciaDto(
    Long codCliente,
    String cpf,
    String logradouro,
    String bairro,
    String cep,
    String cidade,
    String estado,
    Double latitude,
    Double longitude,
    String imagemBase64
) {}
