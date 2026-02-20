package com.carbigdata.infrastructure.kafka;

import com.carbigdata.domain.model.OcorrenciaDto;
import com.carbigdata.domain.port.OcorrenciaMessageSpiPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaOcorrenciaAdapter implements OcorrenciaMessageSpiPort {

    private final StreamBridge streamBridge;

    private static final String BINDING_NAME = "enviarOcorrencia-out-0";

    @Override
    public Mono<Boolean> enviarOcorrencia(OcorrenciaDto ocorrencia) {
        return Mono.fromCallable(() -> {
            log.debug("Enviando ocorrência para o tópico Kafka via binding: {}", BINDING_NAME);
            return streamBridge.send(BINDING_NAME, MessageBuilder.withPayload(ocorrencia).build());
        }).doOnError(e -> log.error("Erro ao publicar mensagem no Kafka", e));
    }
}
