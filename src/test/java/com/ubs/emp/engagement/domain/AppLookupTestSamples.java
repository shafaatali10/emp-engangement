package com.ubs.emp.engagement.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AppLookupTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static AppLookup getAppLookupSample1() {
        return new AppLookup()
            .id(1L)
            .lookupCode("lookupCode1")
            .displayValue("displayValue1")
            .sequence(1)
            .category("category1")
            .dependentCode("dependentCode1");
    }

    public static AppLookup getAppLookupSample2() {
        return new AppLookup()
            .id(2L)
            .lookupCode("lookupCode2")
            .displayValue("displayValue2")
            .sequence(2)
            .category("category2")
            .dependentCode("dependentCode2");
    }

    public static AppLookup getAppLookupRandomSampleGenerator() {
        return new AppLookup()
            .id(longCount.incrementAndGet())
            .lookupCode(UUID.randomUUID().toString())
            .displayValue(UUID.randomUUID().toString())
            .sequence(intCount.incrementAndGet())
            .category(UUID.randomUUID().toString())
            .dependentCode(UUID.randomUUID().toString());
    }
}
