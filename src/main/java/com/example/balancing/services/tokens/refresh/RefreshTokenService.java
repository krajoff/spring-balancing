package com.example.balancing.services.tokens.refresh;

import com.example.balancing.entity.token.RefreshToken;
import com.example.balancing.entity.user.User;

public interface RefreshTokenService {

    RefreshToken findById(Long id);

    RefreshToken findByUser(User user);

    RefreshToken findByToken(String token);

    RefreshToken save(User user);

}
