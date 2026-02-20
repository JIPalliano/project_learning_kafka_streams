package com.carbigdata.domain.port;

import com.carbigdata.domain.model.OcorrenciaDto;
import reactor.core.publisher.Mono;

public interface OcorrenciaMessageApiPort {

    Mono<Boolean> enviarOcorrencia(OcorrenciaDto ocorrencia);

}
