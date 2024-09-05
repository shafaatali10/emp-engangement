package com.ubs.emp.engagement.web.rest;

import com.ubs.emp.engagement.domain.AppUserGroup;
import com.ubs.emp.engagement.repository.AppUserGroupRepository;
import com.ubs.emp.engagement.service.AppUserGroupService;
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
 * REST controller for managing {@link com.ubs.emp.engagement.domain.AppUserGroup}.
 */
@RestController
@RequestMapping("/api/app-user-groups")
public class AppUserGroupResource {

    private final Logger log = LoggerFactory.getLogger(AppUserGroupResource.class);

    private static final String ENTITY_NAME = "appUserGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppUserGroupService appUserGroupService;

    private final AppUserGroupRepository appUserGroupRepository;

    public AppUserGroupResource(AppUserGroupService appUserGroupService, AppUserGroupRepository appUserGroupRepository) {
        this.appUserGroupService = appUserGroupService;
        this.appUserGroupRepository = appUserGroupRepository;
    }

    /**
     * {@code POST  /app-user-groups} : Create a new appUserGroup.
     *
     * @param appUserGroup the appUserGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appUserGroup, or with status {@code 400 (Bad Request)} if the appUserGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AppUserGroup> createAppUserGroup(@RequestBody AppUserGroup appUserGroup) throws URISyntaxException {
        log.debug("REST request to save AppUserGroup : {}", appUserGroup);
        if (appUserGroup.getId() != null) {
            throw new BadRequestAlertException("A new appUserGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        appUserGroup = appUserGroupService.save(appUserGroup);
        return ResponseEntity.created(new URI("/api/app-user-groups/" + appUserGroup.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, appUserGroup.getId().toString()))
            .body(appUserGroup);
    }

    /**
     * {@code PUT  /app-user-groups/:id} : Updates an existing appUserGroup.
     *
     * @param id the id of the appUserGroup to save.
     * @param appUserGroup the appUserGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appUserGroup,
     * or with status {@code 400 (Bad Request)} if the appUserGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appUserGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppUserGroup> updateAppUserGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppUserGroup appUserGroup
    ) throws URISyntaxException {
        log.debug("REST request to update AppUserGroup : {}, {}", id, appUserGroup);
        if (appUserGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appUserGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appUserGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        appUserGroup = appUserGroupService.update(appUserGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appUserGroup.getId().toString()))
            .body(appUserGroup);
    }

    /**
     * {@code PATCH  /app-user-groups/:id} : Partial updates given fields of an existing appUserGroup, field will ignore if it is null
     *
     * @param id the id of the appUserGroup to save.
     * @param appUserGroup the appUserGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appUserGroup,
     * or with status {@code 400 (Bad Request)} if the appUserGroup is not valid,
     * or with status {@code 404 (Not Found)} if the appUserGroup is not found,
     * or with status {@code 500 (Internal Server Error)} if the appUserGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppUserGroup> partialUpdateAppUserGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppUserGroup appUserGroup
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppUserGroup partially : {}, {}", id, appUserGroup);
        if (appUserGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appUserGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appUserGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppUserGroup> result = appUserGroupService.partialUpdate(appUserGroup);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appUserGroup.getId().toString())
        );
    }

    /**
     * {@code GET  /app-user-groups} : get all the appUserGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appUserGroups in body.
     */
    @GetMapping("")
    public List<AppUserGroup> getAllAppUserGroups() {
        log.debug("REST request to get all AppUserGroups");
        return appUserGroupService.findAll();
    }

    /**
     * {@code GET  /app-user-groups/:id} : get the "id" appUserGroup.
     *
     * @param id the id of the appUserGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appUserGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppUserGroup> getAppUserGroup(@PathVariable("id") Long id) {
        log.debug("REST request to get AppUserGroup : {}", id);
        Optional<AppUserGroup> appUserGroup = appUserGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appUserGroup);
    }

    /**
     * {@code DELETE  /app-user-groups/:id} : delete the "id" appUserGroup.
     *
     * @param id the id of the appUserGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppUserGroup(@PathVariable("id") Long id) {
        log.debug("REST request to delete AppUserGroup : {}", id);
        appUserGroupService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
