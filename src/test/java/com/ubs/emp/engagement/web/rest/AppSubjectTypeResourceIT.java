package com.ubs.emp.engagement.web.rest;

import static com.ubs.emp.engagement.domain.AppSubjectTypeAsserts.*;
import static com.ubs.emp.engagement.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubs.emp.engagement.IntegrationTest;
import com.ubs.emp.engagement.domain.AppSubjectType;
import com.ubs.emp.engagement.repository.AppSubjectTypeRepository;
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
 * Integration tests for the {@link AppSubjectTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppSubjectTypeResourceIT {

    private static final String DEFAULT_SUBJECT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-subject-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AppSubjectTypeRepository appSubjectTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppSubjectTypeMockMvc;

    private AppSubjectType appSubjectType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppSubjectType createEntity(EntityManager em) {
        AppSubjectType appSubjectType = new AppSubjectType().subjectCode(DEFAULT_SUBJECT_CODE).description(DEFAULT_DESCRIPTION);
        return appSubjectType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppSubjectType createUpdatedEntity(EntityManager em) {
        AppSubjectType appSubjectType = new AppSubjectType().subjectCode(UPDATED_SUBJECT_CODE).description(UPDATED_DESCRIPTION);
        return appSubjectType;
    }

    @BeforeEach
    public void initTest() {
        appSubjectType = createEntity(em);
    }

    @Test
    @Transactional
    void createAppSubjectType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AppSubjectType
        var returnedAppSubjectType = om.readValue(
            restAppSubjectTypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appSubjectType)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AppSubjectType.class
        );

        // Validate the AppSubjectType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAppSubjectTypeUpdatableFieldsEquals(returnedAppSubjectType, getPersistedAppSubjectType(returnedAppSubjectType));
    }

    @Test
    @Transactional
    void createAppSubjectTypeWithExistingId() throws Exception {
        // Create the AppSubjectType with an existing ID
        appSubjectType.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppSubjectTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appSubjectType)))
            .andExpect(status().isBadRequest());

        // Validate the AppSubjectType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppSubjectTypes() throws Exception {
        // Initialize the database
        appSubjectTypeRepository.saveAndFlush(appSubjectType);

        // Get all the appSubjectTypeList
        restAppSubjectTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appSubjectType.getId().intValue())))
            .andExpect(jsonPath("$.[*].subjectCode").value(hasItem(DEFAULT_SUBJECT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getAppSubjectType() throws Exception {
        // Initialize the database
        appSubjectTypeRepository.saveAndFlush(appSubjectType);

        // Get the appSubjectType
        restAppSubjectTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, appSubjectType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appSubjectType.getId().intValue()))
            .andExpect(jsonPath("$.subjectCode").value(DEFAULT_SUBJECT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingAppSubjectType() throws Exception {
        // Get the appSubjectType
        restAppSubjectTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppSubjectType() throws Exception {
        // Initialize the database
        appSubjectTypeRepository.saveAndFlush(appSubjectType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appSubjectType
        AppSubjectType updatedAppSubjectType = appSubjectTypeRepository.findById(appSubjectType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAppSubjectType are not directly saved in db
        em.detach(updatedAppSubjectType);
        updatedAppSubjectType.subjectCode(UPDATED_SUBJECT_CODE).description(UPDATED_DESCRIPTION);

        restAppSubjectTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAppSubjectType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAppSubjectType))
            )
            .andExpect(status().isOk());

        // Validate the AppSubjectType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAppSubjectTypeToMatchAllProperties(updatedAppSubjectType);
    }

    @Test
    @Transactional
    void putNonExistingAppSubjectType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubjectType.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppSubjectTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appSubjectType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appSubjectType))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppSubjectType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppSubjectType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubjectType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppSubjectTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appSubjectType))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppSubjectType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppSubjectType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubjectType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppSubjectTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appSubjectType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppSubjectType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppSubjectTypeWithPatch() throws Exception {
        // Initialize the database
        appSubjectTypeRepository.saveAndFlush(appSubjectType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appSubjectType using partial update
        AppSubjectType partialUpdatedAppSubjectType = new AppSubjectType();
        partialUpdatedAppSubjectType.setId(appSubjectType.getId());

        partialUpdatedAppSubjectType.subjectCode(UPDATED_SUBJECT_CODE).description(UPDATED_DESCRIPTION);

        restAppSubjectTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppSubjectType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppSubjectType))
            )
            .andExpect(status().isOk());

        // Validate the AppSubjectType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppSubjectTypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAppSubjectType, appSubjectType),
            getPersistedAppSubjectType(appSubjectType)
        );
    }

    @Test
    @Transactional
    void fullUpdateAppSubjectTypeWithPatch() throws Exception {
        // Initialize the database
        appSubjectTypeRepository.saveAndFlush(appSubjectType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appSubjectType using partial update
        AppSubjectType partialUpdatedAppSubjectType = new AppSubjectType();
        partialUpdatedAppSubjectType.setId(appSubjectType.getId());

        partialUpdatedAppSubjectType.subjectCode(UPDATED_SUBJECT_CODE).description(UPDATED_DESCRIPTION);

        restAppSubjectTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppSubjectType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppSubjectType))
            )
            .andExpect(status().isOk());

        // Validate the AppSubjectType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppSubjectTypeUpdatableFieldsEquals(partialUpdatedAppSubjectType, getPersistedAppSubjectType(partialUpdatedAppSubjectType));
    }

    @Test
    @Transactional
    void patchNonExistingAppSubjectType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubjectType.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppSubjectTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appSubjectType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appSubjectType))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppSubjectType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppSubjectType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubjectType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppSubjectTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appSubjectType))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppSubjectType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppSubjectType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubjectType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppSubjectTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(appSubjectType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppSubjectType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppSubjectType() throws Exception {
        // Initialize the database
        appSubjectTypeRepository.saveAndFlush(appSubjectType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the appSubjectType
        restAppSubjectTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, appSubjectType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return appSubjectTypeRepository.count();
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

    protected AppSubjectType getPersistedAppSubjectType(AppSubjectType appSubjectType) {
        return appSubjectTypeRepository.findById(appSubjectType.getId()).orElseThrow();
    }

    protected void assertPersistedAppSubjectTypeToMatchAllProperties(AppSubjectType expectedAppSubjectType) {
        assertAppSubjectTypeAllPropertiesEquals(expectedAppSubjectType, getPersistedAppSubjectType(expectedAppSubjectType));
    }

    protected void assertPersistedAppSubjectTypeToMatchUpdatableProperties(AppSubjectType expectedAppSubjectType) {
        assertAppSubjectTypeAllUpdatablePropertiesEquals(expectedAppSubjectType, getPersistedAppSubjectType(expectedAppSubjectType));
    }
}
