package com.ubs.emp.engagement.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AppTopicLookupTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AppTopicLookup getAppTopicLookupSample1() {
        return new AppTopicLookup().id(1L).topicCode("topicCode1").topicName("topicName1").targetGroup("targetGroup1");
    }

    public static AppTopicLookup getAppTopicLookupSample2() {
        return new AppTopicLookup().id(2L).topicCode("topicCode2").topicName("topicName2").targetGroup("targetGroup2");
    }

    public static AppTopicLookup getAppTopicLookupRandomSampleGenerator() {
        return new AppTopicLookup()
            .id(longCount.incrementAndGet())
            .topicCode(UUID.randomUUID().toString())
            .topicName(UUID.randomUUID().toString())
            .targetGroup(UUID.randomUUID().toString());
    }
}
