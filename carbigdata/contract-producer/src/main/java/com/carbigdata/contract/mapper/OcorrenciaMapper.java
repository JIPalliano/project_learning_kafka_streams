package com.carbigdata.contract.mapper;

import com.carbigdata.contract.request.OcorrenciaRequest;
import com.carbigdata.domain.model.OcorrenciaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OcorrenciaMapper {
    OcorrenciaDto toDomain(OcorrenciaRequest request);
}
