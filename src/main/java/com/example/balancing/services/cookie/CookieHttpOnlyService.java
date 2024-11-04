package com.example.balancing.services.cookie;

import com.example.balancing.models.token.RefreshToken;
import com.example.balancing.payloads.requests.SignInRequest;
import com.example.balancing.payloads.requests.SignUpRequest;
import com.example.balancing.services.auth.AuthenticationService;
import com.example.balancing.services.tokens.access.AccessTokenService;
import com.example.balancing.services.tokens.refresh.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.util.Optional;

@Service
public class CookieHttpOnlyService {

    private final String ACCESS_COOKIE_NAME = "accessToken";
    private final String ACCESS_COOKIE_PATH = "/";
    private final String REFRESH_COOKIE_NAME = "refreshToken";
    private final String REFRESH_COOKIE_PATH = "/";
    private final RefreshTokenService refreshTokenService;
    private final AccessTokenService accessTokenService;
    private final AuthenticationService authenticationService;

    public CookieHttpOnlyService(RefreshTokenService refreshTokenService, AccessTokenService accessTokenService,
                                 AuthenticationService authenticationService) {
        this.refreshTokenService = refreshTokenService;
        this.accessTokenService = accessTokenService;
        this.authenticationService = authenticationService;
    }

    public void signUp(SignUpRequest request, HttpServletResponse response) {
        var authResponse = authenticationService.signUp(request);
        addAuthCookies(response, authResponse.getAccessToken(),
                authResponse.getRefreshToken());
    }

    public void signIn(SignInRequest request, HttpServletResponse response) {
        var authResponse = authenticationService.signIn(request);
        addAuthCookies(response, authResponse.getAccessToken(),
                authResponse.getRefreshToken());
    }

    public Optional<String> getAccessToken(HttpServletRequest request) {
        return getCookieValueByName(request, ACCESS_COOKIE_NAME);
    }

    public Optional<String> getRefreshToken(HttpServletRequest request) {
        return getCookieValueByName(request, REFRESH_COOKIE_NAME);
    }

    private Optional<String> getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        return Optional.ofNullable(cookie).map(Cookie::getValue);
    }

    public boolean isValidRefreshToken(String refreshToken) {
        return refreshTokenService.isValidExpiration(refreshToken);
    }

    public boolean isValidRefreshToken(HttpServletRequest request) {
        return getRefreshToken(request).map(this::isValidRefreshToken).orElse(false);
    }

    public boolean isValidAccessToken(String accessToken) {
        return accessTokenService.isValidAccessToken(accessToken);
    }

    public boolean isValidAccessToken(HttpServletRequest request) {
        return getAccessToken(request).map(this::isValidAccessToken).orElse(false);
    }

    private void addCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public void addAccessTokenCookie(HttpServletResponse response, String accessToken) {
        addCookie(response, ACCESS_COOKIE_NAME, accessToken, ACCESS_COOKIE_PATH,
                (int) (accessTokenService.getAccessTokenExpiration() / 1000));
    }

    public void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        addCookie(response, REFRESH_COOKIE_NAME, refreshToken, REFRESH_COOKIE_PATH,
                (int) (refreshTokenService.getRefreshTokenExpiration() / 1000));
    }

    private void addAuthCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        addAccessTokenCookie(response, accessToken);
        addRefreshTokenCookie(response, refreshToken);
    }

    public void clearCookies(HttpServletResponse response) {
        addCookie(response, ACCESS_COOKIE_NAME, null, "", 0);
        addCookie(response, REFRESH_COOKIE_NAME, null, "", 0);
    }

    public String getUsernameFromAccessToken(String accessToken) {
        return accessTokenService.extractUsername(accessToken);
    }

    public String getUsernameFromRefreshToken(String refreshToken) {
        return refreshTokenService.findByToken(refreshToken).getUser().getUsername();
    }

}
