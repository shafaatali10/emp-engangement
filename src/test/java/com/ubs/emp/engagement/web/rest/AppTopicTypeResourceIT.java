package com.ubs.emp.engagement.web.rest;

import static com.ubs.emp.engagement.domain.AppTopicTypeAsserts.*;
import static com.ubs.emp.engagement.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubs.emp.engagement.IntegrationTest;
import com.ubs.emp.engagement.domain.AppTopicType;
import com.ubs.emp.engagement.repository.AppTopicTypeRepository;
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
 * Integration tests for the {@link AppTopicTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppTopicTypeResourceIT {

    private static final String DEFAULT_TOPIC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TOPIC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_GROUP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-topic-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AppTopicTypeRepository appTopicTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppTopicTypeMockMvc;

    private AppTopicType appTopicType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppTopicType createEntity(EntityManager em) {
        AppTopicType appTopicType = new AppTopicType()
            .topicCode(DEFAULT_TOPIC_CODE)
            .topicName(DEFAULT_TOPIC_NAME)
            .targetGroup(DEFAULT_TARGET_GROUP);
        return appTopicType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppTopicType createUpdatedEntity(EntityManager em) {
        AppTopicType appTopicType = new AppTopicType()
            .topicCode(UPDATED_TOPIC_CODE)
            .topicName(UPDATED_TOPIC_NAME)
            .targetGroup(UPDATED_TARGET_GROUP);
        return appTopicType;
    }

    @BeforeEach
    public void initTest() {
        appTopicType = createEntity(em);
    }

    @Test
    @Transactional
    void createAppTopicType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AppTopicType
        var returnedAppTopicType = om.readValue(
            restAppTopicTypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appTopicType)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AppTopicType.class
        );

        // Validate the AppTopicType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAppTopicTypeUpdatableFieldsEquals(returnedAppTopicType, getPersistedAppTopicType(returnedAppTopicType));
    }

    @Test
    @Transactional
    void createAppTopicTypeWithExistingId() throws Exception {
        // Create the AppTopicType with an existing ID
        appTopicType.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppTopicTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appTopicType)))
            .andExpect(status().isBadRequest());

        // Validate the AppTopicType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppTopicTypes() throws Exception {
        // Initialize the database
        appTopicTypeRepository.saveAndFlush(appTopicType);

        // Get all the appTopicTypeList
        restAppTopicTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appTopicType.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicCode").value(hasItem(DEFAULT_TOPIC_CODE)))
            .andExpect(jsonPath("$.[*].topicName").value(hasItem(DEFAULT_TOPIC_NAME)))
            .andExpect(jsonPath("$.[*].targetGroup").value(hasItem(DEFAULT_TARGET_GROUP)));
    }

    @Test
    @Transactional
    void getAppTopicType() throws Exception {
        // Initialize the database
        appTopicTypeRepository.saveAndFlush(appTopicType);

        // Get the appTopicType
        restAppTopicTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, appTopicType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appTopicType.getId().intValue()))
            .andExpect(jsonPath("$.topicCode").value(DEFAULT_TOPIC_CODE))
            .andExpect(jsonPath("$.topicName").value(DEFAULT_TOPIC_NAME))
            .andExpect(jsonPath("$.targetGroup").value(DEFAULT_TARGET_GROUP));
    }

    @Test
    @Transactional
    void getNonExistingAppTopicType() throws Exception {
        // Get the appTopicType
        restAppTopicTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppTopicType() throws Exception {
        // Initialize the database
        appTopicTypeRepository.saveAndFlush(appTopicType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appTopicType
        AppTopicType updatedAppTopicType = appTopicTypeRepository.findById(appTopicType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAppTopicType are not directly saved in db
        em.detach(updatedAppTopicType);
        updatedAppTopicType.topicCode(UPDATED_TOPIC_CODE).topicName(UPDATED_TOPIC_NAME).targetGroup(UPDATED_TARGET_GROUP);

        restAppTopicTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAppTopicType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAppTopicType))
            )
            .andExpect(status().isOk());

        // Validate the AppTopicType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAppTopicTypeToMatchAllProperties(updatedAppTopicType);
    }

    @Test
    @Transactional
    void putNonExistingAppTopicType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appTopicType.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppTopicTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appTopicType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appTopicType))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppTopicType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppTopicType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appTopicType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppTopicTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appTopicType))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppTopicType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppTopicType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appTopicType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppTopicTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appTopicType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppTopicType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppTopicTypeWithPatch() throws Exception {
        // Initialize the database
        appTopicTypeRepository.saveAndFlush(appTopicType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appTopicType using partial update
        AppTopicType partialUpdatedAppTopicType = new AppTopicType();
        partialUpdatedAppTopicType.setId(appTopicType.getId());

        partialUpdatedAppTopicType.topicCode(UPDATED_TOPIC_CODE).topicName(UPDATED_TOPIC_NAME);

        restAppTopicTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppTopicType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppTopicType))
            )
            .andExpect(status().isOk());

        // Validate the AppTopicType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppTopicTypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAppTopicType, appTopicType),
            getPersistedAppTopicType(appTopicType)
        );
    }

    @Test
    @Transactional
    void fullUpdateAppTopicTypeWithPatch() throws Exception {
        // Initialize the database
        appTopicTypeRepository.saveAndFlush(appTopicType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appTopicType using partial update
        AppTopicType partialUpdatedAppTopicType = new AppTopicType();
        partialUpdatedAppTopicType.setId(appTopicType.getId());

        partialUpdatedAppTopicType.topicCode(UPDATED_TOPIC_CODE).topicName(UPDATED_TOPIC_NAME).targetGroup(UPDATED_TARGET_GROUP);

        restAppTopicTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppTopicType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppTopicType))
            )
            .andExpect(status().isOk());

        // Validate the AppTopicType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppTopicTypeUpdatableFieldsEquals(partialUpdatedAppTopicType, getPersistedAppTopicType(partialUpdatedAppTopicType));
    }

    @Test
    @Transactional
    void patchNonExistingAppTopicType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appTopicType.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppTopicTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appTopicType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appTopicType))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppTopicType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppTopicType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appTopicType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppTopicTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appTopicType))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppTopicType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppTopicType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appTopicType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppTopicTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(appTopicType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppTopicType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppTopicType() throws Exception {
        // Initialize the database
        appTopicTypeRepository.saveAndFlush(appTopicType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the appTopicType
        restAppTopicTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, appTopicType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return appTopicTypeRepository.count();
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

    protected AppTopicType getPersistedAppTopicType(AppTopicType appTopicType) {
        return appTopicTypeRepository.findById(appTopicType.getId()).orElseThrow();
    }

    protected void assertPersistedAppTopicTypeToMatchAllProperties(AppTopicType expectedAppTopicType) {
        assertAppTopicTypeAllPropertiesEquals(expectedAppTopicType, getPersistedAppTopicType(expectedAppTopicType));
    }

    protected void assertPersistedAppTopicTypeToMatchUpdatableProperties(AppTopicType expectedAppTopicType) {
        assertAppTopicTypeAllUpdatablePropertiesEquals(expectedAppTopicType, getPersistedAppTopicType(expectedAppTopicType));
    }
}
