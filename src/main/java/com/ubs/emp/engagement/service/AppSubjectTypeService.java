package com.ubs.emp.engagement.service;

import com.ubs.emp.engagement.domain.AppSubjectType;
import com.ubs.emp.engagement.repository.AppSubjectTypeRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ubs.emp.engagement.domain.AppSubjectType}.
 */
@Service
@Transactional
public class AppSubjectTypeService {

    private final Logger log = LoggerFactory.getLogger(AppSubjectTypeService.class);

    private final AppSubjectTypeRepository appSubjectTypeRepository;

    public AppSubjectTypeService(AppSubjectTypeRepository appSubjectTypeRepository) {
        this.appSubjectTypeRepository = appSubjectTypeRepository;
    }

    /**
     * Save a appSubjectType.
     *
     * @param appSubjectType the entity to save.
     * @return the persisted entity.
     */
    public AppSubjectType save(AppSubjectType appSubjectType) {
        log.debug("Request to save AppSubjectType : {}", appSubjectType);
        return appSubjectTypeRepository.save(appSubjectType);
    }

    /**
     * Update a appSubjectType.
     *
     * @param appSubjectType the entity to save.
     * @return the persisted entity.
     */
    public AppSubjectType update(AppSubjectType appSubjectType) {
        log.debug("Request to update AppSubjectType : {}", appSubjectType);
        return appSubjectTypeRepository.save(appSubjectType);
    }

    /**
     * Partially update a appSubjectType.
     *
     * @param appSubjectType the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppSubjectType> partialUpdate(AppSubjectType appSubjectType) {
        log.debug("Request to partially update AppSubjectType : {}", appSubjectType);

        return appSubjectTypeRepository
            .findById(appSubjectType.getId())
            .map(existingAppSubjectType -> {
                if (appSubjectType.getSubjectCode() != null) {
                    existingAppSubjectType.setSubjectCode(appSubjectType.getSubjectCode());
                }
                if (appSubjectType.getDescription() != null) {
                    existingAppSubjectType.setDescription(appSubjectType.getDescription());
                }

                return existingAppSubjectType;
            })
            .map(appSubjectTypeRepository::save);
    }

    /**
     * Get all the appSubjectTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AppSubjectType> findAll() {
        log.debug("Request to get all AppSubjectTypes");
        return appSubjectTypeRepository.findAll();
    }

    /**
     * Get one appSubjectType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppSubjectType> findOne(Long id) {
        log.debug("Request to get AppSubjectType : {}", id);
        return appSubjectTypeRepository.findById(id);
    }

    /**
     * Delete the appSubjectType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppSubjectType : {}", id);
        appSubjectTypeRepository.deleteById(id);
    }
}
