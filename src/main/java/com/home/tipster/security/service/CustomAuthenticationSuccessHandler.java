package com.home.tipster.security.service;

import com.home.tipster.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Value("${app.authorize.success-url}")
    private String baseRedirectUrl;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, String> params = new HashMap<>();
        params.put("id", authentication.getName());
        String token = jwtTokenProvider.getNewToken(params);
        String redirectUrl = String.format("%s?token=%s", baseRedirectUrl, token);
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
