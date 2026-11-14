package com.costrella.cechini.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegistrationRateLimiterTest {

    @Test
    void shouldLimitAttemptsPerAddress() {
        RegistrationRateLimiter rateLimiter = new RegistrationRateLimiter();

        for (int attempt = 0; attempt < 10; attempt++) {
            assertThat(rateLimiter.allow("192.0.2.1", attempt)).isTrue();
        }

        assertThat(rateLimiter.allow("192.0.2.1", 10)).isFalse();
        assertThat(rateLimiter.allow("192.0.2.2", 10)).isTrue();
    }

    @Test
    void shouldAllowAttemptsAfterTheWindowExpires() {
        RegistrationRateLimiter rateLimiter = new RegistrationRateLimiter();

        for (int attempt = 0; attempt < 10; attempt++) {
            rateLimiter.allow("192.0.2.1", attempt);
        }

        assertThat(rateLimiter.allow("192.0.2.1", 60 * 60 * 1000L)).isTrue();
    }
}
