package com.carbigdata.infrastructure.repository;

import com.carbigdata.domain.dto.OcorrenciaEventDto;
import com.carbigdata.infrastructure.repository.endereco.EnderecoEntity;
import com.carbigdata.infrastructure.repository.foto.FotoOcorrenciaEntity;
import com.carbigdata.infrastructure.repository.ocorrencia.OcorrenciaEntiity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OcorrenciaImplMapper {

    @Mapping(target = "codEndereco", ignore = true)
    @Mapping(target = "logradouro", source = "logradouro")
    @Mapping(target = "bairro", source = "bairro")
    @Mapping(target = "cep", source = "cep")
    @Mapping(target = "cidade", source = "cidade")
    @Mapping(target = "estado", source = "estado")
    EnderecoEntity toEnderecoEntity(OcorrenciaEventDto dto);

    @Mapping(target = "codOcorrencia", ignore = true)
    @Mapping(target = "codCliente", source = "dto.codCliente")
    @Mapping(target = "codEndereco", source = "codEndereco")
    @Mapping(target = "dataOcorrencia", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "statusOcorrencia", constant = "ATIVA")
    OcorrenciaEntiity toOcorrenciaEntity(OcorrenciaEventDto dto, Long codEndereco);

    @Mapping(target = "codFotoOcorrencia", ignore = true)
    @Mapping(target = "codOcorrencia", source = "codOcorrencia")
    @Mapping(target = "dataCriacao", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "pathBucket", source = "urlImagem")
    @Mapping(target = "hash", expression = "java(java.util.UUID.randomUUID().toString())")
    FotoOcorrenciaEntity toFotoEntity(Long codOcorrencia, String urlImagem);

    @Mapping(target = "imagemBase64", ignore = true)
    @Mapping(target = "urlImagem", source = "urlImagem")
    @Mapping(target = "logradouro", ignore = true) 
    @Mapping(target = "bairro", ignore = true)
    @Mapping(target = "cep", ignore = true)
    @Mapping(target = "cidade", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "latitude", ignore = true)
    @Mapping(target = "longitude", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    OcorrenciaEventDto toDto(OcorrenciaEntiity entity, String urlImagem);
}
