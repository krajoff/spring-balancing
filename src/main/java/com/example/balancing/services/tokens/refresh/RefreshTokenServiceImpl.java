package com.example.balancing.services.tokens.refresh;

import com.example.balancing.exception.token.RefreshTokenNotFoundException;
import com.example.balancing.models.token.RefreshToken;
import com.example.balancing.models.user.User;
import com.example.balancing.repositories.token.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Getter
    @Value("${REFRESH_TOKEN_EXPIRATION}")
    private long RefreshTokenExpiration;

    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(RefreshTokenNotFoundException::new);
    }

    public RefreshToken findByUserId(Long id) {
        return refreshTokenRepository.findByUserId(id)
                .orElseThrow(RefreshTokenNotFoundException::new);
    }

    public RefreshToken create(User user) {
        return save(generate(user));
    }

    public RefreshToken generate(User user) {
        RefreshToken token = new RefreshToken();
        token.setUser(user);
        return setExpiryAndToken(token);
    }

    private RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    public boolean isValidExpiration(RefreshToken refreshToken) {
        return refreshToken.getExpiration().after(new Date());
    }

    public boolean isValidExpiration(String refreshToken) {
        return findByToken(refreshToken).getExpiration().after(new Date());
    }

    @Transactional
    public RefreshToken update(String token) {
        RefreshToken existingToken = findByToken(token);
        setExpiryAndToken(existingToken);
        return save(existingToken);
    }

    @Transactional
    public RefreshToken update(User user) {
        Optional<RefreshToken> existingToken =
                refreshTokenRepository.findByUserId(user.getId());
        return existingToken.map(refreshToken ->
                save(setExpiryAndToken(refreshToken))).orElseGet(() -> save(generate(user)));
    }

    private RefreshToken setExpiryAndToken(RefreshToken refreshToken) {
        refreshToken.setExpiration(new Date(System.currentTimeMillis()
                + RefreshTokenExpiration));
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshToken;
    }

    @Transactional
    public void deleteAll() {
        refreshTokenRepository.deleteAll();
    }

    @Transactional
    public void deleteByUserId(Long id) {
        refreshTokenRepository.deleteByUserId(id);
    }
}
