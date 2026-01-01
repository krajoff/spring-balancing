package com.example.balancing.controller.web;

import com.example.balancing.payload.request.SignInRequest;
import com.example.balancing.payload.request.SignUpRequest;
import com.example.balancing.service.auth.AuthenticationService;
import com.example.balancing.service.cookie.CookieHttpOnlyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class WebAuthenticationController {

    private final AuthenticationService authenticationService;
    private final CookieHttpOnlyService cookieService;

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("signupRequest", new SignUpRequest());
        return "auth/signup"; // Thymeleaf template
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signupRequest") SignUpRequest request,
                         BindingResult result,
                         HttpServletResponse response,
                         Model model) {
        if (result.hasErrors()) {
            return "auth/signup";
        }

        try {
            var tokens = authenticationService.signUp(request);
            setAuthCookies(response, tokens);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/signup";
        }

        return "redirect:/user";
    }

    @GetMapping("/login")
    public String showLoginForm(HttpServletRequest request, Model model) {
        var accessToken = cookieService.getAccessToken(request);
        if (accessToken.isPresent() && cookieService.isValidAccessToken(accessToken.get())) {
            return "redirect:/user";
        }
        model.addAttribute("loginRequest", new SignInRequest());
        return "auth/login"; // Thymeleaf template
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginRequest") SignInRequest request,
                        BindingResult result,
                        HttpServletResponse response,
                        Model model) {
        if (result.hasErrors()) {
            return "auth/login";
        }

        try {
            var tokens = authenticationService.signIn(request);
            setAuthCookies(response, tokens);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/login";
        }

        return "redirect:/user";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        var refreshToken = cookieService.getRefreshToken(request).orElse(null);
        cookieService.clear(response, refreshToken);
        request.getSession().invalidate();
        return "redirect:/auth/login";
    }

    private void setAuthCookies(HttpServletResponse response, Object tokens) {
        // tokens должен быть AuthenticationResponse
        var authTokens = (com.example.balancing.payload.response.AuthenticationResponse) tokens;
        cookieService.setAccessToken(response, authTokens.getAccessToken());
        cookieService.setRefreshToken(response, authTokens.getRefreshToken());
    }
}
