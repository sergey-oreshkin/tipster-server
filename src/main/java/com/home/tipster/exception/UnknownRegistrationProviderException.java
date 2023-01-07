package com.home.tipster.exception;

import org.springframework.security.core.AuthenticationException;

public class UnknownRegistrationProviderException extends AuthenticationException {
    public UnknownRegistrationProviderException(String msg) {
        super(msg);
    }
}
