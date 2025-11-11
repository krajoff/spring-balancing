package com.example.balancing.services.user;

import com.example.balancing.dto.user.UserDto;
import com.example.balancing.entity.user.User;
import com.example.balancing.exception.user.UserAlreadyExistedException;
import com.example.balancing.exception.user.UserNotFoundException;
import com.example.balancing.repositories.user.UserRepository;
import com.example.balancing.utils.UserMapper;
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
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public void createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) throw new UserAlreadyExistedException();
        userRepository.save(user);
    }

    public User updateUser(User user) {
        User existingUser = getUserById(user.getId());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }

    public User updateByEmail(String email, User user) {
        User existingUser = getUserByEmail(email);
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public UserDetails loadUserByUsername(String email) {
        return getUserByEmail(email);
    }

    public UserDetailsService userDetailsService() {
        return this::getUserByEmail;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return getUserByUsername(username);
    }

    public UserDto getCurrentUserDto() {
        var username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userMapper.entityToDto(getUserByUsername(username));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

}
