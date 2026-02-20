package com.carbigdata.infrastructure.repository.endereco;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends ReactiveCrudRepository<EnderecoEntity, Long> {
}
