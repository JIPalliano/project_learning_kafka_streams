package com.carbigdata.infrastructure.repository.endereco;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnderecoRepositoryHandler {

    private final EnderecoRepository repository;

    public Mono<EnderecoEntity> salvar(EnderecoEntity endereco) {
        return repository.save(endereco)
                .doOnSuccess(e -> log.info("Endereço salvo com sucesso. ID: {}", e.getCodEndereco()))
                .doOnError(e -> log.error("Erro ao salvar endereço: {}", e.getMessage()));
    }
}
