package com.peels.arrival.providers.exceptions;

public class ProviderCreationException extends RuntimeException {
    public ProviderCreationException() {
        super("Failed to lookup provider after creation");
    }

}
