package com.ubs.emp.engagement.web.rest;

import static com.ubs.emp.engagement.domain.AppTopicLookupAsserts.*;
import static com.ubs.emp.engagement.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubs.emp.engagement.IntegrationTest;
import com.ubs.emp.engagement.domain.AppTopicLookup;
import com.ubs.emp.engagement.repository.AppTopicLookupRepository;
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
 * Integration tests for the {@link AppTopicLookupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppTopicLookupResourceIT {

    private static final String DEFAULT_TOPIC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TOPIC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_GROUP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-topic-lookups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AppTopicLookupRepository appTopicLookupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppTopicLookupMockMvc;

    private AppTopicLookup appTopicLookup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppTopicLookup createEntity(EntityManager em) {
        AppTopicLookup appTopicLookup = new AppTopicLookup()
            .topicCode(DEFAULT_TOPIC_CODE)
            .topicName(DEFAULT_TOPIC_NAME)
            .targetGroup(DEFAULT_TARGET_GROUP);
        return appTopicLookup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppTopicLookup createUpdatedEntity(EntityManager em) {
        AppTopicLookup appTopicLookup = new AppTopicLookup()
            .topicCode(UPDATED_TOPIC_CODE)
            .topicName(UPDATED_TOPIC_NAME)
            .targetGroup(UPDATED_TARGET_GROUP);
        return appTopicLookup;
    }

    @BeforeEach
    public void initTest() {
        appTopicLookup = createEntity(em);
    }

    @Test
    @Transactional
    void createAppTopicLookup() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AppTopicLookup
        var returnedAppTopicLookup = om.readValue(
            restAppTopicLookupMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appTopicLookup)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AppTopicLookup.class
        );

        // Validate the AppTopicLookup in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAppTopicLookupUpdatableFieldsEquals(returnedAppTopicLookup, getPersistedAppTopicLookup(returnedAppTopicLookup));
    }

    @Test
    @Transactional
    void createAppTopicLookupWithExistingId() throws Exception {
        // Create the AppTopicLookup with an existing ID
        appTopicLookup.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppTopicLookupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appTopicLookup)))
            .andExpect(status().isBadRequest());

        // Validate the AppTopicLookup in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppTopicLookups() throws Exception {
        // Initialize the database
        appTopicLookupRepository.saveAndFlush(appTopicLookup);

        // Get all the appTopicLookupList
        restAppTopicLookupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appTopicLookup.getId().intValue())))
            .andExpect(jsonPath("$.[*].topicCode").value(hasItem(DEFAULT_TOPIC_CODE)))
            .andExpect(jsonPath("$.[*].topicName").value(hasItem(DEFAULT_TOPIC_NAME)))
            .andExpect(jsonPath("$.[*].targetGroup").value(hasItem(DEFAULT_TARGET_GROUP)));
    }

    @Test
    @Transactional
    void getAppTopicLookup() throws Exception {
        // Initialize the database
        appTopicLookupRepository.saveAndFlush(appTopicLookup);

        // Get the appTopicLookup
        restAppTopicLookupMockMvc
            .perform(get(ENTITY_API_URL_ID, appTopicLookup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appTopicLookup.getId().intValue()))
            .andExpect(jsonPath("$.topicCode").value(DEFAULT_TOPIC_CODE))
            .andExpect(jsonPath("$.topicName").value(DEFAULT_TOPIC_NAME))
            .andExpect(jsonPath("$.targetGroup").value(DEFAULT_TARGET_GROUP));
    }

    @Test
    @Transactional
    void getNonExistingAppTopicLookup() throws Exception {
        // Get the appTopicLookup
        restAppTopicLookupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppTopicLookup() throws Exception {
        // Initialize the database
        appTopicLookupRepository.saveAndFlush(appTopicLookup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appTopicLookup
        AppTopicLookup updatedAppTopicLookup = appTopicLookupRepository.findById(appTopicLookup.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAppTopicLookup are not directly saved in db
        em.detach(updatedAppTopicLookup);
        updatedAppTopicLookup.topicCode(UPDATED_TOPIC_CODE).topicName(UPDATED_TOPIC_NAME).targetGroup(UPDATED_TARGET_GROUP);

        restAppTopicLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAppTopicLookup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAppTopicLookup))
            )
            .andExpect(status().isOk());

        // Validate the AppTopicLookup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAppTopicLookupToMatchAllProperties(updatedAppTopicLookup);
    }

    @Test
    @Transactional
    void putNonExistingAppTopicLookup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appTopicLookup.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppTopicLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appTopicLookup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appTopicLookup))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppTopicLookup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppTopicLookup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appTopicLookup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppTopicLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appTopicLookup))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppTopicLookup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppTopicLookup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appTopicLookup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppTopicLookupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appTopicLookup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppTopicLookup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppTopicLookupWithPatch() throws Exception {
        // Initialize the database
        appTopicLookupRepository.saveAndFlush(appTopicLookup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appTopicLookup using partial update
        AppTopicLookup partialUpdatedAppTopicLookup = new AppTopicLookup();
        partialUpdatedAppTopicLookup.setId(appTopicLookup.getId());

        partialUpdatedAppTopicLookup.topicName(UPDATED_TOPIC_NAME).targetGroup(UPDATED_TARGET_GROUP);

        restAppTopicLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppTopicLookup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppTopicLookup))
            )
            .andExpect(status().isOk());

        // Validate the AppTopicLookup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppTopicLookupUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAppTopicLookup, appTopicLookup),
            getPersistedAppTopicLookup(appTopicLookup)
        );
    }

    @Test
    @Transactional
    void fullUpdateAppTopicLookupWithPatch() throws Exception {
        // Initialize the database
        appTopicLookupRepository.saveAndFlush(appTopicLookup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appTopicLookup using partial update
        AppTopicLookup partialUpdatedAppTopicLookup = new AppTopicLookup();
        partialUpdatedAppTopicLookup.setId(appTopicLookup.getId());

        partialUpdatedAppTopicLookup.topicCode(UPDATED_TOPIC_CODE).topicName(UPDATED_TOPIC_NAME).targetGroup(UPDATED_TARGET_GROUP);

        restAppTopicLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppTopicLookup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppTopicLookup))
            )
            .andExpect(status().isOk());

        // Validate the AppTopicLookup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppTopicLookupUpdatableFieldsEquals(partialUpdatedAppTopicLookup, getPersistedAppTopicLookup(partialUpdatedAppTopicLookup));
    }

    @Test
    @Transactional
    void patchNonExistingAppTopicLookup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appTopicLookup.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppTopicLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appTopicLookup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appTopicLookup))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppTopicLookup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppTopicLookup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appTopicLookup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppTopicLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appTopicLookup))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppTopicLookup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppTopicLookup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appTopicLookup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppTopicLookupMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(appTopicLookup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppTopicLookup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppTopicLookup() throws Exception {
        // Initialize the database
        appTopicLookupRepository.saveAndFlush(appTopicLookup);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the appTopicLookup
        restAppTopicLookupMockMvc
            .perform(delete(ENTITY_API_URL_ID, appTopicLookup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return appTopicLookupRepository.count();
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

    protected AppTopicLookup getPersistedAppTopicLookup(AppTopicLookup appTopicLookup) {
        return appTopicLookupRepository.findById(appTopicLookup.getId()).orElseThrow();
    }

    protected void assertPersistedAppTopicLookupToMatchAllProperties(AppTopicLookup expectedAppTopicLookup) {
        assertAppTopicLookupAllPropertiesEquals(expectedAppTopicLookup, getPersistedAppTopicLookup(expectedAppTopicLookup));
    }

    protected void assertPersistedAppTopicLookupToMatchUpdatableProperties(AppTopicLookup expectedAppTopicLookup) {
        assertAppTopicLookupAllUpdatablePropertiesEquals(expectedAppTopicLookup, getPersistedAppTopicLookup(expectedAppTopicLookup));
    }
}
