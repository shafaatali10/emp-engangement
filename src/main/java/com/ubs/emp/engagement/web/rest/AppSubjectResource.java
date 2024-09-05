package com.ubs.emp.engagement.web.rest;

import com.ubs.emp.engagement.domain.AppSubject;
import com.ubs.emp.engagement.repository.AppSubjectRepository;
import com.ubs.emp.engagement.service.AppSubjectService;
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
 * REST controller for managing {@link com.ubs.emp.engagement.domain.AppSubject}.
 */
@RestController
@RequestMapping("/api/app-subjects")
public class AppSubjectResource {

    private final Logger log = LoggerFactory.getLogger(AppSubjectResource.class);

    private static final String ENTITY_NAME = "appSubject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppSubjectService appSubjectService;

    private final AppSubjectRepository appSubjectRepository;

    public AppSubjectResource(AppSubjectService appSubjectService, AppSubjectRepository appSubjectRepository) {
        this.appSubjectService = appSubjectService;
        this.appSubjectRepository = appSubjectRepository;
    }

    /**
     * {@code POST  /app-subjects} : Create a new appSubject.
     *
     * @param appSubject the appSubject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appSubject, or with status {@code 400 (Bad Request)} if the appSubject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AppSubject> createAppSubject(@RequestBody AppSubject appSubject) throws URISyntaxException {
        log.debug("REST request to save AppSubject : {}", appSubject);
        if (appSubject.getId() != null) {
            throw new BadRequestAlertException("A new appSubject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        appSubject = appSubjectService.save(appSubject);
        return ResponseEntity.created(new URI("/api/app-subjects/" + appSubject.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, appSubject.getId().toString()))
            .body(appSubject);
    }

    /**
     * {@code PUT  /app-subjects/:id} : Updates an existing appSubject.
     *
     * @param id the id of the appSubject to save.
     * @param appSubject the appSubject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appSubject,
     * or with status {@code 400 (Bad Request)} if the appSubject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appSubject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppSubject> updateAppSubject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppSubject appSubject
    ) throws URISyntaxException {
        log.debug("REST request to update AppSubject : {}, {}", id, appSubject);
        if (appSubject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appSubject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appSubjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        appSubject = appSubjectService.update(appSubject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appSubject.getId().toString()))
            .body(appSubject);
    }

    /**
     * {@code PATCH  /app-subjects/:id} : Partial updates given fields of an existing appSubject, field will ignore if it is null
     *
     * @param id the id of the appSubject to save.
     * @param appSubject the appSubject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appSubject,
     * or with status {@code 400 (Bad Request)} if the appSubject is not valid,
     * or with status {@code 404 (Not Found)} if the appSubject is not found,
     * or with status {@code 500 (Internal Server Error)} if the appSubject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppSubject> partialUpdateAppSubject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppSubject appSubject
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppSubject partially : {}, {}", id, appSubject);
        if (appSubject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appSubject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appSubjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppSubject> result = appSubjectService.partialUpdate(appSubject);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appSubject.getId().toString())
        );
    }

    /**
     * {@code GET  /app-subjects} : get all the appSubjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appSubjects in body.
     */
    @GetMapping("")
    public List<AppSubject> getAllAppSubjects() {
        log.debug("REST request to get all AppSubjects");
        return appSubjectService.findAll();
    }

    /**
     * {@code GET  /app-subjects/:id} : get the "id" appSubject.
     *
     * @param id the id of the appSubject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appSubject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppSubject> getAppSubject(@PathVariable("id") Long id) {
        log.debug("REST request to get AppSubject : {}", id);
        Optional<AppSubject> appSubject = appSubjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appSubject);
    }

    /**
     * {@code DELETE  /app-subjects/:id} : delete the "id" appSubject.
     *
     * @param id the id of the appSubject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppSubject(@PathVariable("id") Long id) {
        log.debug("REST request to delete AppSubject : {}", id);
        appSubjectService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
