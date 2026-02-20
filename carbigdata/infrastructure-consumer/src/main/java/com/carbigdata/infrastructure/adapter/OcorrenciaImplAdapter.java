package com.carbigdata.infrastructure.adapter;

import com.carbigdata.domain.dto.OcorrenciaEventDto;
import com.carbigdata.domain.port.ProcessamentoSpiPort;
import com.carbigdata.infrastructure.repository.Cliente.ClienteRepositoryHandler;
import com.carbigdata.infrastructure.repository.OcorrenciaImplMapper;
import com.carbigdata.infrastructure.repository.endereco.EnderecoRepositoryHandler;
import com.carbigdata.infrastructure.repository.foto.FotoOcorrenciaRepositoryHandler;
import com.carbigdata.infrastructure.repository.ocorrencia.OcorrenciaRepositoryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.Base64;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OcorrenciaImplAdapter implements ProcessamentoSpiPort {

    private final S3AsyncClient s3AsyncClient;
    private final OcorrenciaRepositoryHandler ocorrenciaHandler;
    private final EnderecoRepositoryHandler enderecoHandler;
    private final FotoOcorrenciaRepositoryHandler fotoHandler;
    private final ClienteRepositoryHandler clienteHandler;
    private final OcorrenciaImplMapper mapper;

    @Value("${carbigdata.storage.bucket-name}")
    private String bucketName;

    @Value("${carbigdata.storage.endpoint}")
    private String minioEndpoint;

    @Override
    public Mono<OcorrenciaEventDto> salvaProcessoEvento(OcorrenciaEventDto ocorrencia) {
        log.info("Iniciando processamento da ocorrência. Cliente informado: {}", ocorrencia.codCliente());

        return Mono.fromCallable(() -> Base64.getDecoder().decode(ocorrencia.imagemBase64()))
                .flatMap(bytes -> {
                    String nomeArquivo = "ocorrencia_" + ocorrencia.codCliente() + "_" + UUID.randomUUID() + ".jpg";
                    return uploadArquivo(nomeArquivo, bytes);
                })
                .flatMap(url -> {
                    return clienteHandler.buscarOuSalvar(ocorrencia.codCliente(), ocorrencia.cpf())
                            .flatMap(clienteSalvo -> {
                                log.debug("Cliente vinculado: {}", clienteSalvo.getCodCliente());

                                var endereco = mapper.toEnderecoEntity(ocorrencia);
                                return enderecoHandler.salvar(endereco)
                                        .flatMap(savedEndereco -> {
                                            var entity = mapper.toOcorrenciaEntity(ocorrencia, savedEndereco.getCodEndereco());
                                            entity.setCodCliente(clienteSalvo.getCodCliente());

                                            return ocorrenciaHandler.salvar(entity)
                                                    .flatMap(savedOcorrencia -> {

                                                        var foto = mapper.toFotoEntity(savedOcorrencia.getCodOcorrencia(), url);
                                                        return fotoHandler.salvar(foto)
                                                                .map(savedFoto -> {
                                                                    log.info("Processo completo. Ocorrência ID: {}", savedOcorrencia.getCodOcorrencia());
                                                                    return mapper.toDto(savedOcorrencia, url);
                                                                });
                                                    });
                                        });
                            });
                })
                .doOnError(e -> log.error("Erro CRÍTICO no adaptador de processamento: {}", e.getMessage(), e));
    }

    private Mono<String> uploadArquivo(String nomeArquivo, byte[] conteudoBytes) {
        return Mono.fromFuture(() -> {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(nomeArquivo)
                    .contentType("image/jpeg")
                    .build();

            return s3AsyncClient.putObject(putObjectRequest, AsyncRequestBody.fromBytes(conteudoBytes));
        })
        .map(response -> {
            String url = String.format("%s/%s/%s", minioEndpoint, bucketName, nomeArquivo);
            return url;
        });
    }
}
