package com.carbigdata.infrastructure.repository.ocorrencia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OcorrenciaRepositoryHandlerTest {

    @Mock
    private OcorrenciaRepository repository;

    @InjectMocks
    private OcorrenciaRepositoryHandler handler;

    @Test
    void deveSalvarOcorrenciaComSucesso() {
        var ocorrenciaParaSalvar = OcorrenciaEntiity.builder()
                .codCliente(1L)
                .codEndereco(2L)
                .dataOcorrencia(LocalDateTime.now())
                .statusOcorrencia("ATIVA")
                .build();

        var ocorrenciaSalva = OcorrenciaEntiity.builder()
                .codOcorrencia(100L)
                .codCliente(1L)
                .codEndereco(2L)
                .dataOcorrencia(LocalDateTime.now())
                .statusOcorrencia("ATIVA")
                .build();

        when(repository.save(any(OcorrenciaEntiity.class))).thenReturn(Mono.just(ocorrenciaSalva));

        StepVerifier.create(handler.salvar(ocorrenciaParaSalvar))
                .expectNextMatches(salva ->
                        salva.getCodOcorrencia().equals(100L) &&
                                salva.getCodCliente().equals(1L) &&
                                salva.getStatusOcorrencia().equals("ATIVA")
                )
                .verifyComplete();
    }
}