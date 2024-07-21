package com.mycompany.company.repository;

import com.mycompany.company.domain.Department;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Spring Data MongoDB reactive repository for the Department entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentRepository extends ReactiveMongoRepository<Department, String> {
    Flux<Department> findAllBy(Pageable pageable);
}
