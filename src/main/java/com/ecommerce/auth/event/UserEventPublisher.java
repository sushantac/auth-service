package com.ecommerce.auth.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class UserEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(UserEventPublisher.class);

    public void publishUserRegistered(Long userId, String email, String fullName) {
        log.info("Would publish auth.user.registered for user {}: email={}, fullName={}", userId, email, fullName);
    }

    public void publishUserUpdated(Long userId, String email, String fullName) {
        log.info("Would publish auth.user.updated for user {}: email={}, fullName={}", userId, email, fullName);
    }
}
