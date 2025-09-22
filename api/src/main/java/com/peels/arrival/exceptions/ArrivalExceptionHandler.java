package com.peels.arrival.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.peels.arrival.providers.exceptions.ProviderUpdateException;
import com.peels.arrival.visitors.exceptions.InvalidNumberException;
import com.peels.arrival.visitors.exceptions.InvalidProviderException;
import com.peels.arrival.visitors.exceptions.VisitorCreationException;
import com.peels.arrival.visitors.exceptions.VisitorUpdateException;

@ControllerAdvice
public class ArrivalExceptionHandler {

    @ExceptionHandler({
            InvalidNumberException.class,
            InvalidProviderException.class,
            VisitorUpdateException.class,
            ProviderUpdateException.class,
            VisitorCreationException.class })
    public ResponseEntity<ExceptionMessage> handleBadRequest(Exception e) {
        return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private record ExceptionMessage(String message) {
    }

}
