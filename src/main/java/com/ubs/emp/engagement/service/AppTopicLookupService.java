package com.ubs.emp.engagement.service;

import com.ubs.emp.engagement.domain.AppTopicLookup;
import com.ubs.emp.engagement.repository.AppTopicLookupRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ubs.emp.engagement.domain.AppTopicLookup}.
 */
@Service
@Transactional
public class AppTopicLookupService {

    private final Logger log = LoggerFactory.getLogger(AppTopicLookupService.class);

    private final AppTopicLookupRepository appTopicLookupRepository;

    public AppTopicLookupService(AppTopicLookupRepository appTopicLookupRepository) {
        this.appTopicLookupRepository = appTopicLookupRepository;
    }

    /**
     * Save a appTopicLookup.
     *
     * @param appTopicLookup the entity to save.
     * @return the persisted entity.
     */
    public AppTopicLookup save(AppTopicLookup appTopicLookup) {
        log.debug("Request to save AppTopicLookup : {}", appTopicLookup);
        return appTopicLookupRepository.save(appTopicLookup);
    }

    /**
     * Update a appTopicLookup.
     *
     * @param appTopicLookup the entity to save.
     * @return the persisted entity.
     */
    public AppTopicLookup update(AppTopicLookup appTopicLookup) {
        log.debug("Request to update AppTopicLookup : {}", appTopicLookup);
        return appTopicLookupRepository.save(appTopicLookup);
    }

    /**
     * Partially update a appTopicLookup.
     *
     * @param appTopicLookup the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppTopicLookup> partialUpdate(AppTopicLookup appTopicLookup) {
        log.debug("Request to partially update AppTopicLookup : {}", appTopicLookup);

        return appTopicLookupRepository
            .findById(appTopicLookup.getId())
            .map(existingAppTopicLookup -> {
                if (appTopicLookup.getTopicCode() != null) {
                    existingAppTopicLookup.setTopicCode(appTopicLookup.getTopicCode());
                }
                if (appTopicLookup.getTopicName() != null) {
                    existingAppTopicLookup.setTopicName(appTopicLookup.getTopicName());
                }
                if (appTopicLookup.getTargetGroup() != null) {
                    existingAppTopicLookup.setTargetGroup(appTopicLookup.getTargetGroup());
                }

                return existingAppTopicLookup;
            })
            .map(appTopicLookupRepository::save);
    }

    /**
     * Get all the appTopicLookups.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AppTopicLookup> findAll() {
        log.debug("Request to get all AppTopicLookups");
        return appTopicLookupRepository.findAll();
    }

    /**
     * Get one appTopicLookup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppTopicLookup> findOne(Long id) {
        log.debug("Request to get AppTopicLookup : {}", id);
        return appTopicLookupRepository.findById(id);
    }

    /**
     * Delete the appTopicLookup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppTopicLookup : {}", id);
        appTopicLookupRepository.deleteById(id);
    }
}
