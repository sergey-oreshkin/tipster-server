package com.home.tipster.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Service
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${app.authorize.failure-url}")
    private String baseRedirectUrl;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.warn("Authentication failure {}. Remote Address {}", exception.getMessage(), request.getRemoteAddr());
        response.sendRedirect(String.format("%s?error=%s", baseRedirectUrl, exception.getMessage()));
    }
}