package com.carbigdata.domain.service;

import com.carbigdata.domain.model.OcorrenciaDto;
import com.carbigdata.domain.port.OcorrenciaMessageApiPort;
import com.carbigdata.domain.port.OcorrenciaMessageSpiPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngestaoService implements OcorrenciaMessageApiPort {

    private final OcorrenciaMessageSpiPort ocorrenciaMessageSpiPort;

    @Override
    public Mono<Boolean> enviarOcorrencia(OcorrenciaDto ocorrencia) {
        log.info("Iniciando ingestão da ocorrência para o cliente: {}", ocorrencia.codCliente());

        return ocorrenciaMessageSpiPort.enviarOcorrencia(ocorrencia)
                .flatMap(sucesso -> {
                    if (Boolean.TRUE.equals(sucesso)) {
                        log.info("Ocorrência enviada com sucesso para processamento assíncrono.");
                        return Mono.empty();
                    } else {
                        log.error("Falha ao enviar ocorrência para o broker.");
                        return Mono.error(new RuntimeException("Erro ao enviar ocorrência para processamento."));
                    }
                });
    }
}
