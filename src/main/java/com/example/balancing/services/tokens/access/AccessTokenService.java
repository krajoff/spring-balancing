package com.example.balancing.services.tokens.access;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;

public interface AccessTokenService {
    String extractUsername(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    Key getSigningKey();

    long getAccessTokenExpiration();
}
