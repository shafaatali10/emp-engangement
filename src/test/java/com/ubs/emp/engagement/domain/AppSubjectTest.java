package com.ubs.emp.engagement.domain;

import static com.ubs.emp.engagement.domain.AppSubjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ubs.emp.engagement.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppSubjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppSubject.class);
        AppSubject appSubject1 = getAppSubjectSample1();
        AppSubject appSubject2 = new AppSubject();
        assertThat(appSubject1).isNotEqualTo(appSubject2);

        appSubject2.setId(appSubject1.getId());
        assertThat(appSubject1).isEqualTo(appSubject2);

        appSubject2 = getAppSubjectSample2();
        assertThat(appSubject1).isNotEqualTo(appSubject2);
    }
}
