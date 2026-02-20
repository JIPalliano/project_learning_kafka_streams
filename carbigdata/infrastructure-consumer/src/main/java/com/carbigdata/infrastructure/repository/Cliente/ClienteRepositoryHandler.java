package com.carbigdata.infrastructure.repository.Cliente;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClienteRepositoryHandler {

    private final ClienteRepository repository;

    public Mono<ClienteEntity> buscarOuSalvar(Long codCliente, String cpf) {
        return repository.findById(codCliente)
                .switchIfEmpty(
                        Mono.defer(() -> {
                            log.info("Cliente {} não encontrado. Criando novo...", codCliente);
                            ClienteEntity novoCliente = ClienteEntity.builder()
                                    .nome("Cliente Auto Gerado " + cpf)
                                    .cpf(cpf)
                                    .dataCriacao(LocalDateTime.now())
                                    .build();
                            return repository.save(novoCliente);
                        })
                )
                .doOnSuccess(c -> log.info("Cliente garantido: {}", c.getCodCliente()));
    }
}
