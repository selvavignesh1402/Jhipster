package com.mycompany.company.service;

import com.mycompany.company.domain.Department;
import com.mycompany.company.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.mycompany.company.domain.Department}.
 */
@Service
public class DepartmentService {

    private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * Save a department.
     *
     * @param department the entity to save.
     * @return the persisted entity.
     */
    public Mono<Department> save(Department department) {
        log.debug("Request to save Department : {}", department);
        return departmentRepository.save(department);
    }

    /**
     * Update a department.
     *
     * @param department the entity to save.
     * @return the persisted entity.
     */
    public Mono<Department> update(Department department) {
        log.debug("Request to update Department : {}", department);
        return departmentRepository.save(department);
    }

    /**
     * Partially update a department.
     *
     * @param department the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<Department> partialUpdate(Department department) {
        log.debug("Request to partially update Department : {}", department);

        return departmentRepository
            .findById(department.getId())
            .map(existingDepartment -> {
                if (department.getDept_name() != null) {
                    existingDepartment.setDept_name(department.getDept_name());
                }
                if (department.getDept_sname() != null) {
                    existingDepartment.setDept_sname(department.getDept_sname());
                }
                if (department.getDept_status() != null) {
                    existingDepartment.setDept_status(department.getDept_status());
                }
                if (department.getDate() != null) {
                    existingDepartment.setDate(department.getDate());
                }

                return existingDepartment;
            })
            .flatMap(departmentRepository::save);
    }

    /**
     * Get all the departments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Flux<Department> findAll(Pageable pageable) {
        log.debug("Request to get all Departments");
        return departmentRepository.findAllBy(pageable);
    }

    /**
     * Returns the number of departments available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return departmentRepository.count();
    }

    /**
     * Get one department by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Mono<Department> findOne(String id) {
        log.debug("Request to get Department : {}", id);
        return departmentRepository.findById(id);
    }

    /**
     * Delete the department by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Department : {}", id);
        return departmentRepository.deleteById(id);
    }
}
