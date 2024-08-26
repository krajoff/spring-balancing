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

    boolean saveUser(User user);

    User getUserByUsername(String username);

<<<<<<< HEAD
=======
    UserDetailsService userDetailsService();

    User getCurrentUser();
>>>>>>> 0d50e1e (Jwt init)
}
