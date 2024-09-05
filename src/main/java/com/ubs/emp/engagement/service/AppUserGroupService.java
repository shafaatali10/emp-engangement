package com.ubs.emp.engagement.service;

import com.ubs.emp.engagement.domain.AppUserGroup;
import com.ubs.emp.engagement.repository.AppUserGroupRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ubs.emp.engagement.domain.AppUserGroup}.
 */
@Service
@Transactional
public class AppUserGroupService {

    private final Logger log = LoggerFactory.getLogger(AppUserGroupService.class);

    private final AppUserGroupRepository appUserGroupRepository;

    public AppUserGroupService(AppUserGroupRepository appUserGroupRepository) {
        this.appUserGroupRepository = appUserGroupRepository;
    }

    /**
     * Save a appUserGroup.
     *
     * @param appUserGroup the entity to save.
     * @return the persisted entity.
     */
    public AppUserGroup save(AppUserGroup appUserGroup) {
        log.debug("Request to save AppUserGroup : {}", appUserGroup);
        return appUserGroupRepository.save(appUserGroup);
    }

    /**
     * Update a appUserGroup.
     *
     * @param appUserGroup the entity to save.
     * @return the persisted entity.
     */
    public AppUserGroup update(AppUserGroup appUserGroup) {
        log.debug("Request to update AppUserGroup : {}", appUserGroup);
        return appUserGroupRepository.save(appUserGroup);
    }

    /**
     * Partially update a appUserGroup.
     *
     * @param appUserGroup the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppUserGroup> partialUpdate(AppUserGroup appUserGroup) {
        log.debug("Request to partially update AppUserGroup : {}", appUserGroup);

        return appUserGroupRepository
            .findById(appUserGroup.getId())
            .map(existingAppUserGroup -> {
                if (appUserGroup.getGroupName() != null) {
                    existingAppUserGroup.setGroupName(appUserGroup.getGroupName());
                }
                if (appUserGroup.getEmail() != null) {
                    existingAppUserGroup.setEmail(appUserGroup.getEmail());
                }
                if (appUserGroup.getAdminUser() != null) {
                    existingAppUserGroup.setAdminUser(appUserGroup.getAdminUser());
                }

                return existingAppUserGroup;
            })
            .map(appUserGroupRepository::save);
    }

    /**
     * Get all the appUserGroups.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AppUserGroup> findAll() {
        log.debug("Request to get all AppUserGroups");
        return appUserGroupRepository.findAll();
    }

    /**
     * Get one appUserGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppUserGroup> findOne(Long id) {
        log.debug("Request to get AppUserGroup : {}", id);
        return appUserGroupRepository.findById(id);
    }

    /**
     * Delete the appUserGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppUserGroup : {}", id);
        appUserGroupRepository.deleteById(id);
    }
}
