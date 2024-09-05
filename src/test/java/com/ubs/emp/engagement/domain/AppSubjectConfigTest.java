package com.ubs.emp.engagement.domain;

import static com.ubs.emp.engagement.domain.AppSubjectConfigTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ubs.emp.engagement.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppSubjectConfigTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppSubjectConfig.class);
        AppSubjectConfig appSubjectConfig1 = getAppSubjectConfigSample1();
        AppSubjectConfig appSubjectConfig2 = new AppSubjectConfig();
        assertThat(appSubjectConfig1).isNotEqualTo(appSubjectConfig2);

        appSubjectConfig2.setId(appSubjectConfig1.getId());
        assertThat(appSubjectConfig1).isEqualTo(appSubjectConfig2);

        appSubjectConfig2 = getAppSubjectConfigSample2();
        assertThat(appSubjectConfig1).isNotEqualTo(appSubjectConfig2);
    }
}
