package com.ubs.emp.engagement.domain;

import static com.ubs.emp.engagement.domain.AppSubjectTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ubs.emp.engagement.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppSubjectTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppSubjectType.class);
        AppSubjectType appSubjectType1 = getAppSubjectTypeSample1();
        AppSubjectType appSubjectType2 = new AppSubjectType();
        assertThat(appSubjectType1).isNotEqualTo(appSubjectType2);

        appSubjectType2.setId(appSubjectType1.getId());
        assertThat(appSubjectType1).isEqualTo(appSubjectType2);

        appSubjectType2 = getAppSubjectTypeSample2();
        assertThat(appSubjectType1).isNotEqualTo(appSubjectType2);
    }
}
