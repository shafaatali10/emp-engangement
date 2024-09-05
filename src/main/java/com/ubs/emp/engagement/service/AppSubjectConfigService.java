package com.ubs.emp.engagement.service;

import com.ubs.emp.engagement.domain.AppSubjectConfig;
import com.ubs.emp.engagement.repository.AppSubjectConfigRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ubs.emp.engagement.domain.AppSubjectConfig}.
 */
@Service
@Transactional
public class AppSubjectConfigService {

    private final Logger log = LoggerFactory.getLogger(AppSubjectConfigService.class);

    private final AppSubjectConfigRepository appSubjectConfigRepository;

    public AppSubjectConfigService(AppSubjectConfigRepository appSubjectConfigRepository) {
        this.appSubjectConfigRepository = appSubjectConfigRepository;
    }

    /**
     * Save a appSubjectConfig.
     *
     * @param appSubjectConfig the entity to save.
     * @return the persisted entity.
     */
    public AppSubjectConfig save(AppSubjectConfig appSubjectConfig) {
        log.debug("Request to save AppSubjectConfig : {}", appSubjectConfig);
        return appSubjectConfigRepository.save(appSubjectConfig);
    }

    /**
     * Update a appSubjectConfig.
     *
     * @param appSubjectConfig the entity to save.
     * @return the persisted entity.
     */
    public AppSubjectConfig update(AppSubjectConfig appSubjectConfig) {
        log.debug("Request to update AppSubjectConfig : {}", appSubjectConfig);
        return appSubjectConfigRepository.save(appSubjectConfig);
    }

    /**
     * Partially update a appSubjectConfig.
     *
     * @param appSubjectConfig the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppSubjectConfig> partialUpdate(AppSubjectConfig appSubjectConfig) {
        log.debug("Request to partially update AppSubjectConfig : {}", appSubjectConfig);

        return appSubjectConfigRepository
            .findById(appSubjectConfig.getId())
            .map(existingAppSubjectConfig -> {
                if (appSubjectConfig.getSubjectCode() != null) {
                    existingAppSubjectConfig.setSubjectCode(appSubjectConfig.getSubjectCode());
                }
                if (appSubjectConfig.getVersion() != null) {
                    existingAppSubjectConfig.setVersion(appSubjectConfig.getVersion());
                }
                if (appSubjectConfig.getPayload() != null) {
                    existingAppSubjectConfig.setPayload(appSubjectConfig.getPayload());
                }

                return existingAppSubjectConfig;
            })
            .map(appSubjectConfigRepository::save);
    }

    /**
     * Get all the appSubjectConfigs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AppSubjectConfig> findAll() {
        log.debug("Request to get all AppSubjectConfigs");
        return appSubjectConfigRepository.findAll();
    }

    /**
     * Get one appSubjectConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppSubjectConfig> findOne(Long id) {
        log.debug("Request to get AppSubjectConfig : {}", id);
        return appSubjectConfigRepository.findById(id);
    }

    /**
     * Delete the appSubjectConfig by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppSubjectConfig : {}", id);
        appSubjectConfigRepository.deleteById(id);
    }
}
