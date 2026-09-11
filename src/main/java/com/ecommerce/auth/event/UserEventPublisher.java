package com.ecommerce.auth.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class UserEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(UserEventPublisher.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UserEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishUserRegistered(Long userId, String email, String fullName) {
        UserRegisteredEvent event = new UserRegisteredEvent(
                UUID.randomUUID().toString(), userId, email, fullName, Instant.now());
        try {
            var future = kafkaTemplate.send("auth.user.registered", String.valueOf(userId), event);
            var result = future.get(10, java.util.concurrent.TimeUnit.SECONDS);
            log.info("Published auth.user.registered for user {}: {}", userId, result.getRecordMetadata());
        } catch (Exception ex) {
            log.error("Failed to publish auth.user.registered for user {}: {}", userId, ex.getMessage(), ex);
            throw new RuntimeException("Failed to publish user registered event", ex);
        }
    }

    public void publishUserUpdated(Long userId, String email, String fullName) {
        UserUpdatedEvent event = new UserUpdatedEvent(
                UUID.randomUUID().toString(), userId, email, fullName, Instant.now());
        try {
            var future = kafkaTemplate.send("auth.user.updated", String.valueOf(userId), event);
            var result = future.get(10, java.util.concurrent.TimeUnit.SECONDS);
            log.info("Published auth.user.updated for user {}", userId);
        } catch (Exception ex) {
            log.error("Failed to publish auth.user.updated for user {}: {}", userId, ex.getMessage(), ex);
            throw new RuntimeException("Failed to publish user updated event", ex);
        }
    }
}
