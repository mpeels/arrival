package com.peels.arrival.visitors.model;

public record CreateVisitorRequest(
        String firstName,
        String lastName,
        Integer number) {

}
