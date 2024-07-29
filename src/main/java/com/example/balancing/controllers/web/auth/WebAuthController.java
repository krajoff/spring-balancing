package com.example.balancing.controllers.web.auth;

import com.example.balancing.requests.SignInRequest;
import com.example.balancing.requests.SignUpRequest;
import com.example.balancing.services.jwt.AuthenticationService;
import com.example.balancing.services.jwt.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class WebAuthController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @GetMapping("/signup")
    String showSignupForm() {
        return "auth/signup";
    }

    @PostMapping("/signup")
    String signup(@Valid SignUpRequest request) {
        authenticationService.signUp(request);
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    String showLoginForm() {
        return "auth/login";
    }
    @PostMapping("/login")
    String login(@Valid SignInRequest request, HttpServletResponse response) {
        authenticationService.signIn(request, response);
        return "redirect:/user/me";
    }

}
