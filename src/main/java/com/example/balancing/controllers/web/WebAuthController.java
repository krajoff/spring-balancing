package com.example.balancing.controllers.web;

import com.example.balancing.payloads.requests.SignInRequest;
import com.example.balancing.payloads.requests.SignUpRequest;
import com.example.balancing.services.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class WebAuthController {
    private final AuthenticationService authenticationService;

    @GetMapping("/sign-up")
    String showSignUpForm() {
        return "auth/sign-up";
    }

    @PostMapping("/sign-up")
    String signUp(@Valid SignUpRequest request) {
        authenticationService.signUp(request);
        return "redirect:/auth/sign-in";
    }

    @PostMapping("/sign-in")
    String signIn(@Valid SignInRequest request) {
        authenticationService.signIn(request);
        return "redirect:/unit";
    }

    @GetMapping("/sign-in")
    String signIn() {
        return "auth/sign-in";
    }

}
