package com.carbigdata.domain.port;

import com.carbigdata.domain.model.OcorrenciaDto;
import reactor.core.publisher.Mono;

public interface OcorrenciaMessageSpiPort {

    Mono<Boolean> enviarOcorrencia(OcorrenciaDto ocorrencia);

}
