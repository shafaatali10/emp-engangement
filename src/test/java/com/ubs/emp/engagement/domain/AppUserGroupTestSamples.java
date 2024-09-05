package com.ubs.emp.engagement.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AppUserGroupTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AppUserGroup getAppUserGroupSample1() {
        return new AppUserGroup().id(1L).groupName("groupName1").email("email1").adminUser("adminUser1");
    }

    public static AppUserGroup getAppUserGroupSample2() {
        return new AppUserGroup().id(2L).groupName("groupName2").email("email2").adminUser("adminUser2");
    }

    public static AppUserGroup getAppUserGroupRandomSampleGenerator() {
        return new AppUserGroup()
            .id(longCount.incrementAndGet())
            .groupName(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .adminUser(UUID.randomUUID().toString());
    }
}
