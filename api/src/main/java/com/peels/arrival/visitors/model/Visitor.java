package com.peels.arrival.visitors.model;

import java.time.LocalDateTime;

public record Visitor(
        long id,
        String firstName,
        String lastName,
        Integer number,
        Status status,
        Long provider,
        LocalDateTime arrivalTime,
        LocalDateTime callTime,
        LocalDateTime completeTime) {

    public enum Status {
        WAITING,
        IN_PROGRESS,
        COMPLETED,
        DELETED
    }

}
