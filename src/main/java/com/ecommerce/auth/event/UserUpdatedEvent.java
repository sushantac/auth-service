package com.ecommerce.auth.event;

import java.time.Instant;

public record UserUpdatedEvent(
        String eventId,
        Long userId,
        String email,
        String fullName,
        Instant occurredAt
) {}
