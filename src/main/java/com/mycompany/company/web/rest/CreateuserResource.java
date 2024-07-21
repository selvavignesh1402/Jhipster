package com.mycompany.company.web.rest;

import com.mycompany.company.domain.Createuser;
import com.mycompany.company.repository.CreateuserRepository;
import com.mycompany.company.service.CreateuserService;
import com.mycompany.company.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.ForwardedHeaderUtils;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.company.domain.Createuser}.
 */
@RestController
@RequestMapping("/api/createusers")
public class CreateuserResource {

    private static final Logger log = LoggerFactory.getLogger(CreateuserResource.class);

    private static final String ENTITY_NAME = "createuser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreateuserService createuserService;

    private final CreateuserRepository createuserRepository;

    public CreateuserResource(CreateuserService createuserService, CreateuserRepository createuserRepository) {
        this.createuserService = createuserService;
        this.createuserRepository = createuserRepository;
    }

    /**
     * {@code POST  /createusers} : Create a new createuser.
     *
     * @param createuser the createuser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new createuser, or with status {@code 400 (Bad Request)} if the createuser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<Createuser>> createCreateuser(@Valid @RequestBody Createuser createuser) throws URISyntaxException {
        log.debug("REST request to save Createuser : {}", createuser);
        if (createuser.getId() != null) {
            throw new BadRequestAlertException("A new createuser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return createuserService
            .save(createuser)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/createusers/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /createusers/:id} : Updates an existing createuser.
     *
     * @param id the id of the createuser to save.
     * @param createuser the createuser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated createuser,
     * or with status {@code 400 (Bad Request)} if the createuser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the createuser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Createuser>> updateCreateuser(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Createuser createuser
    ) throws URISyntaxException {
        log.debug("REST request to update Createuser : {}, {}", id, createuser);
        if (createuser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, createuser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return createuserRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return createuserService
                    .update(createuser)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(
                        result ->
                            ResponseEntity.ok()
                                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId()))
                                .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /createusers/:id} : Partial updates given fields of an existing createuser, field will ignore if it is null
     *
     * @param id the id of the createuser to save.
     * @param createuser the createuser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated createuser,
     * or with status {@code 400 (Bad Request)} if the createuser is not valid,
     * or with status {@code 404 (Not Found)} if the createuser is not found,
     * or with status {@code 500 (Internal Server Error)} if the createuser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Createuser>> partialUpdateCreateuser(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Createuser createuser
    ) throws URISyntaxException {
        log.debug("REST request to partial update Createuser partially : {}, {}", id, createuser);
        if (createuser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, createuser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return createuserRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Createuser> result = createuserService.partialUpdate(createuser);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(
                        res ->
                            ResponseEntity.ok()
                                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId()))
                                .body(res)
                    );
            });
    }

    /**
     * {@code GET  /createusers} : get all the createusers.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of createusers in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Createuser>>> getAllCreateusers(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Createusers");
        return createuserService
            .countAll()
            .zipWith(createuserService.findAll(pageable).collectList())
            .map(
                countWithEntities ->
                    ResponseEntity.ok()
                        .headers(
                            PaginationUtil.generatePaginationHttpHeaders(
                                ForwardedHeaderUtils.adaptFromForwardedHeaders(request.getURI(), request.getHeaders()),
                                new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                            )
                        )
                        .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /createusers/:id} : get the "id" createuser.
     *
     * @param id the id of the createuser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the createuser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Createuser>> getCreateuser(@PathVariable("id") String id) {
        log.debug("REST request to get Createuser : {}", id);
        Mono<Createuser> createuser = createuserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(createuser);
    }

    /**
     * {@code DELETE  /createusers/:id} : delete the "id" createuser.
     *
     * @param id the id of the createuser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteCreateuser(@PathVariable("id") String id) {
        log.debug("REST request to delete Createuser : {}", id);
        return createuserService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build()
                )
            );
    }
}
