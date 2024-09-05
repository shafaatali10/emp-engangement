package com.ubs.emp.engagement.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AppSubjectConfigTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static AppSubjectConfig getAppSubjectConfigSample1() {
        return new AppSubjectConfig().id(1L).subjectCode("subjectCode1").version(1).payload("payload1");
    }

    public static AppSubjectConfig getAppSubjectConfigSample2() {
        return new AppSubjectConfig().id(2L).subjectCode("subjectCode2").version(2).payload("payload2");
    }

    public static AppSubjectConfig getAppSubjectConfigRandomSampleGenerator() {
        return new AppSubjectConfig()
            .id(longCount.incrementAndGet())
            .subjectCode(UUID.randomUUID().toString())
            .version(intCount.incrementAndGet())
            .payload(UUID.randomUUID().toString());
    }
}
