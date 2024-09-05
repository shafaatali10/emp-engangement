package com.ubs.emp.engagement.domain;

import static com.ubs.emp.engagement.domain.AppTopicLookupTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ubs.emp.engagement.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppTopicLookupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppTopicLookup.class);
        AppTopicLookup appTopicLookup1 = getAppTopicLookupSample1();
        AppTopicLookup appTopicLookup2 = new AppTopicLookup();
        assertThat(appTopicLookup1).isNotEqualTo(appTopicLookup2);

        appTopicLookup2.setId(appTopicLookup1.getId());
        assertThat(appTopicLookup1).isEqualTo(appTopicLookup2);

        appTopicLookup2 = getAppTopicLookupSample2();
        assertThat(appTopicLookup1).isNotEqualTo(appTopicLookup2);
    }
}
