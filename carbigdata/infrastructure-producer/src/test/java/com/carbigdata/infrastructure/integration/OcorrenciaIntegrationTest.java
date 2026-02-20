package com.carbigdata.infrastructure.integration;

import com.carbigdata.domain.model.OcorrenciaDto;
import com.carbigdata.infrastructure.kafka.KafkaOcorrenciaAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {KafkaOcorrenciaAdapter.class})
@EnableAutoConfiguration // Garante que Jackson e Spring Cloud Stream sejam configurados
@Import(TestChannelBinderConfiguration.class)
class OcorrenciaIntegrationTest {

    @Autowired
    private KafkaOcorrenciaAdapter adapter;

    @Autowired
    private OutputDestination outputDestination;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveEnviarOcorrenciaParaKafkaComSucesso() {
        var ocorrencia = OcorrenciaDto.builder()
                .codCliente(1050L)
                .cpf("12345678909")
                .logradouro("Rua Teste")
                .bairro("Bairro Teste")
                .cep("12345678")
                .cidade("Cidade Teste")
                .estado("RS")
                .latitude(-29.0)
                .longitude(-50.0)
                .imagemBase64("base64dummy")
                .build();

        StepVerifier.create(adapter.enviarOcorrencia(ocorrencia))
                .expectNext(true)
                .verifyComplete();

        Message<byte[]> message = outputDestination.receive(1000, "enviarOcorrencia-out-0");
        assertThat(message).isNotNull();

        try {
            OcorrenciaDto payload = objectMapper.readValue(message.getPayload(), OcorrenciaDto.class);
            assertThat(payload.codCliente()).isEqualTo(1050L);
            assertThat(payload.cpf()).isEqualTo("12345678909");
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler payload JSON", e);
        }
    }
}
