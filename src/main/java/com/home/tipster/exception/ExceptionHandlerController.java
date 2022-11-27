package com.home.tipster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(CONFLICT)
    public ErrorDto handleConflictException(ConflictException ex) {
        return ErrorDto.builder()
                .code(CONFLICT.value())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleIsBlankException(MethodArgumentNotValidException ex) {
        return ErrorDto.builder()
                .code(BAD_REQUEST.value())
                .message(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage())
                .build();
    }
}
