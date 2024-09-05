package com.ubs.emp.engagement.domain;

import static com.ubs.emp.engagement.domain.AppTopicTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ubs.emp.engagement.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppTopicTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppTopicType.class);
        AppTopicType appTopicType1 = getAppTopicTypeSample1();
        AppTopicType appTopicType2 = new AppTopicType();
        assertThat(appTopicType1).isNotEqualTo(appTopicType2);

        appTopicType2.setId(appTopicType1.getId());
        assertThat(appTopicType1).isEqualTo(appTopicType2);

        appTopicType2 = getAppTopicTypeSample2();
        assertThat(appTopicType1).isNotEqualTo(appTopicType2);
    }
}
