package com.ubs.emp.engagement.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AppSubjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AppSubject getAppSubjectSample1() {
        return new AppSubject().id(1L).subjectCode("subjectCode1").topicCode("topicCode1").status("status1").detailsJson("detailsJson1");
    }

    public static AppSubject getAppSubjectSample2() {
        return new AppSubject().id(2L).subjectCode("subjectCode2").topicCode("topicCode2").status("status2").detailsJson("detailsJson2");
    }

    public static AppSubject getAppSubjectRandomSampleGenerator() {
        return new AppSubject()
            .id(longCount.incrementAndGet())
            .subjectCode(UUID.randomUUID().toString())
            .topicCode(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .detailsJson(UUID.randomUUID().toString());
    }
}
