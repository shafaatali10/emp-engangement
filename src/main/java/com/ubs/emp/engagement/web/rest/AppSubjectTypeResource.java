package com.ubs.emp.engagement.web.rest;

import com.ubs.emp.engagement.domain.AppSubjectType;
import com.ubs.emp.engagement.repository.AppSubjectTypeRepository;
import com.ubs.emp.engagement.service.AppSubjectTypeService;
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
 * REST controller for managing {@link com.ubs.emp.engagement.domain.AppSubjectType}.
 */
@RestController
@RequestMapping("/api/app-subject-types")
public class AppSubjectTypeResource {

    private final Logger log = LoggerFactory.getLogger(AppSubjectTypeResource.class);

    private static final String ENTITY_NAME = "appSubjectType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppSubjectTypeService appSubjectTypeService;

    private final AppSubjectTypeRepository appSubjectTypeRepository;

    public AppSubjectTypeResource(AppSubjectTypeService appSubjectTypeService, AppSubjectTypeRepository appSubjectTypeRepository) {
        this.appSubjectTypeService = appSubjectTypeService;
        this.appSubjectTypeRepository = appSubjectTypeRepository;
    }

    /**
     * {@code POST  /app-subject-types} : Create a new appSubjectType.
     *
     * @param appSubjectType the appSubjectType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appSubjectType, or with status {@code 400 (Bad Request)} if the appSubjectType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AppSubjectType> createAppSubjectType(@RequestBody AppSubjectType appSubjectType) throws URISyntaxException {
        log.debug("REST request to save AppSubjectType : {}", appSubjectType);
        if (appSubjectType.getId() != null) {
            throw new BadRequestAlertException("A new appSubjectType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        appSubjectType = appSubjectTypeService.save(appSubjectType);
        return ResponseEntity.created(new URI("/api/app-subject-types/" + appSubjectType.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, appSubjectType.getId().toString()))
            .body(appSubjectType);
    }

    /**
     * {@code PUT  /app-subject-types/:id} : Updates an existing appSubjectType.
     *
     * @param id the id of the appSubjectType to save.
     * @param appSubjectType the appSubjectType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appSubjectType,
     * or with status {@code 400 (Bad Request)} if the appSubjectType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appSubjectType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppSubjectType> updateAppSubjectType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppSubjectType appSubjectType
    ) throws URISyntaxException {
        log.debug("REST request to update AppSubjectType : {}, {}", id, appSubjectType);
        if (appSubjectType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appSubjectType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appSubjectTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        appSubjectType = appSubjectTypeService.update(appSubjectType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appSubjectType.getId().toString()))
            .body(appSubjectType);
    }

    /**
     * {@code PATCH  /app-subject-types/:id} : Partial updates given fields of an existing appSubjectType, field will ignore if it is null
     *
     * @param id the id of the appSubjectType to save.
     * @param appSubjectType the appSubjectType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appSubjectType,
     * or with status {@code 400 (Bad Request)} if the appSubjectType is not valid,
     * or with status {@code 404 (Not Found)} if the appSubjectType is not found,
     * or with status {@code 500 (Internal Server Error)} if the appSubjectType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppSubjectType> partialUpdateAppSubjectType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppSubjectType appSubjectType
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppSubjectType partially : {}, {}", id, appSubjectType);
        if (appSubjectType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appSubjectType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appSubjectTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppSubjectType> result = appSubjectTypeService.partialUpdate(appSubjectType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appSubjectType.getId().toString())
        );
    }

    /**
     * {@code GET  /app-subject-types} : get all the appSubjectTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appSubjectTypes in body.
     */
    @GetMapping("")
    public List<AppSubjectType> getAllAppSubjectTypes() {
        log.debug("REST request to get all AppSubjectTypes");
        return appSubjectTypeService.findAll();
    }

    /**
     * {@code GET  /app-subject-types/:id} : get the "id" appSubjectType.
     *
     * @param id the id of the appSubjectType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appSubjectType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppSubjectType> getAppSubjectType(@PathVariable("id") Long id) {
        log.debug("REST request to get AppSubjectType : {}", id);
        Optional<AppSubjectType> appSubjectType = appSubjectTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appSubjectType);
    }

    /**
     * {@code DELETE  /app-subject-types/:id} : delete the "id" appSubjectType.
     *
     * @param id the id of the appSubjectType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppSubjectType(@PathVariable("id") Long id) {
        log.debug("REST request to delete AppSubjectType : {}", id);
        appSubjectTypeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
