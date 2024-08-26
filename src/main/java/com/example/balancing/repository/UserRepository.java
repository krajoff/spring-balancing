package com.example.balancing.repository;

import com.example.balancing.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username) throws UsernameNotFoundException;

    User findByEmail(String email);

    @Query(value = "select * from users u where u.username = ?1 or u.email = ?2",
            nativeQuery = true)
    List<User> findByUsernameOrEmail(String username, String email)
            throws UsernameNotFoundException;

    @Query(value = "delete from records r where r.unit_id in " +
            "(select id from units un where un.user_id " +
            "in (select id from users us where us.username = ?1)); " +
            "delete from units un where un.user_id in " +
            "(select id from users us where us.username = ?1); " +
            "delete from users us where us.username = ?1;",
            nativeQuery = true)
    void deleteByUsername(String username)
            throws UsernameNotFoundException;

    @Query(value = "update from users u set u.password = ?2.password " +
            "where u.username = ?1", nativeQuery = true)
    User updateByUsername(String username, User user)
            throws UsernameNotFoundException;

    boolean existsByUsername(String username);
}
