package com.example.balancing.services.auth;

import com.example.balancing.exception.auth.AuthException;
import com.example.balancing.exception.auth.WrongRequestException;
import com.example.balancing.exception.user.UserAlreadyExistedException;
import com.example.balancing.payloads.requests.RefreshTokenRequest;
import com.example.balancing.payloads.responses.AuthenticationResponse;
import com.example.balancing.payloads.requests.SignInRequest;
import com.example.balancing.payloads.requests.SignUpRequest;
import com.example.balancing.models.user.Role;
import com.example.balancing.models.user.User;
import com.example.balancing.services.tokens.access.AccessTokenService;
import com.example.balancing.services.tokens.refresh.RefreshTokenService;
import com.example.balancing.services.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя (по умолчанию у всех роль ROLE_USER)
     *
     * @param request {@link SignUpRequest}
     * @return Рефреш- и аксес-токены
     */
    public AuthenticationResponse signUp(SignUpRequest request) {

        try {

            log.info("Signing up user with username: {}", request.getUsername());
            User user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.ROLE_USER)
                    .build();

            // Сохранение информации о пользователе
            log.info("Creating user: {}", user.getUsername());
            userService.createUser(user);

            // Генерация токенов
            var accessToken = accessTokenService.generateToken(user);
            var refreshToken = refreshTokenService.create(user).getToken();

            log.info("User {} successfully signed up, tokens generated.",
                    user.getUsername());

            return new AuthenticationResponse(accessToken, refreshToken);

        } catch (UserAlreadyExistedException ex) {
            log.error("User has already existed: {}", request.getUsername(), ex);
            throw new UserAlreadyExistedException();
        } catch (WrongRequestException ex) {
            log.error("Sign-up request contains invalid data for user: {}", request.getUsername(), ex);
            throw new WrongRequestException();
        } catch (Exception ex) {
            log.error("An unexpected error occurred during sign-in for user: {}", request.getUsername(), ex);
            throw new AuthException("Произошла неизвестная ошибка. " + ex.getMessage());
        }

    }

    /**
     * Аутентификация пользователя
     *
     * @param request {@link SignInRequest}
     * @return Рефреш- и аксес-токены
     */
    public AuthenticationResponse signIn(SignInRequest request) {

        try {
            log.info("Signing in user with username: {}", request.getUsername());

            // Аутентификация пользователя
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    ));

            // Получение информации о пользователе
            User user = userService.getUserByUsername(request.getUsername());
            log.info("Loading user: {}", user.getUsername());

            // Генерация токенов
            var accessToken = accessTokenService.generateToken(user);
            var refreshToken = refreshTokenService.update(user).getToken();

            log.info("User {} successfully signed up, tokens generated.",
                    user.getUsername());

            return new AuthenticationResponse(accessToken, refreshToken);

        } catch (AuthenticationException ex) {
            log.error("Authentication failed for user: {}",
                    request.getUsername(), ex);
            throw new AuthException();
        } catch (WrongRequestException ex) {
            log.error("Sign-in request contains invalid data for user: {}",
                    request.getUsername(), ex);
            throw new WrongRequestException();
        } catch (Exception ex) {
            log.error("An unexpected error occurred during sign-in for user: {}", request.getUsername(), ex);
            throw new AuthException("Произошла неизвестная ошибка. " + ex.getMessage());
        }
    }

    /**
     * Обновление рефреш-токена
     *
     * @param request {@link RefreshTokenRequest}
     * @return Рефреш- и аксес-токены
     */
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        try {
            log.info("Refreshing tokens using refresh token.");

            // Обновление рефреш-токена
            var refreshToken = refreshTokenService.update(request.getRefreshToken());

            // Генерация нового аксес-токена
            var accessToken = accessTokenService.generateToken(refreshToken.getUser());

            log.info("Tokens refreshed successfully for user: {}", refreshToken.getUser().getUsername());

            return new AuthenticationResponse(accessToken, refreshToken.getToken());

        } catch (AuthenticationException ex) {
            log.error("Authentication failed for token: {}", request.getRefreshToken(), ex);
            throw new AuthException();
        } catch (WrongRequestException ex) {
            log.error("Sign-in request contains invalid data for token: {}", request.getRefreshToken(), ex);
            throw new WrongRequestException();
        } catch (Exception ex) {
            log.error("An unexpected error occurred during sign-in for token: {}", request.getRefreshToken(), ex);
            throw new AuthException("Произошла неизвестная ошибка. " + ex.getMessage());
        }
    }


    /**
     * Аутентификация пользователя
     *
     * @param request  данные пользователя
     * @param response ответ содержащий cookies
     * @return токен
     */
    public AuthenticationResponse signIn(SignInRequest request,
                                         HttpServletResponse response) {

        // Аутентификация пользователя
        var authenticationResponse = signIn(request);

        // Создание cookie для аксес-токена
        Cookie accessTokenCookie = new Cookie("access_token",
                authenticationResponse.getAccessToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge((int)
                accessTokenService.getAccessTokenExpiration() / 1000);
        accessTokenCookie.setPath("/");

        // Создание cookie для рефреш-токена
        Cookie refreshTokenCookie = new Cookie("refresh_token",
                authenticationResponse.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge((int)
                refreshTokenService.getRefreshTokenExpiration() / 1000);
        refreshTokenCookie.setPath("/");

        // Добавление cookies в ответ
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        log.info("Access and refresh tokens set " +
                "in HttpOnly cookies for user: {}", request.getUsername());

        return authenticationResponse;
    }

    /**
     * Регистрация пользователя
     *
     * @param request  данные для регистрации пользователя
     * @param response ответ, содержащий cookies
     * @return токены
     */
    public AuthenticationResponse signUp(SignUpRequest request,
                                         HttpServletResponse response) {

        // Регистрация нового пользователя
        var authenticationResponse = signUp(request);

        // Создание cookie для аксес-токена
        Cookie accessTokenCookie = new Cookie("access_token",
                authenticationResponse.getAccessToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge((int)
                accessTokenService.getAccessTokenExpiration() / 1000);
        accessTokenCookie.setPath("/");

        // Создание cookie для рефреш-токена
        Cookie refreshTokenCookie = new Cookie("refresh_token",
                authenticationResponse.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge((int)
                refreshTokenService.getRefreshTokenExpiration() / 1000);
        refreshTokenCookie.setPath("/");

        // Добавление cookies в ответ
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        log.info("User {} successfully signed up, tokens generated " +
                "and set in HttpOnly cookies.", request.getUsername());

        return authenticationResponse;
    }


}
