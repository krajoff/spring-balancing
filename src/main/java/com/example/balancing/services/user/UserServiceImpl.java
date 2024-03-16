package com.example.balancing.services.user;

import com.example.balancing.models.user.Role;
import com.example.balancing.models.user.User;
import com.example.balancing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

//    public User getUserByUsername(String username){
//        return userRepository.findByUsername(username);
//    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return existingUser;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
//        User user = userRepository.findByUsername(username);
//        List<GrantedAuthority> authorities = new java.util.ArrayList<>(Collections.emptyList());
//        authorities.add((GrantedAuthority) () -> user.getRole().name());
//        return new User(user.getUsername(), user.getPassword(), authorities);
        return userRepository.findByUsername(username);
    }

    public boolean saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            System.out.println("User already existed");
            return false;
        } else {
            user.setRoles("USER");
            user.setRole(Role.USER);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }
    }
}
