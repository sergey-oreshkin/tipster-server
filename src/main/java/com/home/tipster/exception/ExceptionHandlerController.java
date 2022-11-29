package com.home.tipster.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(CONFLICT)
    public ErrorDto handleConflictException(ConflictException ex) {
        log.warn("Error, not unique - {}", ex.getMessage());
        return ErrorDto.builder()
                .code(CONFLICT.value())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorDto handleIsBlankException(MethodArgumentNotValidException ex) {
        String defaultMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        log.warn("Error, {} - value is not valid", defaultMessage);

        return ErrorDto.builder()
                .code(BAD_REQUEST.value())
                .message(defaultMessage)
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorDto handleIsBlankException(NotFoundException ex) {
        log.warn("Error, {} - not found exception", ex.getMessage());
        return ErrorDto.builder()
                .code(NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
    }
}
