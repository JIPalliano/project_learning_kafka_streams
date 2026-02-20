package com.carbigdata.domain.dto;

import lombok.Builder;

@Builder
public record OcorrenciaEventDto(
    Long codCliente,
    String cpf,
    String logradouro,
    String bairro,
    String cep,
    String cidade,
    String estado,
    Double latitude,
    Double longitude,
    String imagemBase64,
    String urlImagem
) {}
