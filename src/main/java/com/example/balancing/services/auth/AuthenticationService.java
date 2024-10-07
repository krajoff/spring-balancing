package com.example.balancing.services.auth;

import com.example.balancing.payloads.responses.JwtAuthenticationResponse;
import com.example.balancing.payloads.requests.SignInRequest;
import com.example.balancing.payloads.requests.SignUpRequest;
import com.example.balancing.models.user.Role;
import com.example.balancing.models.user.User;
import com.example.balancing.services.tokens.access.AccessTokenService;
import com.example.balancing.services.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final AccessTokenService accessTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.createUser(user);

        var jwt = accessTokenService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = accessTokenService.generateToken(user);

        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request  данные пользователя
     * @param response ответ содержащий cookies
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request,
                                            HttpServletResponse response) {

        var jwt = signIn(request);
        Cookie cookie = new Cookie("jwt", jwt.getToken());
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) accessTokenService.getAccessTokenExpiration() / 1000);
        cookie.setPath("/");
        response.addCookie(cookie);

        return jwt;
    }


}
