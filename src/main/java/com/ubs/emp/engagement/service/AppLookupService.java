package com.ubs.emp.engagement.service;

import com.ubs.emp.engagement.domain.AppLookup;
import com.ubs.emp.engagement.repository.AppLookupRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ubs.emp.engagement.domain.AppLookup}.
 */
@Service
@Transactional
public class AppLookupService {

    private final Logger log = LoggerFactory.getLogger(AppLookupService.class);

    private final AppLookupRepository appLookupRepository;

    public AppLookupService(AppLookupRepository appLookupRepository) {
        this.appLookupRepository = appLookupRepository;
    }

    /**
     * Save a appLookup.
     *
     * @param appLookup the entity to save.
     * @return the persisted entity.
     */
    public AppLookup save(AppLookup appLookup) {
        log.debug("Request to save AppLookup : {}", appLookup);
        return appLookupRepository.save(appLookup);
    }

    /**
     * Update a appLookup.
     *
     * @param appLookup the entity to save.
     * @return the persisted entity.
     */
    public AppLookup update(AppLookup appLookup) {
        log.debug("Request to update AppLookup : {}", appLookup);
        return appLookupRepository.save(appLookup);
    }

    /**
     * Partially update a appLookup.
     *
     * @param appLookup the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppLookup> partialUpdate(AppLookup appLookup) {
        log.debug("Request to partially update AppLookup : {}", appLookup);

        return appLookupRepository
            .findById(appLookup.getId())
            .map(existingAppLookup -> {
                if (appLookup.getLookupCode() != null) {
                    existingAppLookup.setLookupCode(appLookup.getLookupCode());
                }
                if (appLookup.getDisplayValue() != null) {
                    existingAppLookup.setDisplayValue(appLookup.getDisplayValue());
                }
                if (appLookup.getSequence() != null) {
                    existingAppLookup.setSequence(appLookup.getSequence());
                }
                if (appLookup.getCategory() != null) {
                    existingAppLookup.setCategory(appLookup.getCategory());
                }
                if (appLookup.getDependentCode() != null) {
                    existingAppLookup.setDependentCode(appLookup.getDependentCode());
                }

                return existingAppLookup;
            })
            .map(appLookupRepository::save);
    }

    /**
     * Get all the appLookups.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AppLookup> findAll() {
        log.debug("Request to get all AppLookups");
        return appLookupRepository.findAll();
    }

    /**
     * Get one appLookup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppLookup> findOne(Long id) {
        log.debug("Request to get AppLookup : {}", id);
        return appLookupRepository.findById(id);
    }

    /**
     * Delete the appLookup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppLookup : {}", id);
        appLookupRepository.deleteById(id);
    }
}
