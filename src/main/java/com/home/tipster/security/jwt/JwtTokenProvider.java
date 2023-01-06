package com.home.tipster.security.jwt;

import java.util.List;
import java.util.Map;

public interface JwtTokenProvider {

    String getNewToken(Map<String, String> params);

    Map<String, String> getParamsFromToken(String token, List<String> keys);
}
