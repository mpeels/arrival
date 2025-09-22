package com.peels.arrival.providers.exceptions;

public class ProviderUpdateException extends RuntimeException {
    public ProviderUpdateException() {
        super("Failed to update provider.");
    }

}
