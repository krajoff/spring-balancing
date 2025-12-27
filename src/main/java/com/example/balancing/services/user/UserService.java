package com.example.balancing.services.user;

import com.example.balancing.dto.user.UserDto;
import com.example.balancing.entity.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    User getUserById(Long id);

    void createUser(User user);

    void deleteUser(Long id);

    void deleteUserByUsername(String username);

    UserDetailsService userDetailsService();

    UserDto getCurrentUserDto();

    User getUserByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByUsernameOrEmail(String username, String email);
}
