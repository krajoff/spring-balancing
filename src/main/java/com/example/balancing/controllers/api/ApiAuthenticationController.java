package com.example.balancing.controllers.api;

import com.example.balancing.payloads.requests.SignInRequest;
import com.example.balancing.payloads.requests.SignUpRequest;
import com.example.balancing.payloads.responses.AuthenticationResponse;
import com.example.balancing.services.auth.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
public class ApiAuthenticationController {

    private final AuthenticationService authenticationService;

    public ApiAuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public AuthenticationResponse register(@RequestBody SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/login")
    public AuthenticationResponse authenticate(@RequestBody SignInRequest request) {
        return authenticationService.signIn(request);
    }

}
