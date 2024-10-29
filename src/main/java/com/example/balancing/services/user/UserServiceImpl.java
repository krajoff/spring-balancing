package com.example.balancing.services.user;

import com.example.balancing.dtos.user.UserDto;
import com.example.balancing.exception.user.UserAlreadyExistedException;
import com.example.balancing.exception.user.UserNotFoundException;
import com.example.balancing.models.user.User;
import com.example.balancing.repositories.user.UserRepository;
import com.example.balancing.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(User user) {
        if (!userRepository.findByUsernameOrEmail
                (user.getUsername(), user.getEmail()).isEmpty()) {
            throw new UserAlreadyExistedException();
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return existingUser;
    }

    public User updateByUsername(String username, User user) {
        User existingUser = getUserByUsername(username);
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return existingUser;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public UserDetails loadUserByUsername(String username) {
        return getUserByUsername(username);
    }

    public UserDetailsService userDetailsService() {
        return this::getUserByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return getUserByUsername(username);
    }

    public UserDto getCurrentUserDto() {
        var username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return userMapper.userToUserDto(getUserByUsername(username));
    }

}
