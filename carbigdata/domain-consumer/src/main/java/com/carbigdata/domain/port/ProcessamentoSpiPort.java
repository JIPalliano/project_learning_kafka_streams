package com.carbigdata.domain.port;

import com.carbigdata.domain.dto.OcorrenciaEventDto;
import reactor.core.publisher.Mono;

public interface ProcessamentoSpiPort {
    Mono<OcorrenciaEventDto> salvaProcessoEvento(OcorrenciaEventDto ocorrencia);
}
