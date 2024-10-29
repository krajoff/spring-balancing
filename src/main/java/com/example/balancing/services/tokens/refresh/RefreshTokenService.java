package com.example.balancing.services.tokens.refresh;


import com.example.balancing.models.token.RefreshToken;
import com.example.balancing.models.user.User;

public interface RefreshTokenService {
    RefreshToken findByToken(String token);

    RefreshToken findByUserId(Long id);

    RefreshToken create(User user);

    RefreshToken generate(User user);

    boolean isValidExpiration(RefreshToken refreshToken);

    boolean isValidExpiration(String refreshToken);

    long getRefreshTokenExpiration();

    RefreshToken update(String token);

    RefreshToken update(User user);

    void deleteAll();

    void deleteByUserId(Long id);
}
