package com.carbigdata.infrastructure.repository.foto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class FotoOcorrenciaRepositoryHandler {

    private final FotoOcorrenciaRepository repository;

    public Mono<FotoOcorrenciaEntity> salvar(FotoOcorrenciaEntity foto) {
        return repository.save(foto)
                .doOnSuccess(f -> log.info("Foto da ocorrência salva com sucesso. ID: {}", f.getCodFotoOcorrencia()))
                .doOnError(e -> log.error("Erro ao salvar foto da ocorrência: {}", e.getMessage()));
    }
}
