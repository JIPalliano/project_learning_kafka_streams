package com.carbigdata.contract;

import com.carbigdata.contract.mapper.OcorrenciaMapper;
import com.carbigdata.contract.request.OcorrenciaRequest;
import com.carbigdata.domain.port.OcorrenciaMessageApiPort;
import com.carbigdata.domain.service.IngestaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/v1/ocorrencias")
@RequiredArgsConstructor
public class OcorrenciaController {

    private final OcorrenciaMessageApiPort ocorrenciaMessageApiPort;
    private final OcorrenciaMapper ocorrenciaMapper;

    @PostMapping
    public Mono<ResponseEntity<Void>> criarOcorrencia(@Valid @RequestBody OcorrenciaRequest request) {
        log.info("Recebendo nova ocorrência para o cliente: {}", request.codCliente());
        
        return ocorrenciaMessageApiPort.enviarOcorrencia(ocorrenciaMapper.toDomain(request))
                .then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()));
    }
}
