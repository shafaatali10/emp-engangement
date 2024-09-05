package com.ubs.emp.engagement.domain;

import static com.ubs.emp.engagement.domain.AppUserGroupTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ubs.emp.engagement.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppUserGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUserGroup.class);
        AppUserGroup appUserGroup1 = getAppUserGroupSample1();
        AppUserGroup appUserGroup2 = new AppUserGroup();
        assertThat(appUserGroup1).isNotEqualTo(appUserGroup2);

        appUserGroup2.setId(appUserGroup1.getId());
        assertThat(appUserGroup1).isEqualTo(appUserGroup2);

        appUserGroup2 = getAppUserGroupSample2();
        assertThat(appUserGroup1).isNotEqualTo(appUserGroup2);
    }
}
