package com.carbigdata.infrastructure.repository.foto;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoOcorrenciaRepository extends ReactiveCrudRepository<FotoOcorrenciaEntity, Long> {
}
