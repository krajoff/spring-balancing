package com.example.balancing.controllers.web.auth;

import com.example.balancing.dtos.SignInRequest;
import com.example.balancing.dtos.SignUpRequest;
import com.example.balancing.services.jwt.AuthenticationService;
import com.example.balancing.services.jwt.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
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

    @PostMapping("/login")
    String login(@Valid SignInRequest request, HttpServletResponse response) {
        var responseJWT = authenticationService.signIn(request);
        Cookie cookie = new Cookie("jwt", responseJWT.getToken());
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) jwtService.getExpirationTime() / 1000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/user/me";
    }

    @GetMapping("/login")
    String login() {
        return "auth/login";
    }

}
