package com.example.balancing.services.tokens.refresh;

import com.example.balancing.entity.token.RefreshToken;
import com.example.balancing.entity.user.User;
import com.example.balancing.exception.token.RefreshTokenNotFoundException;
import com.example.balancing.repositories.token.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${application.token.refresh.expiration}")
    private Duration expiration;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public RefreshToken findById(Long id) {
        return refreshTokenRepository.findById(id).orElseThrow(RefreshTokenNotFoundException::new);
    }

    @Override
    public RefreshToken findByUser(User user) {
        return refreshTokenRepository.findByUserId(user.getId()).orElseThrow(RefreshTokenNotFoundException::new);
    }

    @Override
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token).orElseThrow(RefreshTokenNotFoundException::new);
    }

    @Override
    public RefreshToken save(User user) {
        Optional<RefreshToken> token = refreshTokenRepository.findByUserId(user.getId());
        RefreshToken refreshToken;
        if (token.isPresent()) {
            refreshToken = token.get();
        } else {
            refreshToken = new RefreshToken();
            refreshToken.setUser(user);
        }
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiration(new Date(System.currentTimeMillis() + expiration.toMillis()));
        return save(refreshToken);
    }

    private RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

}
