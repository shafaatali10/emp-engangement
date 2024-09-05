package com.ubs.emp.engagement.web.rest;

import com.ubs.emp.engagement.domain.AppLookup;
import com.ubs.emp.engagement.repository.AppLookupRepository;
import com.ubs.emp.engagement.service.AppLookupService;
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
 * REST controller for managing {@link com.ubs.emp.engagement.domain.AppLookup}.
 */
@RestController
@RequestMapping("/api/app-lookups")
public class AppLookupResource {

    private final Logger log = LoggerFactory.getLogger(AppLookupResource.class);

    private static final String ENTITY_NAME = "appLookup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppLookupService appLookupService;

    private final AppLookupRepository appLookupRepository;

    public AppLookupResource(AppLookupService appLookupService, AppLookupRepository appLookupRepository) {
        this.appLookupService = appLookupService;
        this.appLookupRepository = appLookupRepository;
    }

    /**
     * {@code POST  /app-lookups} : Create a new appLookup.
     *
     * @param appLookup the appLookup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appLookup, or with status {@code 400 (Bad Request)} if the appLookup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AppLookup> createAppLookup(@RequestBody AppLookup appLookup) throws URISyntaxException {
        log.debug("REST request to save AppLookup : {}", appLookup);
        if (appLookup.getId() != null) {
            throw new BadRequestAlertException("A new appLookup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        appLookup = appLookupService.save(appLookup);
        return ResponseEntity.created(new URI("/api/app-lookups/" + appLookup.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, appLookup.getId().toString()))
            .body(appLookup);
    }

    /**
     * {@code PUT  /app-lookups/:id} : Updates an existing appLookup.
     *
     * @param id the id of the appLookup to save.
     * @param appLookup the appLookup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appLookup,
     * or with status {@code 400 (Bad Request)} if the appLookup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appLookup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppLookup> updateAppLookup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppLookup appLookup
    ) throws URISyntaxException {
        log.debug("REST request to update AppLookup : {}, {}", id, appLookup);
        if (appLookup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appLookup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appLookupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        appLookup = appLookupService.update(appLookup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appLookup.getId().toString()))
            .body(appLookup);
    }

    /**
     * {@code PATCH  /app-lookups/:id} : Partial updates given fields of an existing appLookup, field will ignore if it is null
     *
     * @param id the id of the appLookup to save.
     * @param appLookup the appLookup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appLookup,
     * or with status {@code 400 (Bad Request)} if the appLookup is not valid,
     * or with status {@code 404 (Not Found)} if the appLookup is not found,
     * or with status {@code 500 (Internal Server Error)} if the appLookup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppLookup> partialUpdateAppLookup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppLookup appLookup
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppLookup partially : {}, {}", id, appLookup);
        if (appLookup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appLookup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appLookupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppLookup> result = appLookupService.partialUpdate(appLookup);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appLookup.getId().toString())
        );
    }

    /**
     * {@code GET  /app-lookups} : get all the appLookups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appLookups in body.
     */
    @GetMapping("")
    public List<AppLookup> getAllAppLookups() {
        log.debug("REST request to get all AppLookups");
        return appLookupService.findAll();
    }

    /**
     * {@code GET  /app-lookups/:id} : get the "id" appLookup.
     *
     * @param id the id of the appLookup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appLookup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppLookup> getAppLookup(@PathVariable("id") Long id) {
        log.debug("REST request to get AppLookup : {}", id);
        Optional<AppLookup> appLookup = appLookupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appLookup);
    }

    /**
     * {@code DELETE  /app-lookups/:id} : delete the "id" appLookup.
     *
     * @param id the id of the appLookup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppLookup(@PathVariable("id") Long id) {
        log.debug("REST request to delete AppLookup : {}", id);
        appLookupService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
