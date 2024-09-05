package com.ubs.emp.engagement.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AppSubjectTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AppSubjectType getAppSubjectTypeSample1() {
        return new AppSubjectType().id(1L).subjectCode("subjectCode1").description("description1");
    }

    public static AppSubjectType getAppSubjectTypeSample2() {
        return new AppSubjectType().id(2L).subjectCode("subjectCode2").description("description2");
    }

    public static AppSubjectType getAppSubjectTypeRandomSampleGenerator() {
        return new AppSubjectType()
            .id(longCount.incrementAndGet())
            .subjectCode(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
