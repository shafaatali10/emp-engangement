package com.ubs.emp.engagement.web.rest;

import com.ubs.emp.engagement.domain.AppTopicLookup;
import com.ubs.emp.engagement.repository.AppTopicLookupRepository;
import com.ubs.emp.engagement.service.AppTopicLookupService;
import com.ubs.emp.engagement.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ubs.emp.engagement.domain.AppTopicLookup}.
 */
@RestController
@RequestMapping("/api/app-topic-lookups")
public class AppTopicLookupResource {

    private final Logger log = LoggerFactory.getLogger(AppTopicLookupResource.class);

    private static final String ENTITY_NAME = "appTopicLookup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppTopicLookupService appTopicLookupService;

    private final AppTopicLookupRepository appTopicLookupRepository;

    public AppTopicLookupResource(AppTopicLookupService appTopicLookupService, AppTopicLookupRepository appTopicLookupRepository) {
        this.appTopicLookupService = appTopicLookupService;
        this.appTopicLookupRepository = appTopicLookupRepository;
    }

    /**
     * {@code POST  /app-topic-lookups} : Create a new appTopicLookup.
     *
     * @param appTopicLookup the appTopicLookup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appTopicLookup, or with status {@code 400 (Bad Request)} if the appTopicLookup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AppTopicLookup> createAppTopicLookup(@RequestBody AppTopicLookup appTopicLookup) throws URISyntaxException {
        log.debug("REST request to save AppTopicLookup : {}", appTopicLookup);
        if (appTopicLookup.getId() != null) {
            throw new BadRequestAlertException("A new appTopicLookup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        appTopicLookup = appTopicLookupService.save(appTopicLookup);
        return ResponseEntity.created(new URI("/api/app-topic-lookups/" + appTopicLookup.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, appTopicLookup.getId().toString()))
            .body(appTopicLookup);
    }

    /**
     * {@code PUT  /app-topic-lookups/:id} : Updates an existing appTopicLookup.
     *
     * @param id the id of the appTopicLookup to save.
     * @param appTopicLookup the appTopicLookup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appTopicLookup,
     * or with status {@code 400 (Bad Request)} if the appTopicLookup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appTopicLookup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppTopicLookup> updateAppTopicLookup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppTopicLookup appTopicLookup
    ) throws URISyntaxException {
        log.debug("REST request to update AppTopicLookup : {}, {}", id, appTopicLookup);
        if (appTopicLookup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appTopicLookup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appTopicLookupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        appTopicLookup = appTopicLookupService.update(appTopicLookup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appTopicLookup.getId().toString()))
            .body(appTopicLookup);
    }

    /**
     * {@code PATCH  /app-topic-lookups/:id} : Partial updates given fields of an existing appTopicLookup, field will ignore if it is null
     *
     * @param id the id of the appTopicLookup to save.
     * @param appTopicLookup the appTopicLookup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appTopicLookup,
     * or with status {@code 400 (Bad Request)} if the appTopicLookup is not valid,
     * or with status {@code 404 (Not Found)} if the appTopicLookup is not found,
     * or with status {@code 500 (Internal Server Error)} if the appTopicLookup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppTopicLookup> partialUpdateAppTopicLookup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppTopicLookup appTopicLookup
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppTopicLookup partially : {}, {}", id, appTopicLookup);
        if (appTopicLookup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appTopicLookup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appTopicLookupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppTopicLookup> result = appTopicLookupService.partialUpdate(appTopicLookup);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appTopicLookup.getId().toString())
        );
    }

    /**
     * {@code GET  /app-topic-lookups} : get all the appTopicLookups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appTopicLookups in body.
     */
    @GetMapping("")
    public List<AppTopicLookup> getAllAppTopicLookups() {
        log.debug("REST request to get all AppTopicLookups");
        return appTopicLookupService.findAll();
    }

    /**
     * {@code GET  /app-topic-lookups/:id} : get the "id" appTopicLookup.
     *
     * @param id the id of the appTopicLookup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appTopicLookup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppTopicLookup> getAppTopicLookup(@PathVariable("id") Long id) {
        log.debug("REST request to get AppTopicLookup : {}", id);
        Optional<AppTopicLookup> appTopicLookup = appTopicLookupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appTopicLookup);
    }

    /**
     * {@code DELETE  /app-topic-lookups/:id} : delete the "id" appTopicLookup.
     *
     * @param id the id of the appTopicLookup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppTopicLookup(@PathVariable("id") Long id) {
        log.debug("REST request to delete AppTopicLookup : {}", id);
        appTopicLookupService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
