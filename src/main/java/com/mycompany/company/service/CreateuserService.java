package com.mycompany.company.service;

import com.mycompany.company.domain.Createuser;
import com.mycompany.company.repository.CreateuserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.mycompany.company.domain.Createuser}.
 */
@Service
public class CreateuserService {

    private static final Logger log = LoggerFactory.getLogger(CreateuserService.class);

    private final CreateuserRepository createuserRepository;

    public CreateuserService(CreateuserRepository createuserRepository) {
        this.createuserRepository = createuserRepository;
    }

    /**
     * Save a createuser.
     *
     * @param createuser the entity to save.
     * @return the persisted entity.
     */
    public Mono<Createuser> save(Createuser createuser) {
        log.debug("Request to save Createuser : {}", createuser);
        return createuserRepository.save(createuser);
    }

    /**
     * Update a createuser.
     *
     * @param createuser the entity to save.
     * @return the persisted entity.
     */
    public Mono<Createuser> update(Createuser createuser) {
        log.debug("Request to update Createuser : {}", createuser);
        return createuserRepository.save(createuser);
    }

    /**
     * Partially update a createuser.
     *
     * @param createuser the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<Createuser> partialUpdate(Createuser createuser) {
        log.debug("Request to partially update Createuser : {}", createuser);

        return createuserRepository
            .findById(createuser.getId())
            .map(existingCreateuser -> {
                if (createuser.getRollNo() != null) {
                    existingCreateuser.setRollNo(createuser.getRollNo());
                }
                if (createuser.getUserName() != null) {
                    existingCreateuser.setUserName(createuser.getUserName());
                }
                if (createuser.getPassword() != null) {
                    existingCreateuser.setPassword(createuser.getPassword());
                }
                if (createuser.getDepartment() != null) {
                    existingCreateuser.setDepartment(createuser.getDepartment());
                }
                if (createuser.getDesignation() != null) {
                    existingCreateuser.setDesignation(createuser.getDesignation());
                }
                if (createuser.getEmail() != null) {
                    existingCreateuser.setEmail(createuser.getEmail());
                }
                if (createuser.getUserImage() != null) {
                    existingCreateuser.setUserImage(createuser.getUserImage());
                }
                if (createuser.getUserImageContentType() != null) {
                    existingCreateuser.setUserImageContentType(createuser.getUserImageContentType());
                }
                if (createuser.getRoleStatus() != null) {
                    existingCreateuser.setRoleStatus(createuser.getRoleStatus());
                }
                if (createuser.getRole() != null) {
                    existingCreateuser.setRole(createuser.getRole());
                }

                return existingCreateuser;
            })
            .flatMap(createuserRepository::save);
    }

    /**
     * Get all the createusers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Flux<Createuser> findAll(Pageable pageable) {
        log.debug("Request to get all Createusers");
        return createuserRepository.findAllBy(pageable);
    }

    /**
     * Returns the number of createusers available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return createuserRepository.count();
    }

    /**
     * Get one createuser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Mono<Createuser> findOne(String id) {
        log.debug("Request to get Createuser : {}", id);
        return createuserRepository.findById(id);
    }

    /**
     * Delete the createuser by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Createuser : {}", id);
        return createuserRepository.deleteById(id);
    }
}
