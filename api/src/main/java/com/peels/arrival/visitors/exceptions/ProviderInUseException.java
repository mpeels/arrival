package com.peels.arrival.visitors.exceptions;

public class ProviderInUseException extends RuntimeException {
    private static final String ERROR_MSG = """
            Specified provider is already assigned to a visitor that is 'IN_PROGRESS'.
            Please choose a different provider or mark the current visitor as 'COMPLETED'
            """;

    public ProviderInUseException() {
        super(ERROR_MSG);
    }
}
