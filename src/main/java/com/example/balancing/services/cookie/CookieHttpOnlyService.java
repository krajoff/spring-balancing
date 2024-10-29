package com.example.balancing.services.cookie;

import com.example.balancing.exception.token.RefreshTokenNotFoundException;
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

import java.util.Arrays;
import java.util.Optional;

@Service
public class CookieHttpOnlyService {

    private final String accessCookieName = "accessToken";
    private final String accessCookiePath = "/";
    private final String refreshCookieName = "refreshToken";
    private final String refreshCookiePath = "/";
    private final RefreshTokenService refreshTokenService;
    private final AccessTokenService accessTokenService;
    private final AuthenticationService authenticationService;

    public CookieHttpOnlyService(RefreshTokenService refreshTokenService, AccessTokenService accessTokenService, AuthenticationService authenticationService) {
        this.refreshTokenService = refreshTokenService;
        this.accessTokenService = accessTokenService;
        this.authenticationService = authenticationService;
    }

    public void signUp(SignUpRequest request, HttpServletResponse response) {
        var authResponse = authenticationService.signUp(request);
        addTokensCookie(response, authResponse.getAccessToken(),
                authResponse.getRefreshToken());
    }

    public void signIn(SignInRequest request, HttpServletResponse response) {
        var authResponse = authenticationService.signIn(request);
        addTokensCookie(response, authResponse.getAccessToken(),
                authResponse.getRefreshToken());
    }

    public Optional<String> getAccessToken(HttpServletRequest request) {
        return getCookieValueByName(request, accessCookieName);
    }

    public Optional<String> getRefreshToken(HttpServletRequest request) {
        return getCookieValueByName(request, refreshCookieName);
    }

    private Optional<String> getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        return Optional.ofNullable(cookie).map(Cookie::getValue);
    }

    public boolean isValidToken(String refreshToken){
        return refreshTokenService.isValidExpiration(refreshToken);
    }

    public void addAccessTokenCookie(HttpServletResponse response, String accessToken) {
        Cookie accessCookie = new Cookie(accessCookieName, accessToken);
        accessCookie.setHttpOnly(true);
        accessCookie.setPath(accessCookiePath);
        accessCookie.setMaxAge((int) (accessTokenService.getAccessTokenExpiration() / 1000));
        response.addCookie(accessCookie);
    }

    public void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshCookie = new Cookie(refreshCookieName, refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath(refreshCookiePath);
        refreshCookie.setMaxAge((int) (refreshTokenService.getRefreshTokenExpiration() / 1000));
        response.addCookie(refreshCookie);
    }

    private void addTokensCookie(HttpServletResponse response,
                                 String accessToken, String refreshToken) {
        addAccessTokenCookie(response, accessToken);
        addRefreshTokenCookie(response, refreshToken);
    }



    public void clearCookies(HttpServletResponse response) {
        Cookie accessCookie = new Cookie("accessToken", null);
        accessCookie.setHttpOnly(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(0);
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("refreshToken", null);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(0);
        response.addCookie(refreshCookie);
    }

}
