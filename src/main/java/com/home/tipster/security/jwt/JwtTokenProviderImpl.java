package com.home.tipster.security.jwt;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JwtTokenProviderImpl implements JwtTokenProvider {
    @Override
    public String getNewToken(Map<String, String> params) {
        return null;
    }

    @Override
    public Map<String, String> getParamsFromToken(String token, List<String> keys) {
        return null;
    }
}
