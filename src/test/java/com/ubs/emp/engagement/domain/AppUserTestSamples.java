package com.ubs.emp.engagement.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AppUserTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AppUser getAppUserSample1() {
        return new AppUser()
            .id(1L)
            .firstName("firstName1")
            .lastName("lastName1")
            .login("login1")
            .password("password1")
            .role("role1")
            .email("email1");
    }

    public static AppUser getAppUserSample2() {
        return new AppUser()
            .id(2L)
            .firstName("firstName2")
            .lastName("lastName2")
            .login("login2")
            .password("password2")
            .role("role2")
            .email("email2");
    }

    public static AppUser getAppUserRandomSampleGenerator() {
        return new AppUser()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .login(UUID.randomUUID().toString())
            .password(UUID.randomUUID().toString())
            .role(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
