package com.example.balancing.service.user;

import com.example.balancing.dto.UserDto;
import com.example.balancing.entity.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService {

    void createUser(User user);

    void deleteUserByUsername(String username);

    UserDetailsService userDetailsService();

    UserDto getCurrentUserDto();

    User getCurrentUser();

    User getUserByUsername(String username);

    boolean existsByUsernameOrEmail(String username, String email);

    UserDetails loadUserById(UUID uuid);

}
