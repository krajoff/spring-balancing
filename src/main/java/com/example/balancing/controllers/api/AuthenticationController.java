package com.example.balancing.controllers.api;

import com.example.balancing.dto.JwtAuthenticationResponse;
import com.example.balancing.dto.SignInRequest;
import com.example.balancing.dto.SignUpRequest;
import com.example.balancing.services.jwt.AuthenticationService;
import com.example.balancing.services.jwt.JwtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService,
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
//        var authenticatedUser = authenticationService.signIn(request);
//
//        String jwtToken = jwtService.generateToken(authenticatedUser);
//
//        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return authenticationService.signIn(request);
    }
}
