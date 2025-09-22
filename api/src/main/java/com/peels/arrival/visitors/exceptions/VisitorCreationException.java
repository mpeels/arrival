package com.peels.arrival.visitors.exceptions;

public class VisitorCreationException extends RuntimeException {

    public VisitorCreationException() {
        super("Failed to lookup visitor after creation.");
    }

}
