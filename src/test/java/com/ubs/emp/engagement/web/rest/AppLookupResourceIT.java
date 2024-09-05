package com.ubs.emp.engagement.web.rest;

import static com.ubs.emp.engagement.domain.AppLookupAsserts.*;
import static com.ubs.emp.engagement.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubs.emp.engagement.IntegrationTest;
import com.ubs.emp.engagement.domain.AppLookup;
import com.ubs.emp.engagement.repository.AppLookupRepository;
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
 * Integration tests for the {@link AppLookupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppLookupResourceIT {

    private static final String DEFAULT_LOOKUP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LOOKUP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_VALUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_DEPENDENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DEPENDENT_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-lookups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AppLookupRepository appLookupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppLookupMockMvc;

    private AppLookup appLookup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppLookup createEntity(EntityManager em) {
        AppLookup appLookup = new AppLookup()
            .lookupCode(DEFAULT_LOOKUP_CODE)
            .displayValue(DEFAULT_DISPLAY_VALUE)
            .sequence(DEFAULT_SEQUENCE)
            .category(DEFAULT_CATEGORY)
            .dependentCode(DEFAULT_DEPENDENT_CODE);
        return appLookup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppLookup createUpdatedEntity(EntityManager em) {
        AppLookup appLookup = new AppLookup()
            .lookupCode(UPDATED_LOOKUP_CODE)
            .displayValue(UPDATED_DISPLAY_VALUE)
            .sequence(UPDATED_SEQUENCE)
            .category(UPDATED_CATEGORY)
            .dependentCode(UPDATED_DEPENDENT_CODE);
        return appLookup;
    }

    @BeforeEach
    public void initTest() {
        appLookup = createEntity(em);
    }

    @Test
    @Transactional
    void createAppLookup() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AppLookup
        var returnedAppLookup = om.readValue(
            restAppLookupMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appLookup)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AppLookup.class
        );

        // Validate the AppLookup in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAppLookupUpdatableFieldsEquals(returnedAppLookup, getPersistedAppLookup(returnedAppLookup));
    }

    @Test
    @Transactional
    void createAppLookupWithExistingId() throws Exception {
        // Create the AppLookup with an existing ID
        appLookup.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppLookupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appLookup)))
            .andExpect(status().isBadRequest());

        // Validate the AppLookup in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppLookups() throws Exception {
        // Initialize the database
        appLookupRepository.saveAndFlush(appLookup);

        // Get all the appLookupList
        restAppLookupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appLookup.getId().intValue())))
            .andExpect(jsonPath("$.[*].lookupCode").value(hasItem(DEFAULT_LOOKUP_CODE)))
            .andExpect(jsonPath("$.[*].displayValue").value(hasItem(DEFAULT_DISPLAY_VALUE)))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].dependentCode").value(hasItem(DEFAULT_DEPENDENT_CODE)));
    }

    @Test
    @Transactional
    void getAppLookup() throws Exception {
        // Initialize the database
        appLookupRepository.saveAndFlush(appLookup);

        // Get the appLookup
        restAppLookupMockMvc
            .perform(get(ENTITY_API_URL_ID, appLookup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appLookup.getId().intValue()))
            .andExpect(jsonPath("$.lookupCode").value(DEFAULT_LOOKUP_CODE))
            .andExpect(jsonPath("$.displayValue").value(DEFAULT_DISPLAY_VALUE))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.dependentCode").value(DEFAULT_DEPENDENT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingAppLookup() throws Exception {
        // Get the appLookup
        restAppLookupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppLookup() throws Exception {
        // Initialize the database
        appLookupRepository.saveAndFlush(appLookup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appLookup
        AppLookup updatedAppLookup = appLookupRepository.findById(appLookup.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAppLookup are not directly saved in db
        em.detach(updatedAppLookup);
        updatedAppLookup
            .lookupCode(UPDATED_LOOKUP_CODE)
            .displayValue(UPDATED_DISPLAY_VALUE)
            .sequence(UPDATED_SEQUENCE)
            .category(UPDATED_CATEGORY)
            .dependentCode(UPDATED_DEPENDENT_CODE);

        restAppLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAppLookup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAppLookup))
            )
            .andExpect(status().isOk());

        // Validate the AppLookup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAppLookupToMatchAllProperties(updatedAppLookup);
    }

    @Test
    @Transactional
    void putNonExistingAppLookup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appLookup.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appLookup.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appLookup))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLookup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppLookup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appLookup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appLookup))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLookup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppLookup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appLookup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLookupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appLookup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppLookup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppLookupWithPatch() throws Exception {
        // Initialize the database
        appLookupRepository.saveAndFlush(appLookup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appLookup using partial update
        AppLookup partialUpdatedAppLookup = new AppLookup();
        partialUpdatedAppLookup.setId(appLookup.getId());

        partialUpdatedAppLookup.category(UPDATED_CATEGORY);

        restAppLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppLookup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppLookup))
            )
            .andExpect(status().isOk());

        // Validate the AppLookup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppLookupUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAppLookup, appLookup),
            getPersistedAppLookup(appLookup)
        );
    }

    @Test
    @Transactional
    void fullUpdateAppLookupWithPatch() throws Exception {
        // Initialize the database
        appLookupRepository.saveAndFlush(appLookup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appLookup using partial update
        AppLookup partialUpdatedAppLookup = new AppLookup();
        partialUpdatedAppLookup.setId(appLookup.getId());

        partialUpdatedAppLookup
            .lookupCode(UPDATED_LOOKUP_CODE)
            .displayValue(UPDATED_DISPLAY_VALUE)
            .sequence(UPDATED_SEQUENCE)
            .category(UPDATED_CATEGORY)
            .dependentCode(UPDATED_DEPENDENT_CODE);

        restAppLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppLookup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppLookup))
            )
            .andExpect(status().isOk());

        // Validate the AppLookup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppLookupUpdatableFieldsEquals(partialUpdatedAppLookup, getPersistedAppLookup(partialUpdatedAppLookup));
    }

    @Test
    @Transactional
    void patchNonExistingAppLookup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appLookup.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appLookup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appLookup))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLookup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppLookup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appLookup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appLookup))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppLookup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppLookup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appLookup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppLookupMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(appLookup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppLookup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppLookup() throws Exception {
        // Initialize the database
        appLookupRepository.saveAndFlush(appLookup);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the appLookup
        restAppLookupMockMvc
            .perform(delete(ENTITY_API_URL_ID, appLookup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return appLookupRepository.count();
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

    protected AppLookup getPersistedAppLookup(AppLookup appLookup) {
        return appLookupRepository.findById(appLookup.getId()).orElseThrow();
    }

    protected void assertPersistedAppLookupToMatchAllProperties(AppLookup expectedAppLookup) {
        assertAppLookupAllPropertiesEquals(expectedAppLookup, getPersistedAppLookup(expectedAppLookup));
    }

    protected void assertPersistedAppLookupToMatchUpdatableProperties(AppLookup expectedAppLookup) {
        assertAppLookupAllUpdatablePropertiesEquals(expectedAppLookup, getPersistedAppLookup(expectedAppLookup));
    }
}
