package com.example.balancing.repositories.token;

import com.example.balancing.models.token.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserId(Long id);

    @Transactional
    @Modifying
    void deleteAll();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM refresh_tokens t WHERE t.user_id = ?1",
            nativeQuery = true)
    void deleteByUserId(Long id);

    @Transactional
    @Modifying
    void deleteByToken(String token) throws RefreshTokenException;

    @Transactional
    @Modifying
    @Query(value = "UPDATE refresh_tokens SET token = ?2 WHERE token = ?1",
            nativeQuery = true)
    RefreshToken update(String OldToken, String NewToken);
}
