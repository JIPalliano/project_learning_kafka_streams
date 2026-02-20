package com.carbigdata.contract.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OcorrenciaRequest(
    @NotNull(message = "O código do cliente é obrigatório")
    Long codCliente,

    @NotBlank(message = "O CPF é obrigatório")
    String cpf,

    @NotBlank(message = "Logradouro é obrigatório")
    String logradouro,

    @NotBlank(message = "Bairro é obrigatório")
    String bairro,

    @NotBlank(message = "CEP é obrigatório")
    String cep,

    @NotBlank(message = "Cidade é obrigatória")
    String cidade,

    @NotBlank(message = "Estado é obrigatório")
    String estado,

    Double latitude,
    Double longitude,

    @NotBlank(message = "A imagem em Base64 é obrigatória")
    String imagemBase64
) {}
