package com.peels.arrival.visitors.exceptions;

public class VisitorUpdateException extends RuntimeException {
    public VisitorUpdateException() {
        super("Failed to update visitor");
    }
}
