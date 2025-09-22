package com.peels.arrival.visitors.exceptions;

public class InvalidProviderException extends RuntimeException {
    public InvalidProviderException() {
        super("Specified provider does not exist or is not active");
    }

}
