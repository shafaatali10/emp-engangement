package com.ubs.emp.engagement.web.rest;

import static com.ubs.emp.engagement.domain.AppSubjectConfigAsserts.*;
import static com.ubs.emp.engagement.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubs.emp.engagement.IntegrationTest;
import com.ubs.emp.engagement.domain.AppSubjectConfig;
import com.ubs.emp.engagement.repository.AppSubjectConfigRepository;
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
 * Integration tests for the {@link AppSubjectConfigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppSubjectConfigResourceIT {

    private static final String DEFAULT_SUBJECT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final String DEFAULT_PAYLOAD = "AAAAAAAAAA";
    private static final String UPDATED_PAYLOAD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-subject-configs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AppSubjectConfigRepository appSubjectConfigRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppSubjectConfigMockMvc;

    private AppSubjectConfig appSubjectConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppSubjectConfig createEntity(EntityManager em) {
        AppSubjectConfig appSubjectConfig = new AppSubjectConfig()
            .subjectCode(DEFAULT_SUBJECT_CODE)
            .version(DEFAULT_VERSION)
            .payload(DEFAULT_PAYLOAD);
        return appSubjectConfig;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppSubjectConfig createUpdatedEntity(EntityManager em) {
        AppSubjectConfig appSubjectConfig = new AppSubjectConfig()
            .subjectCode(UPDATED_SUBJECT_CODE)
            .version(UPDATED_VERSION)
            .payload(UPDATED_PAYLOAD);
        return appSubjectConfig;
    }

    @BeforeEach
    public void initTest() {
        appSubjectConfig = createEntity(em);
    }

    @Test
    @Transactional
    void createAppSubjectConfig() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AppSubjectConfig
        var returnedAppSubjectConfig = om.readValue(
            restAppSubjectConfigMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appSubjectConfig)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AppSubjectConfig.class
        );

        // Validate the AppSubjectConfig in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAppSubjectConfigUpdatableFieldsEquals(returnedAppSubjectConfig, getPersistedAppSubjectConfig(returnedAppSubjectConfig));
    }

    @Test
    @Transactional
    void createAppSubjectConfigWithExistingId() throws Exception {
        // Create the AppSubjectConfig with an existing ID
        appSubjectConfig.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppSubjectConfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appSubjectConfig)))
            .andExpect(status().isBadRequest());

        // Validate the AppSubjectConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppSubjectConfigs() throws Exception {
        // Initialize the database
        appSubjectConfigRepository.saveAndFlush(appSubjectConfig);

        // Get all the appSubjectConfigList
        restAppSubjectConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appSubjectConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].subjectCode").value(hasItem(DEFAULT_SUBJECT_CODE)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].payload").value(hasItem(DEFAULT_PAYLOAD)));
    }

    @Test
    @Transactional
    void getAppSubjectConfig() throws Exception {
        // Initialize the database
        appSubjectConfigRepository.saveAndFlush(appSubjectConfig);

        // Get the appSubjectConfig
        restAppSubjectConfigMockMvc
            .perform(get(ENTITY_API_URL_ID, appSubjectConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appSubjectConfig.getId().intValue()))
            .andExpect(jsonPath("$.subjectCode").value(DEFAULT_SUBJECT_CODE))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.payload").value(DEFAULT_PAYLOAD));
    }

    @Test
    @Transactional
    void getNonExistingAppSubjectConfig() throws Exception {
        // Get the appSubjectConfig
        restAppSubjectConfigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppSubjectConfig() throws Exception {
        // Initialize the database
        appSubjectConfigRepository.saveAndFlush(appSubjectConfig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appSubjectConfig
        AppSubjectConfig updatedAppSubjectConfig = appSubjectConfigRepository.findById(appSubjectConfig.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAppSubjectConfig are not directly saved in db
        em.detach(updatedAppSubjectConfig);
        updatedAppSubjectConfig.subjectCode(UPDATED_SUBJECT_CODE).version(UPDATED_VERSION).payload(UPDATED_PAYLOAD);

        restAppSubjectConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAppSubjectConfig.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAppSubjectConfig))
            )
            .andExpect(status().isOk());

        // Validate the AppSubjectConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAppSubjectConfigToMatchAllProperties(updatedAppSubjectConfig);
    }

    @Test
    @Transactional
    void putNonExistingAppSubjectConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubjectConfig.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppSubjectConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appSubjectConfig.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appSubjectConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppSubjectConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppSubjectConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubjectConfig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppSubjectConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appSubjectConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppSubjectConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppSubjectConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubjectConfig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppSubjectConfigMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appSubjectConfig)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppSubjectConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppSubjectConfigWithPatch() throws Exception {
        // Initialize the database
        appSubjectConfigRepository.saveAndFlush(appSubjectConfig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appSubjectConfig using partial update
        AppSubjectConfig partialUpdatedAppSubjectConfig = new AppSubjectConfig();
        partialUpdatedAppSubjectConfig.setId(appSubjectConfig.getId());

        restAppSubjectConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppSubjectConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppSubjectConfig))
            )
            .andExpect(status().isOk());

        // Validate the AppSubjectConfig in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppSubjectConfigUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAppSubjectConfig, appSubjectConfig),
            getPersistedAppSubjectConfig(appSubjectConfig)
        );
    }

    @Test
    @Transactional
    void fullUpdateAppSubjectConfigWithPatch() throws Exception {
        // Initialize the database
        appSubjectConfigRepository.saveAndFlush(appSubjectConfig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appSubjectConfig using partial update
        AppSubjectConfig partialUpdatedAppSubjectConfig = new AppSubjectConfig();
        partialUpdatedAppSubjectConfig.setId(appSubjectConfig.getId());

        partialUpdatedAppSubjectConfig.subjectCode(UPDATED_SUBJECT_CODE).version(UPDATED_VERSION).payload(UPDATED_PAYLOAD);

        restAppSubjectConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppSubjectConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppSubjectConfig))
            )
            .andExpect(status().isOk());

        // Validate the AppSubjectConfig in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppSubjectConfigUpdatableFieldsEquals(
            partialUpdatedAppSubjectConfig,
            getPersistedAppSubjectConfig(partialUpdatedAppSubjectConfig)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAppSubjectConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubjectConfig.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppSubjectConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appSubjectConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appSubjectConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppSubjectConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppSubjectConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubjectConfig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppSubjectConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appSubjectConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppSubjectConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppSubjectConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appSubjectConfig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppSubjectConfigMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(appSubjectConfig)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppSubjectConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppSubjectConfig() throws Exception {
        // Initialize the database
        appSubjectConfigRepository.saveAndFlush(appSubjectConfig);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the appSubjectConfig
        restAppSubjectConfigMockMvc
            .perform(delete(ENTITY_API_URL_ID, appSubjectConfig.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return appSubjectConfigRepository.count();
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

    protected AppSubjectConfig getPersistedAppSubjectConfig(AppSubjectConfig appSubjectConfig) {
        return appSubjectConfigRepository.findById(appSubjectConfig.getId()).orElseThrow();
    }

    protected void assertPersistedAppSubjectConfigToMatchAllProperties(AppSubjectConfig expectedAppSubjectConfig) {
        assertAppSubjectConfigAllPropertiesEquals(expectedAppSubjectConfig, getPersistedAppSubjectConfig(expectedAppSubjectConfig));
    }

    protected void assertPersistedAppSubjectConfigToMatchUpdatableProperties(AppSubjectConfig expectedAppSubjectConfig) {
        assertAppSubjectConfigAllUpdatablePropertiesEquals(
            expectedAppSubjectConfig,
            getPersistedAppSubjectConfig(expectedAppSubjectConfig)
        );
    }
}
