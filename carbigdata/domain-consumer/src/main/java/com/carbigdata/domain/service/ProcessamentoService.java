package com.carbigdata.domain.service;

import com.carbigdata.domain.dto.OcorrenciaEventDto;
import com.carbigdata.domain.port.ProcessamentoApiPort;
import com.carbigdata.domain.port.ProcessamentoSpiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProcessamentoService implements ProcessamentoApiPort {

    private final ProcessamentoSpiPort processamentoSpiPort;

    @Override
    public Mono<OcorrenciaEventDto> salvaProcessoEvento(OcorrenciaEventDto ocorrencia) {
        return processamentoSpiPort.salvaProcessoEvento(ocorrencia);
    }
}
