package com.ubs.emp.engagement.service;

import com.ubs.emp.engagement.domain.AppSubject;
import com.ubs.emp.engagement.repository.AppSubjectRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ubs.emp.engagement.domain.AppSubject}.
 */
@Service
@Transactional
public class AppSubjectService {

    private final Logger log = LoggerFactory.getLogger(AppSubjectService.class);

    private final AppSubjectRepository appSubjectRepository;

    public AppSubjectService(AppSubjectRepository appSubjectRepository) {
        this.appSubjectRepository = appSubjectRepository;
    }

    /**
     * Save a appSubject.
     *
     * @param appSubject the entity to save.
     * @return the persisted entity.
     */
    public AppSubject save(AppSubject appSubject) {
        log.debug("Request to save AppSubject : {}", appSubject);
        return appSubjectRepository.save(appSubject);
    }

    /**
     * Update a appSubject.
     *
     * @param appSubject the entity to save.
     * @return the persisted entity.
     */
    public AppSubject update(AppSubject appSubject) {
        log.debug("Request to update AppSubject : {}", appSubject);
        return appSubjectRepository.save(appSubject);
    }

    /**
     * Partially update a appSubject.
     *
     * @param appSubject the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppSubject> partialUpdate(AppSubject appSubject) {
        log.debug("Request to partially update AppSubject : {}", appSubject);

        return appSubjectRepository
            .findById(appSubject.getId())
            .map(existingAppSubject -> {
                if (appSubject.getSubjectCode() != null) {
                    existingAppSubject.setSubjectCode(appSubject.getSubjectCode());
                }
                if (appSubject.getTopicCode() != null) {
                    existingAppSubject.setTopicCode(appSubject.getTopicCode());
                }
                if (appSubject.getStatus() != null) {
                    existingAppSubject.setStatus(appSubject.getStatus());
                }
                if (appSubject.getIsApprovalRequired() != null) {
                    existingAppSubject.setIsApprovalRequired(appSubject.getIsApprovalRequired());
                }
                if (appSubject.getIsApproved() != null) {
                    existingAppSubject.setIsApproved(appSubject.getIsApproved());
                }
                if (appSubject.getDetailsJson() != null) {
                    existingAppSubject.setDetailsJson(appSubject.getDetailsJson());
                }

                return existingAppSubject;
            })
            .map(appSubjectRepository::save);
    }

    /**
     * Get all the appSubjects.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AppSubject> findAll() {
        log.debug("Request to get all AppSubjects");
        return appSubjectRepository.findAll();
    }

    /**
     * Get one appSubject by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppSubject> findOne(Long id) {
        log.debug("Request to get AppSubject : {}", id);
        return appSubjectRepository.findById(id);
    }

    /**
     * Delete the appSubject by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppSubject : {}", id);
        appSubjectRepository.deleteById(id);
    }
}
