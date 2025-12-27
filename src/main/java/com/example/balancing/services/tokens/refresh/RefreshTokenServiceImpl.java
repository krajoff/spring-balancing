package com.example.balancing.services.tokens.refresh;

import com.example.balancing.entity.RefreshToken;
import com.example.balancing.entity.user.User;
import com.example.balancing.exception.token.RefreshTokenNotFoundException;
import com.example.balancing.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
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
    @Transactional
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    @Override
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token).orElseThrow(RefreshTokenNotFoundException::new);
    }

    @Override
    public RefreshToken save(User user) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    RefreshToken t = new RefreshToken();
                    t.setUser(user);
                    return t;
                });
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiration(Instant.now().plus(expiration));
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken update(String token) {
        RefreshToken refreshToken = findByToken(token);

        if (refreshToken.getExpiration().isBefore(Instant.now()))
            throw new RefreshTokenNotFoundException("Refresh token expired");

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiration(Instant.now().plus(expiration));
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Duration getRefreshTokenExpiration() {
        return expiration;
    }

    @Override
    public boolean isValidRefreshToken(String token) {
        try {
            RefreshToken refreshToken = findByToken(token);
            return refreshToken.getExpiration().isAfter(Instant.now());
        } catch (RefreshTokenNotFoundException e) {
            return false;
        }
    }

}
