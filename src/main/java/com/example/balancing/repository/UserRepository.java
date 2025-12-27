package com.example.balancing.repository;

import com.example.balancing.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query(value = "select * from users u where u.username = :username or u.email = :email", nativeQuery = true)
    Optional<User> findByUsernameOrEmail(@Param("username") String username,
                                         @Param("email") String email);

    void deleteByUsername(String username);

}
