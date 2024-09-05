package com.ubs.emp.engagement.domain;

import static com.ubs.emp.engagement.domain.AppLookupTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ubs.emp.engagement.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppLookupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppLookup.class);
        AppLookup appLookup1 = getAppLookupSample1();
        AppLookup appLookup2 = new AppLookup();
        assertThat(appLookup1).isNotEqualTo(appLookup2);

        appLookup2.setId(appLookup1.getId());
        assertThat(appLookup1).isEqualTo(appLookup2);

        appLookup2 = getAppLookupSample2();
        assertThat(appLookup1).isNotEqualTo(appLookup2);
    }
}
