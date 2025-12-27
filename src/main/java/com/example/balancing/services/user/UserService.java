package com.example.balancing.services.user;

import com.example.balancing.dto.UserDto;
import com.example.balancing.entity.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    void createUser(User user);

    void deleteUserByUsername(String username);

    UserDetailsService userDetailsService();

    UserDto getCurrentUserDto();

    User getUserByUsername(String username);

    boolean existsByUsernameOrEmail(String username, String email);
}
