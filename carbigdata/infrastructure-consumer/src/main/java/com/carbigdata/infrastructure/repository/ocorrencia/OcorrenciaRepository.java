package com.carbigdata.infrastructure.repository.ocorrencia;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcorrenciaRepository extends ReactiveCrudRepository<OcorrenciaEntiity, Long> {
}
