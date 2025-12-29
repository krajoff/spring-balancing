package com.example.balancing.service.user;

import com.example.balancing.dto.UserDto;
import com.example.balancing.entity.user.User;
import com.example.balancing.exception.ElementAlreadyExistsException;
import com.example.balancing.exception.EntityTypeException;
import com.example.balancing.exception.NotFoundElementException;
import com.example.balancing.repository.UserRepository;
import com.example.balancing.transformer.UserMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundElementException(EntityTypeException.USER));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundElementException(EntityTypeException.USER));
    }

    public void createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new ElementAlreadyExistsException(EntityTypeException.USER);
        userRepository.save(user);
    }

    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public UserDetailsService userDetailsService() {
        return this::getUserByUsername;
    }

    public User getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) throw new RuntimeException("User not authenticated");
        var username = auth.getName();
        return getUserByUsername(username);
    }

    public UserDto getCurrentUserDto() {
        var username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userMapper.entityToDto(getUserByUsername(username));
    }

    public UserDetails loadUserByUsername(String username) {
        return getUserByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean existsByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email).isPresent();
    }

    @Override
    public UserDetails loadUserById(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow(() -> new NotFoundElementException(EntityTypeException.USER));
    }

}
