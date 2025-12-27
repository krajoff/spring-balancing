package com.example.balancing.services.cookie;

import com.example.balancing.services.tokens.access.AccessTokenService;
import com.example.balancing.services.tokens.refresh.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.time.Duration;
import java.util.Optional;

@Service
public class CookieHttpOnlyService {

    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    private static final String ACCESS_COOKIE_NAME = "access_token";
    private static final String REFRESH_COOKIE_NAME = "refresh_token";
    private static final String COOKIE_PATH = "/";

    public CookieHttpOnlyService(AccessTokenService accessTokenService,
                                 RefreshTokenService refreshTokenService) {
        this.accessTokenService = accessTokenService;
        this.refreshTokenService = refreshTokenService;
    }

    public void setAccessToken(HttpServletResponse response, String token) {
        addCookie(response, ACCESS_COOKIE_NAME, token, getAccessTokenTTL());
    }

    public void setRefreshToken(HttpServletResponse response, String token) {
        addCookie(response, REFRESH_COOKIE_NAME, token, getRefreshTokenTTL());
    }

    public Optional<String> getAccessToken(HttpServletRequest request) {
        return getCookieValue(request, ACCESS_COOKIE_NAME);
    }

    public Optional<String> getRefreshToken(HttpServletRequest request) {
        return getCookieValue(request, REFRESH_COOKIE_NAME);
    }

    public boolean isValidAccessToken(String token) {
        return accessTokenService.isValidAccessToken(token);
    }

    public boolean isValidRefreshToken(String token) {
        return refreshTokenService.isValidRefreshToken(token);
    }

    public String getUsernameFromAccessToken(String token) {
        return accessTokenService.extractUsername(token);
    }

    public String getUsernameFromRefreshToken(String token) {
        return refreshTokenService.findByToken(token).getUser().getUsername();
    }

    public String generateToken(UserDetails userDetails) {
        return accessTokenService.generateToken(userDetails);
    }

    public Duration getAccessTokenTTL() {
        return accessTokenService.getAccessTokenExpiration();
    }

    public Duration getRefreshTokenTTL() {
        return refreshTokenService.getRefreshTokenExpiration();
    }

    public void clear(HttpServletResponse response, String refreshToken) {
        if (refreshToken != null) refreshTokenService.deleteByToken(refreshToken);
        deleteCookie(response, ACCESS_COOKIE_NAME);
        deleteCookie(response, REFRESH_COOKIE_NAME);
    }

    private void addCookie(HttpServletResponse response, String name, String value, Duration ttl) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath(COOKIE_PATH);
        cookie.setMaxAge((int) ttl.toSeconds());
        response.addCookie(cookie);
    }

    private void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath(COOKIE_PATH);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private Optional<String> getCookieValue(HttpServletRequest request, String name) {
        return Optional.ofNullable(WebUtils.getCookie(request, name))
                .map(Cookie::getValue);
    }
}

