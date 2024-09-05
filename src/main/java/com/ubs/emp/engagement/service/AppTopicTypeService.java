package com.ubs.emp.engagement.service;

import com.ubs.emp.engagement.domain.AppTopicType;
import com.ubs.emp.engagement.repository.AppTopicTypeRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ubs.emp.engagement.domain.AppTopicType}.
 */
@Service
@Transactional
public class AppTopicTypeService {

    private final Logger log = LoggerFactory.getLogger(AppTopicTypeService.class);

    private final AppTopicTypeRepository appTopicTypeRepository;

    public AppTopicTypeService(AppTopicTypeRepository appTopicTypeRepository) {
        this.appTopicTypeRepository = appTopicTypeRepository;
    }

    /**
     * Save a appTopicType.
     *
     * @param appTopicType the entity to save.
     * @return the persisted entity.
     */
    public AppTopicType save(AppTopicType appTopicType) {
        log.debug("Request to save AppTopicType : {}", appTopicType);
        return appTopicTypeRepository.save(appTopicType);
    }

    /**
     * Update a appTopicType.
     *
     * @param appTopicType the entity to save.
     * @return the persisted entity.
     */
    public AppTopicType update(AppTopicType appTopicType) {
        log.debug("Request to update AppTopicType : {}", appTopicType);
        return appTopicTypeRepository.save(appTopicType);
    }

    /**
     * Partially update a appTopicType.
     *
     * @param appTopicType the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppTopicType> partialUpdate(AppTopicType appTopicType) {
        log.debug("Request to partially update AppTopicType : {}", appTopicType);

        return appTopicTypeRepository
            .findById(appTopicType.getId())
            .map(existingAppTopicType -> {
                if (appTopicType.getTopicCode() != null) {
                    existingAppTopicType.setTopicCode(appTopicType.getTopicCode());
                }
                if (appTopicType.getTopicName() != null) {
                    existingAppTopicType.setTopicName(appTopicType.getTopicName());
                }
                if (appTopicType.getTargetGroup() != null) {
                    existingAppTopicType.setTargetGroup(appTopicType.getTargetGroup());
                }

                return existingAppTopicType;
            })
            .map(appTopicTypeRepository::save);
    }

    /**
     * Get all the appTopicTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AppTopicType> findAll() {
        log.debug("Request to get all AppTopicTypes");
        return appTopicTypeRepository.findAll();
    }

    /**
     * Get one appTopicType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppTopicType> findOne(Long id) {
        log.debug("Request to get AppTopicType : {}", id);
        return appTopicTypeRepository.findById(id);
    }

    /**
     * Delete the appTopicType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppTopicType : {}", id);
        appTopicTypeRepository.deleteById(id);
    }
}
