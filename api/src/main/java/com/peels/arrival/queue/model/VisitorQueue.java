package com.peels.arrival.queue.model;

import java.util.List;

public record VisitorQueue(List<QueueEntry> entries) {

    public record QueueEntry(
            String firstName,
            String lastName,
            Integer number,
            String provider) {
    }

}
