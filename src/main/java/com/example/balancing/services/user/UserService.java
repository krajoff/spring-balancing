package com.example.balancing.services.user;

import com.example.balancing.models.user.User;


import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    User createUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    boolean saveUser(User user);

    User getUserByUsername(String username);
}
