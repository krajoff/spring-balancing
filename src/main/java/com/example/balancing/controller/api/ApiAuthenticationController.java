package com.example.balancing.controller.api;

import com.example.balancing.payloads.requests.RefreshTokenRequest;
import com.example.balancing.payloads.requests.SignInRequest;
import com.example.balancing.payloads.requests.SignUpRequest;
import com.example.balancing.payloads.responses.AuthenticationResponse;
import com.example.balancing.services.auth.AuthenticationService;
import com.example.balancing.services.cookie.CookieHttpOnlyService;
import com.example.balancing.services.tokens.access.AccessTokenService;
import com.example.balancing.services.tokens.refresh.RefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ApiAuthenticationController {

    private final AuthenticationService authenticationService;
    private final CookieHttpOnlyService cookieService;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signUp(@Valid @RequestBody SignUpRequest request,
                                                         HttpServletResponse response) {
        AuthenticationResponse tokens = authenticationService.signUp(request);
        setAuthCookies(response, tokens);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> signIn(@Valid @RequestBody SignInRequest request,
                                                         HttpServletResponse response) {
        AuthenticationResponse tokens = authenticationService.signIn(request);
        setAuthCookies(response, tokens);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@Valid @RequestBody RefreshTokenRequest request,
                                                          HttpServletResponse response) {
        AuthenticationResponse tokens = authenticationService.refreshToken(request);
        setAuthCookies(response, tokens);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        cookieService.clear(response);
        return ResponseEntity.noContent().build();
    }

    private void setAuthCookies(HttpServletResponse response, AuthenticationResponse tokens) {
        cookieService.setAccessToken(response, tokens.getAccessToken());
        cookieService.setRefreshToken(response, tokens.getRefreshToken());
    }
}
