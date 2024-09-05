package com.ubs.emp.engagement.web.rest;

import static com.ubs.emp.engagement.domain.AppUserGroupAsserts.*;
import static com.ubs.emp.engagement.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubs.emp.engagement.IntegrationTest;
import com.ubs.emp.engagement.domain.AppUserGroup;
import com.ubs.emp.engagement.repository.AppUserGroupRepository;
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
 * Integration tests for the {@link AppUserGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppUserGroupResourceIT {

    private static final String DEFAULT_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADMIN_USER = "AAAAAAAAAA";
    private static final String UPDATED_ADMIN_USER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-user-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AppUserGroupRepository appUserGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppUserGroupMockMvc;

    private AppUserGroup appUserGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppUserGroup createEntity(EntityManager em) {
        AppUserGroup appUserGroup = new AppUserGroup().groupName(DEFAULT_GROUP_NAME).email(DEFAULT_EMAIL).adminUser(DEFAULT_ADMIN_USER);
        return appUserGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppUserGroup createUpdatedEntity(EntityManager em) {
        AppUserGroup appUserGroup = new AppUserGroup().groupName(UPDATED_GROUP_NAME).email(UPDATED_EMAIL).adminUser(UPDATED_ADMIN_USER);
        return appUserGroup;
    }

    @BeforeEach
    public void initTest() {
        appUserGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createAppUserGroup() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AppUserGroup
        var returnedAppUserGroup = om.readValue(
            restAppUserGroupMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appUserGroup)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AppUserGroup.class
        );

        // Validate the AppUserGroup in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAppUserGroupUpdatableFieldsEquals(returnedAppUserGroup, getPersistedAppUserGroup(returnedAppUserGroup));
    }

    @Test
    @Transactional
    void createAppUserGroupWithExistingId() throws Exception {
        // Create the AppUserGroup with an existing ID
        appUserGroup.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppUserGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appUserGroup)))
            .andExpect(status().isBadRequest());

        // Validate the AppUserGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppUserGroups() throws Exception {
        // Initialize the database
        appUserGroupRepository.saveAndFlush(appUserGroup);

        // Get all the appUserGroupList
        restAppUserGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appUserGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].adminUser").value(hasItem(DEFAULT_ADMIN_USER)));
    }

    @Test
    @Transactional
    void getAppUserGroup() throws Exception {
        // Initialize the database
        appUserGroupRepository.saveAndFlush(appUserGroup);

        // Get the appUserGroup
        restAppUserGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, appUserGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appUserGroup.getId().intValue()))
            .andExpect(jsonPath("$.groupName").value(DEFAULT_GROUP_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.adminUser").value(DEFAULT_ADMIN_USER));
    }

    @Test
    @Transactional
    void getNonExistingAppUserGroup() throws Exception {
        // Get the appUserGroup
        restAppUserGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppUserGroup() throws Exception {
        // Initialize the database
        appUserGroupRepository.saveAndFlush(appUserGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appUserGroup
        AppUserGroup updatedAppUserGroup = appUserGroupRepository.findById(appUserGroup.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAppUserGroup are not directly saved in db
        em.detach(updatedAppUserGroup);
        updatedAppUserGroup.groupName(UPDATED_GROUP_NAME).email(UPDATED_EMAIL).adminUser(UPDATED_ADMIN_USER);

        restAppUserGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAppUserGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAppUserGroup))
            )
            .andExpect(status().isOk());

        // Validate the AppUserGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAppUserGroupToMatchAllProperties(updatedAppUserGroup);
    }

    @Test
    @Transactional
    void putNonExistingAppUserGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appUserGroup.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppUserGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appUserGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appUserGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppUserGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppUserGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appUserGroup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppUserGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(appUserGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppUserGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppUserGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appUserGroup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppUserGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(appUserGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppUserGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppUserGroupWithPatch() throws Exception {
        // Initialize the database
        appUserGroupRepository.saveAndFlush(appUserGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appUserGroup using partial update
        AppUserGroup partialUpdatedAppUserGroup = new AppUserGroup();
        partialUpdatedAppUserGroup.setId(appUserGroup.getId());

        partialUpdatedAppUserGroup.groupName(UPDATED_GROUP_NAME).email(UPDATED_EMAIL);

        restAppUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppUserGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppUserGroup))
            )
            .andExpect(status().isOk());

        // Validate the AppUserGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppUserGroupUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAppUserGroup, appUserGroup),
            getPersistedAppUserGroup(appUserGroup)
        );
    }

    @Test
    @Transactional
    void fullUpdateAppUserGroupWithPatch() throws Exception {
        // Initialize the database
        appUserGroupRepository.saveAndFlush(appUserGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the appUserGroup using partial update
        AppUserGroup partialUpdatedAppUserGroup = new AppUserGroup();
        partialUpdatedAppUserGroup.setId(appUserGroup.getId());

        partialUpdatedAppUserGroup.groupName(UPDATED_GROUP_NAME).email(UPDATED_EMAIL).adminUser(UPDATED_ADMIN_USER);

        restAppUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppUserGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAppUserGroup))
            )
            .andExpect(status().isOk());

        // Validate the AppUserGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAppUserGroupUpdatableFieldsEquals(partialUpdatedAppUserGroup, getPersistedAppUserGroup(partialUpdatedAppUserGroup));
    }

    @Test
    @Transactional
    void patchNonExistingAppUserGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appUserGroup.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appUserGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appUserGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppUserGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppUserGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appUserGroup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(appUserGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppUserGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppUserGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        appUserGroup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppUserGroupMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(appUserGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppUserGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppUserGroup() throws Exception {
        // Initialize the database
        appUserGroupRepository.saveAndFlush(appUserGroup);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the appUserGroup
        restAppUserGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, appUserGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return appUserGroupRepository.count();
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

    protected AppUserGroup getPersistedAppUserGroup(AppUserGroup appUserGroup) {
        return appUserGroupRepository.findById(appUserGroup.getId()).orElseThrow();
    }

    protected void assertPersistedAppUserGroupToMatchAllProperties(AppUserGroup expectedAppUserGroup) {
        assertAppUserGroupAllPropertiesEquals(expectedAppUserGroup, getPersistedAppUserGroup(expectedAppUserGroup));
    }

    protected void assertPersistedAppUserGroupToMatchUpdatableProperties(AppUserGroup expectedAppUserGroup) {
        assertAppUserGroupAllUpdatablePropertiesEquals(expectedAppUserGroup, getPersistedAppUserGroup(expectedAppUserGroup));
    }
}
