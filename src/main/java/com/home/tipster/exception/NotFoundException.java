package com.home.tipster.exception;

/**
 * @author Andrey Boyarov
 */
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
