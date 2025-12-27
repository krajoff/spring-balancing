package com.example.balancing.services.auth;

import com.example.balancing.entity.user.Role;
import com.example.balancing.entity.user.User;
import com.example.balancing.exception.auth.AuthException;
import com.example.balancing.exception.auth.WrongRequestException;
import com.example.balancing.exception.user.UserAlreadyExistedException;
import com.example.balancing.payloads.requests.RefreshTokenRequest;
import com.example.balancing.payloads.requests.SignInRequest;
import com.example.balancing.payloads.requests.SignUpRequest;
import com.example.balancing.payloads.responses.AuthenticationResponse;
import com.example.balancing.services.tokens.access.AccessTokenService;
import com.example.balancing.services.tokens.refresh.RefreshTokenService;
import com.example.balancing.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse signUp(SignUpRequest request) {

        if (Objects.isNull(request) || request.getUsername().isBlank() || request.getPassword().isBlank())
            throw new WrongRequestException("Invalid sign-up data");

        if (userService.existsByUsernameOrEmail(request.getUsername(), request.getEmail()))
            throw new UserAlreadyExistedException("Such username or email is already taken");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.ROLE_USER);

        userService.createUser(user);
        log.info("User {} created successfully", user.getUsername());

        return generateTokens(user);
    }

    public AuthenticationResponse signIn(SignInRequest request) {

        try {
            log.info("Signing in user with username: {}", request.getUsername());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
        } catch (AuthenticationException ex) {
            log.error("Authentication failed for user: {}", request.getUsername(), ex);
            throw new AuthException();
        }

        User user = userService.getUserByUsername(request.getUsername());
        log.info("Loading user: {}", user.getUsername());
        AuthenticationResponse tokens = generateTokens(user);

        log.info("User {} signed in successfully", user.getUsername());
        return tokens;

    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        if (Objects.isNull(request) || request.getRefreshToken().isBlank())
            throw new WrongRequestException("Refresh token is missing");

        var refreshToken = refreshTokenService.update(request.getRefreshToken());
        var accessToken = accessTokenService.generateToken(refreshToken.getUser());

        AuthenticationResponse tokens = new AuthenticationResponse(accessToken, refreshToken.getToken());

        log.info("Tokens refreshed for user {}", refreshToken.getUser().getUsername());
        return tokens;
    }

    private AuthenticationResponse generateTokens(User user) {
        String accessToken = accessTokenService.generateToken(user);
        String refreshToken = refreshTokenService.save(user).getToken();
        return new AuthenticationResponse(accessToken, refreshToken);
    }

}
