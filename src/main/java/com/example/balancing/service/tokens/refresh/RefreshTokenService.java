package com.example.balancing.service.tokens.refresh;

import com.example.balancing.entity.RefreshToken;
import com.example.balancing.entity.user.User;

import java.time.Duration;

public interface RefreshTokenService {

    RefreshToken findById(Long id);

    RefreshToken findByUser(User user);

    void deleteByToken(String token);

    RefreshToken findByToken(String token);

    RefreshToken save(User user);

    RefreshToken update(String token);

    Duration getRefreshTokenExpiration();

    boolean isValidRefreshToken(String token);
}
