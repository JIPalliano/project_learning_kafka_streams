package com.carbigdata.infrastructure.repository.Cliente;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends ReactiveCrudRepository<ClienteEntity, Long> {
}
