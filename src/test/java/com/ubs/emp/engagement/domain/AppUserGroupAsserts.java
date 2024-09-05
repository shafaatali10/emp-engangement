package com.ubs.emp.engagement.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AppUserGroupAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppUserGroupAllPropertiesEquals(AppUserGroup expected, AppUserGroup actual) {
        assertAppUserGroupAutoGeneratedPropertiesEquals(expected, actual);
        assertAppUserGroupAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppUserGroupAllUpdatablePropertiesEquals(AppUserGroup expected, AppUserGroup actual) {
        assertAppUserGroupUpdatableFieldsEquals(expected, actual);
        assertAppUserGroupUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppUserGroupAutoGeneratedPropertiesEquals(AppUserGroup expected, AppUserGroup actual) {
        assertThat(expected)
            .as("Verify AppUserGroup auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppUserGroupUpdatableFieldsEquals(AppUserGroup expected, AppUserGroup actual) {
        assertThat(expected)
            .as("Verify AppUserGroup relevant properties")
            .satisfies(e -> assertThat(e.getGroupName()).as("check groupName").isEqualTo(actual.getGroupName()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()))
            .satisfies(e -> assertThat(e.getAdminUser()).as("check adminUser").isEqualTo(actual.getAdminUser()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAppUserGroupUpdatableRelationshipsEquals(AppUserGroup expected, AppUserGroup actual) {}
}
