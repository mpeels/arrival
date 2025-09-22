package com.peels.arrival.visitors.model;

public record UpdateVisitorRequest(
        String firstName,
        String lastName,
        Integer number,
        Long provider,
        Visitor.Status status) {

}
