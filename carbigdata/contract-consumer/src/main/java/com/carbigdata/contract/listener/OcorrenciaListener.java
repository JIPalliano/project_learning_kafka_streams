package com.carbigdata.contract.listener;

import com.carbigdata.domain.dto.OcorrenciaEventDto;
import com.carbigdata.domain.port.ProcessamentoApiPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OcorrenciaListener {

    private final ProcessamentoApiPort processamentoApiPort;

    @Bean
    public Consumer<KStream<String, OcorrenciaEventDto>> processarOcorrencia() {
        return input -> input
                .peek((key, value) -> log.info("Recebendo evento do Kafka Streams: {}", value))
                .foreach((key, value) -> {
                    try {
                        processamentoApiPort.salvaProcessoEvento(value)
                                .subscribe(
                                        sucesso -> log.info("Evento processado com sucesso: {}", sucesso),
                                        erro -> {
                                            log.error("Erro ao processar evento no fluxo reativo: {}", erro.getMessage(), erro);
                                        }
                                );
                    } catch (Exception e) {
                        log.error("Erro inesperado no Listener: {}", e.getMessage(), e);
                    }
                });
    }
}
