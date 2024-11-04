package com.example.balancing.repositories.user;

import com.example.balancing.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username) throws UsernameNotFoundException;

    Optional<User> findByEmail(String email) throws UsernameNotFoundException;

    Optional<User> findByRefreshToken_Token(String refreshToken) throws UsernameNotFoundException;

    @Query(value = "select * from users u where u.username = ?1 or u.email = ?2", nativeQuery = true)
    List<User> findByUsernameOrEmail(String username, String email) throws UsernameNotFoundException;

    @Query(value = "update from users u set u.password = ?2.password where u.username = ?1", nativeQuery = true)
    Optional<User> updateByUsername(String username, User user) throws UsernameNotFoundException;

    void deleteByUsername(String username);

    boolean existsByUsername(String username);

}
