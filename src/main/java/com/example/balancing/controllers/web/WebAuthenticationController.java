package com.example.balancing.controllers.web;

import com.example.balancing.payloads.requests.SignInRequest;
import com.example.balancing.payloads.requests.SignUpRequest;
import com.example.balancing.payloads.responses.AuthenticationResponse;
import com.example.balancing.services.auth.AuthenticationService;
import com.example.balancing.services.cookie.CookieHttpOnlyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class WebAuthenticationController {

    private final AuthenticationService authenticationService;
    private final CookieHttpOnlyService cookieService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody SignInRequest request, HttpServletResponse response) {

        AuthenticationResponse authResponse = authenticationService.signIn(request);

        cookieService.addAccessTokenCookie(response, authResponse.getAccessToken());
        cookieService.addRefreshTokenCookie(response, authResponse.getRefreshToken());

        return ResponseEntity.ok(authResponse);
    }


//    @GetMapping("/sign-up")
//    String showSignUpForm() {
//        return "auth/sign-up";
//    }
//
//    @PostMapping("/sign-up")
//    String signUp(@Valid SignUpRequest request) {
//        authenticationService.signUp(request);
//        return "redirect:/auth/sign-in";
//    }
//
//    @PostMapping("/sign-in")
//    String signIn(@Valid SignInRequest request) {
//        authenticationService.signIn(request);
//        return "redirect:/unit";
//    }
//
//    @GetMapping("/sign-in")
//    String signIn() {
//        return "auth/sign-in";
//    }

}
