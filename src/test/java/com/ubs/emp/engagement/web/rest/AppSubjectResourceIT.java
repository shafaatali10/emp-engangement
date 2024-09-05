package com.ubs.emp.engagement.web.rest;

import static com.ubs.emp.engagement.domain.AppSubjectAsserts.*;
import static com.ubs.emp.engagement.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubs.emp.engagement.IntegrationTest;
import com.ubs.emp.engagement.domain.AppSubject;
import com.ubs.emp.engagement.repository.AppSubjectRepository;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AppSubjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppSubjectResourceIT {

    private static final String DEFAULT_SUBJECT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TOPIC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_APPROVAL_REQUIRED = false;
    private static final Boolean UPDATED_IS_APPROVAL_REQUIRED = true;

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;

    private static final String DEFAULT_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS_JSON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-subjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AppSubjectRepository appSubjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppSubjectMockMvc;

    private AppSubject appSubject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppSubject createEntity(EntityManager em) {
        AppSubject appSubject = new AppSubject()
            .subjectCode(DEFAULT_SUBJECT_CODE)
            .topicCode(DEFAULT_TOPIC_CODE)
            .status(DEFAULT_STATUS)
            .isApprovalRequired(DEFAULT_IS_APPROVAL_REQUIRED)
            .isApproved(DEFAULT_IS_APPROVED)
            .detailsJson(DEFAULT_DETAILS_JSON);
        return appSubject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppSubject createUpdatedEntity(EntityManager em) {
        AppSubject appSubject = new AppSubject()
            .subjectCode(UPDATED_SUBJECT_CODE)
            .topicCode(UPDATED_TOPIC_CODE)
            .status(UPDATED_STATUS)
            .isApprovalRequired(UPDATED_IS_APPROVAL_REQUIRED)
            .isApproved(UPDATED_IS_APPROVED)
            .detailsJson(UPDATED_DETAILS_JSON);
        return appSubject;
    }

    @BeforeEach
    public void initTest() {
        appSubject = createEntity(em);
    }

    @Test
    @Transactional
    void createAppSubject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AppSubject
        var returnedAppSubject = om.readValue(
            restAppSubjectMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appSubject)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AppSubject.class
        );

        // Validate the AppSubject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAppSubjectUpdatableFieldsEquals(returnedAppSubject, getPersistedAppSubject(returnedAppSubject));
    }

    @Test
    @Transactional
    void createAppSubjectWithExistingId() throws Exception {
        // Create the AppSubject with an existing ID
        appSubject.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppSubjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appSubject)))
            .andExpect(status().isBadRequest());

        // Validate the AppSubject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppSubjects() throws Exception {
        // Initialize the database
        appSubjectRepository.saveAndFlush(appSubject);

        // Get all the appSubjectList
        restAppSubjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appSubject.getId().intValue())))
            .andExpect(jsonPath("$.[*].subjectCode").value(hasItem(DEFAULT_SUBJECT_CODE)))
            .andExpect(jsonPath("$.[*].topicCode").value(hasItem(DEFAULT_TOPIC_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].isApprovalRequired").value(hasItem(DEFAULT_IS_APPROVAL_REQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].detailsJson").value(hasItem(DEFAULT_DETAILS_JSON)));
    }

    @Test
    @Transactional
    void getAppSubject() throws Exception {
        // Initialize the database
        appSubjectRepository.saveAndFlush(appSubject);

        // Get the appSubject
        restAppSubjectMockMvc
            .perform(get(ENTITY_API_URL_ID, appSubject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appSubject.getId().intValue()))
            .andExpect(jsonPath("$.subjectCode").value(DEFAULT_SUBJECT_CODE))
            .andExpect(jsonPath("$.topicCode").value(DEFAULT_TOPIC_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.isApprovalRequired").value(DEFAULT_IS_APPROVAL_REQUIRED.booleanValue()))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.detailsJson").value(DEFAULT_DETAILS_JSON));
    }

    @Test
    @Transactional
    void getNonExistingAppSubject() throws Exception {
        // Get the appSubject
        restAppSubjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppSubject() throws Exception {
        // Initialize the database
        appSubjectRepository.saveAndFlush(appSubject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appSubject
        AppSubject updatedAppSubject = appSubjectRepository.findById(appSubject.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAppSubject are not directly saved in db
        em.detach(updatedAppSubject);
        updatedAppSubject
            .subjectCode(UPDATED_SUBJECT_CODE)
            .topicCode(UPDATED_TOPIC_CODE)
            .status(UPDATED_STATUS)
            .isApprovalRequired(UPDATED_IS_APPROVAL_REQUIRED)
            .isApproved(UPDATED_IS_APPROVED)
            .detailsJson(UPDATED_DETAILS_JSON);

        restAppSubjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAppSubject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAppSubject))
            )
            .andExpect(status().isOk());

        // Validate the AppSubject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAppSubjectToMatchAllProperties(updatedAppSubject);
    }

    @Test
    @Transactional
    void putNonExistingAppSubject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppSubjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appSubject.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appSubject))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppSubject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppSubject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppSubjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appSubject))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppSubject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppSubject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppSubjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appSubject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppSubject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppSubjectWithPatch() throws Exception {
        // Initialize the database
        appSubjectRepository.saveAndFlush(appSubject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appSubject using partial update
        AppSubject partialUpdatedAppSubject = new AppSubject();
        partialUpdatedAppSubject.setId(appSubject.getId());

        partialUpdatedAppSubject
            .topicCode(UPDATED_TOPIC_CODE)
            .isApprovalRequired(UPDATED_IS_APPROVAL_REQUIRED)
            .detailsJson(UPDATED_DETAILS_JSON);

        restAppSubjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppSubject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppSubject))
            )
            .andExpect(status().isOk());

        // Validate the AppSubject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppSubjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAppSubject, appSubject),
            getPersistedAppSubject(appSubject)
        );
    }

    @Test
    @Transactional
    void fullUpdateAppSubjectWithPatch() throws Exception {
        // Initialize the database
        appSubjectRepository.saveAndFlush(appSubject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appSubject using partial update
        AppSubject partialUpdatedAppSubject = new AppSubject();
        partialUpdatedAppSubject.setId(appSubject.getId());

        partialUpdatedAppSubject
            .subjectCode(UPDATED_SUBJECT_CODE)
            .topicCode(UPDATED_TOPIC_CODE)
            .status(UPDATED_STATUS)
            .isApprovalRequired(UPDATED_IS_APPROVAL_REQUIRED)
            .isApproved(UPDATED_IS_APPROVED)
            .detailsJson(UPDATED_DETAILS_JSON);

        restAppSubjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppSubject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppSubject))
            )
            .andExpect(status().isOk());

        // Validate the AppSubject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppSubjectUpdatableFieldsEquals(partialUpdatedAppSubject, getPersistedAppSubject(partialUpdatedAppSubject));
    }

    @Test
    @Transactional
    void patchNonExistingAppSubject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubject.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppSubjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appSubject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appSubject))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppSubject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppSubject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppSubjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appSubject))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppSubject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppSubject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubject.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppSubjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(appSubject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppSubject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppSubject() throws Exception {
        // Initialize the database
        appSubjectRepository.saveAndFlush(appSubject);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the appSubject
        restAppSubjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, appSubject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return appSubjectRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected AppSubject getPersistedAppSubject(AppSubject appSubject) {
        return appSubjectRepository.findById(appSubject.getId()).orElseThrow();
    }

    protected void assertPersistedAppSubjectToMatchAllProperties(AppSubject expectedAppSubject) {
        assertAppSubjectAllPropertiesEquals(expectedAppSubject, getPersistedAppSubject(expectedAppSubject));
    }

    protected void assertPersistedAppSubjectToMatchUpdatableProperties(AppSubject expectedAppSubject) {
        assertAppSubjectAllUpdatablePropertiesEquals(expectedAppSubject, getPersistedAppSubject(expectedAppSubject));
    }
}
