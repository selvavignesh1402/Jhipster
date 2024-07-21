package com.mycompany.company.repository;

import com.mycompany.company.domain.Createuser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Spring Data MongoDB reactive repository for the Createuser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreateuserRepository extends ReactiveMongoRepository<Createuser, String> {
    Flux<Createuser> findAllBy(Pageable pageable);
}
