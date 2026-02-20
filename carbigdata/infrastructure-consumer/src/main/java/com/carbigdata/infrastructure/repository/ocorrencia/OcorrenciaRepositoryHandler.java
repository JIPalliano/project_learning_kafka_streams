package com.carbigdata.infrastructure.repository.ocorrencia;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class OcorrenciaRepositoryHandler {

    private final OcorrenciaRepository repository;

    public Mono<OcorrenciaEntiity> salvar(OcorrenciaEntiity entity) {
        return repository.save(entity)
                .doOnError(e -> log.error("Erro ao salvar ocorrência no banco: {}", e.getMessage()));
    }
}
