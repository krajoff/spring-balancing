package com.example.balancing.controllers.api;

import com.example.balancing.payloads.responses.JwtAuthenticationResponse;
import com.example.balancing.payloads.requests.SignInRequest;
import com.example.balancing.payloads.requests.SignUpRequest;
import com.example.balancing.services.auth.AuthenticationService;
import com.example.balancing.services.tokens.jwt.JwtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class ApiAuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public ApiAuthenticationController(JwtService jwtService,
                                    AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public JwtAuthenticationResponse register(@RequestBody SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse authenticate(@RequestBody SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
