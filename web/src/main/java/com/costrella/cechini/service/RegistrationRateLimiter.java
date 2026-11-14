package com.costrella.cechini.service;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class RegistrationRateLimiter {

    private static final int MAX_ATTEMPTS = 10;
    private static final int MAX_TRACKED_ADDRESSES = 10000;
    private static final long WINDOW_MILLIS = 60 * 60 * 1000;

    private final Map<String, AttemptWindow> attemptsByAddress = new LinkedHashMap<>();

    public synchronized boolean allow(String address) {
        return allow(address, System.currentTimeMillis());
    }

    synchronized boolean allow(String address, long now) {
        attemptsByAddress.entrySet().removeIf(entry -> now - entry.getValue().startedAt >= WINDOW_MILLIS);

        AttemptWindow window = attemptsByAddress.get(address);
        if (window == null) {
            if (attemptsByAddress.size() >= MAX_TRACKED_ADDRESSES) {
                return false;
            }
            window = new AttemptWindow(now);
            attemptsByAddress.put(address, window);
        }

        if (window.count >= MAX_ATTEMPTS) {
            return false;
        }
        window.count++;
        return true;
    }

    private static class AttemptWindow {
        private final long startedAt;
        private int count;

        private AttemptWindow(long startedAt) {
            this.startedAt = startedAt;
        }
    }
}
