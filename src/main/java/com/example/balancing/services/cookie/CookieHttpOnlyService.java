package com.example.balancing.services.cookie;

import com.example.balancing.exception.token.RefreshTokenNotFoundException;
import com.example.balancing.payloads.requests.RefreshTokenRequest;
import com.example.balancing.payloads.requests.SignInRequest;
import com.example.balancing.payloads.requests.SignUpRequest;
import com.example.balancing.payloads.responses.AuthenticationResponse;
import com.example.balancing.services.auth.AuthenticationService;
import com.example.balancing.services.tokens.access.AccessTokenService;
import com.example.balancing.services.tokens.refresh.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.util.Arrays;

@Service
public class CookieHttpOnlyService {

    private final String accessCookieName = "accessToken";
    private final String accessCookiePath = "/api/auth/v2/access-token/";
    private final String refreshCookieName = "refreshToken";
    private final String refreshCookiePath = "/api/auth/v2/refresh-token/";
    private final RefreshTokenService refreshTokenService;
    private final AccessTokenService accessTokenService;
    private final AuthenticationService authenticationService;

    public CookieHttpOnlyService(RefreshTokenService refreshTokenService, AccessTokenService accessTokenService, AuthenticationService authenticationService) {
        this.refreshTokenService = refreshTokenService;
        this.accessTokenService = accessTokenService;
        this.authenticationService = authenticationService;
    }

    public ResponseEntity<AuthenticationResponse> signUp(SignUpRequest request,
                                                         HttpServletResponse response) {
        var authResponse = authenticationService.signUp(request);

        response.addTokensCookie(response, authResponse.getAccessToken(),
                authResponse.getRefreshToken());
        ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
        return getTokens(authResponse.getAccessToken(), authResponse.getRefreshToken());
    }

    public ResponseEntity<?> signIn(SignInRequest request) {
        var authResponse = authenticationService.signIn(request);


        return getTokens(authResponse.getAccessToken(), authResponse.getRefreshToken());
    }

    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String token = getRefreshToken(request);
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(token);
        var authResponse = authenticationService.refreshToken(refreshTokenRequest);
        return getTokens(authResponse.getAccessToken(), authResponse.getRefreshToken());
    }


    public String getAccessToken(HttpServletRequest request) {
        return getCookieValueByName(request, accessCookieName);
    }

    public String getRefreshToken(HttpServletRequest request) {
        return getCookieValueByName(request, refreshCookieName);
    }

    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
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

    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> refreshCookieName.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(RefreshTokenNotFoundException::new);
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
