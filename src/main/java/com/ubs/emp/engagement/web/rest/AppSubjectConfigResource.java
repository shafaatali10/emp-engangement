package com.ubs.emp.engagement.web.rest;

import com.ubs.emp.engagement.domain.AppSubjectConfig;
import com.ubs.emp.engagement.repository.AppSubjectConfigRepository;
import com.ubs.emp.engagement.service.AppSubjectConfigService;
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
 * REST controller for managing {@link com.ubs.emp.engagement.domain.AppSubjectConfig}.
 */
@RestController
@RequestMapping("/api/app-subject-configs")
public class AppSubjectConfigResource {

    private final Logger log = LoggerFactory.getLogger(AppSubjectConfigResource.class);

    private static final String ENTITY_NAME = "appSubjectConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppSubjectConfigService appSubjectConfigService;

    private final AppSubjectConfigRepository appSubjectConfigRepository;

    public AppSubjectConfigResource(
        AppSubjectConfigService appSubjectConfigService,
        AppSubjectConfigRepository appSubjectConfigRepository
    ) {
        this.appSubjectConfigService = appSubjectConfigService;
        this.appSubjectConfigRepository = appSubjectConfigRepository;
    }

    /**
     * {@code POST  /app-subject-configs} : Create a new appSubjectConfig.
     *
     * @param appSubjectConfig the appSubjectConfig to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appSubjectConfig, or with status {@code 400 (Bad Request)} if the appSubjectConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AppSubjectConfig> createAppSubjectConfig(@RequestBody AppSubjectConfig appSubjectConfig)
        throws URISyntaxException {
        log.debug("REST request to save AppSubjectConfig : {}", appSubjectConfig);
        if (appSubjectConfig.getId() != null) {
            throw new BadRequestAlertException("A new appSubjectConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        appSubjectConfig = appSubjectConfigService.save(appSubjectConfig);
        return ResponseEntity.created(new URI("/api/app-subject-configs/" + appSubjectConfig.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, appSubjectConfig.getId().toString()))
            .body(appSubjectConfig);
    }

    /**
     * {@code PUT  /app-subject-configs/:id} : Updates an existing appSubjectConfig.
     *
     * @param id the id of the appSubjectConfig to save.
     * @param appSubjectConfig the appSubjectConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appSubjectConfig,
     * or with status {@code 400 (Bad Request)} if the appSubjectConfig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appSubjectConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppSubjectConfig> updateAppSubjectConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppSubjectConfig appSubjectConfig
    ) throws URISyntaxException {
        log.debug("REST request to update AppSubjectConfig : {}, {}", id, appSubjectConfig);
        if (appSubjectConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appSubjectConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appSubjectConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        appSubjectConfig = appSubjectConfigService.update(appSubjectConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appSubjectConfig.getId().toString()))
            .body(appSubjectConfig);
    }

    /**
     * {@code PATCH  /app-subject-configs/:id} : Partial updates given fields of an existing appSubjectConfig, field will ignore if it is null
     *
     * @param id the id of the appSubjectConfig to save.
     * @param appSubjectConfig the appSubjectConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appSubjectConfig,
     * or with status {@code 400 (Bad Request)} if the appSubjectConfig is not valid,
     * or with status {@code 404 (Not Found)} if the appSubjectConfig is not found,
     * or with status {@code 500 (Internal Server Error)} if the appSubjectConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppSubjectConfig> partialUpdateAppSubjectConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppSubjectConfig appSubjectConfig
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppSubjectConfig partially : {}, {}", id, appSubjectConfig);
        if (appSubjectConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appSubjectConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appSubjectConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppSubjectConfig> result = appSubjectConfigService.partialUpdate(appSubjectConfig);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appSubjectConfig.getId().toString())
        );
    }

    /**
     * {@code GET  /app-subject-configs} : get all the appSubjectConfigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appSubjectConfigs in body.
     */
    @GetMapping("")
    public List<AppSubjectConfig> getAllAppSubjectConfigs() {
        log.debug("REST request to get all AppSubjectConfigs");
        return appSubjectConfigService.findAll();
    }

    /**
     * {@code GET  /app-subject-configs/:id} : get the "id" appSubjectConfig.
     *
     * @param id the id of the appSubjectConfig to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appSubjectConfig, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppSubjectConfig> getAppSubjectConfig(@PathVariable("id") Long id) {
        log.debug("REST request to get AppSubjectConfig : {}", id);
        Optional<AppSubjectConfig> appSubjectConfig = appSubjectConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appSubjectConfig);
    }

    /**
     * {@code DELETE  /app-subject-configs/:id} : delete the "id" appSubjectConfig.
     *
     * @param id the id of the appSubjectConfig to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppSubjectConfig(@PathVariable("id") Long id) {
        log.debug("REST request to delete AppSubjectConfig : {}", id);
        appSubjectConfigService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
