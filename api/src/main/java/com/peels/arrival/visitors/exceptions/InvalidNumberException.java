package com.peels.arrival.visitors.exceptions;

public class InvalidNumberException extends RuntimeException {

    public InvalidNumberException() {
        super("Provided number is already assigned to a waiting visitor");
    }

}
