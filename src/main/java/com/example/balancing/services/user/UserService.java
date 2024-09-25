package com.example.balancing.services.user;

import com.example.balancing.models.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    User createUser(User user);

    User updateUser(Long id, User user);

    User updateByUsername(String username, User user);

    void deleteUser(Long id);

    void deleteUserByUsername(String username);

    User saveUser(User user);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    UserDetailsService userDetailsService();

    User getCurrentUser();

}
