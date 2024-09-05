package com.ubs.emp.engagement.web.rest;

import com.ubs.emp.engagement.domain.AppTopicType;
import com.ubs.emp.engagement.repository.AppTopicTypeRepository;
import com.ubs.emp.engagement.service.AppTopicTypeService;
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
 * REST controller for managing {@link com.ubs.emp.engagement.domain.AppTopicType}.
 */
@RestController
@RequestMapping("/api/app-topic-types")
public class AppTopicTypeResource {

    private final Logger log = LoggerFactory.getLogger(AppTopicTypeResource.class);

    private static final String ENTITY_NAME = "appTopicType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppTopicTypeService appTopicTypeService;

    private final AppTopicTypeRepository appTopicTypeRepository;

    public AppTopicTypeResource(AppTopicTypeService appTopicTypeService, AppTopicTypeRepository appTopicTypeRepository) {
        this.appTopicTypeService = appTopicTypeService;
        this.appTopicTypeRepository = appTopicTypeRepository;
    }

    /**
     * {@code POST  /app-topic-types} : Create a new appTopicType.
     *
     * @param appTopicType the appTopicType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appTopicType, or with status {@code 400 (Bad Request)} if the appTopicType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AppTopicType> createAppTopicType(@RequestBody AppTopicType appTopicType) throws URISyntaxException {
        log.debug("REST request to save AppTopicType : {}", appTopicType);
        if (appTopicType.getId() != null) {
            throw new BadRequestAlertException("A new appTopicType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        appTopicType = appTopicTypeService.save(appTopicType);
        return ResponseEntity.created(new URI("/api/app-topic-types/" + appTopicType.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, appTopicType.getId().toString()))
            .body(appTopicType);
    }

    /**
     * {@code PUT  /app-topic-types/:id} : Updates an existing appTopicType.
     *
     * @param id the id of the appTopicType to save.
     * @param appTopicType the appTopicType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appTopicType,
     * or with status {@code 400 (Bad Request)} if the appTopicType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appTopicType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppTopicType> updateAppTopicType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppTopicType appTopicType
    ) throws URISyntaxException {
        log.debug("REST request to update AppTopicType : {}, {}", id, appTopicType);
        if (appTopicType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appTopicType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appTopicTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        appTopicType = appTopicTypeService.update(appTopicType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appTopicType.getId().toString()))
            .body(appTopicType);
    }

    /**
     * {@code PATCH  /app-topic-types/:id} : Partial updates given fields of an existing appTopicType, field will ignore if it is null
     *
     * @param id the id of the appTopicType to save.
     * @param appTopicType the appTopicType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appTopicType,
     * or with status {@code 400 (Bad Request)} if the appTopicType is not valid,
     * or with status {@code 404 (Not Found)} if the appTopicType is not found,
     * or with status {@code 500 (Internal Server Error)} if the appTopicType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppTopicType> partialUpdateAppTopicType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppTopicType appTopicType
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppTopicType partially : {}, {}", id, appTopicType);
        if (appTopicType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appTopicType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appTopicTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppTopicType> result = appTopicTypeService.partialUpdate(appTopicType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appTopicType.getId().toString())
        );
    }

    /**
     * {@code GET  /app-topic-types} : get all the appTopicTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appTopicTypes in body.
     */
    @GetMapping("")
    public List<AppTopicType> getAllAppTopicTypes() {
        log.debug("REST request to get all AppTopicTypes");
        return appTopicTypeService.findAll();
    }

    /**
     * {@code GET  /app-topic-types/:id} : get the "id" appTopicType.
     *
     * @param id the id of the appTopicType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appTopicType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppTopicType> getAppTopicType(@PathVariable("id") Long id) {
        log.debug("REST request to get AppTopicType : {}", id);
        Optional<AppTopicType> appTopicType = appTopicTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appTopicType);
    }

    /**
     * {@code DELETE  /app-topic-types/:id} : delete the "id" appTopicType.
     *
     * @param id the id of the appTopicType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppTopicType(@PathVariable("id") Long id) {
        log.debug("REST request to delete AppTopicType : {}", id);
        appTopicTypeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
