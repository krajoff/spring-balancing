package com.example.balancing.controllers.web;

import com.example.balancing.payloads.requests.SignInRequest;
import com.example.balancing.payloads.requests.SignUpRequest;
import com.example.balancing.payloads.responses.AuthenticationResponse;
import com.example.balancing.services.auth.AuthenticationService;
import com.example.balancing.services.cookie.CookieHttpOnlyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Регистрация и аутентификация")
public class WebAuthenticationController {

    private final CookieHttpOnlyService cookieService;

    @GetMapping("/signup")
    public String showSignUpForm() {
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid SignUpRequest request, HttpServletResponse response) {
        cookieService.signUp(request, response);
        return "user/index";
    }

    @GetMapping("/login")
    public String showLoginForm(HttpServletRequest request) {
        return cookieService.isValidToken(cookieService.getRefreshToken(request))
                ? "redirect:/user" : "auth/login";
    }

    @PostMapping("/login")
    public String login(@Valid SignInRequest request, HttpServletResponse response) {
        cookieService.signIn(request, response);
        return "redirect:/user";
    }

}
