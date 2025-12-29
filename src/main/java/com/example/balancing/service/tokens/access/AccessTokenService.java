package com.example.balancing.service.tokens.access;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.time.Duration;

public interface AccessTokenService {

    String extractUsername(String token);

    String generateToken(UserDetails userDetails);

    boolean isValidAccessToken(String token, UserDetails userDetails);

    boolean isValidAccessToken(String token);

    Key getSigningKey();

    Duration getAccessTokenExpiration();

}
