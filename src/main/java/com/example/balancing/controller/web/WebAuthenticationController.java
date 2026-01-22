package com.example.balancing.controller.web;

import com.example.balancing.payload.request.SignInRequest;
import com.example.balancing.payload.request.SignUpRequest;
import com.example.balancing.payload.response.AuthenticationResponse;
import com.example.balancing.service.auth.AuthenticationService;
import com.example.balancing.service.cookie.CookieHttpOnlyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class WebAuthenticationController {

    public final static String SUCCESS_LOGIN = "/stations";
    public final static String REDIRECT_SUCCESS_LOGIN = "redirect:" + SUCCESS_LOGIN;
    public final static String REDIRECT_AUTH_LOGIN = "redirect:auth/login";
    public final static String AUTH_SIGNUP = "auth/signup";
    public final static String AUTH_LOGIN = "auth/login";

    private final AuthenticationService authenticationService;
    private final CookieHttpOnlyService cookieService;

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("signupRequest", new SignUpRequest());
        return AUTH_SIGNUP;
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signupRequest") SignUpRequest request,
                         BindingResult result,
                         HttpServletResponse response,
                         Model model) {

        if (result.hasErrors()) return AUTH_SIGNUP;

        try {
            var tokens = authenticationService.signUp(request);
            setAuthCookies(response, tokens);
            return REDIRECT_SUCCESS_LOGIN;
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return AUTH_SIGNUP;
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal()))
            return REDIRECT_SUCCESS_LOGIN;
        model.addAttribute("loginRequest", new SignInRequest());
        return AUTH_LOGIN;
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginRequest") SignInRequest request,
                        BindingResult result, HttpServletResponse response, Model model) {

        if (result.hasErrors()) return AUTH_LOGIN;
        try {
            AuthenticationResponse tokens = authenticationService.signIn(request);
            setAuthCookies(response, tokens);
            return REDIRECT_SUCCESS_LOGIN;
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return AUTH_LOGIN;
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        var refreshToken = cookieService.getRefreshToken(request).orElse(null);
        cookieService.clear(response, refreshToken);
        request.getSession().invalidate();
        return REDIRECT_AUTH_LOGIN;
    }

    @GetMapping({"", "/"})
    public String redirectToLogin() {
        return "redirect:/auth/login";
    }

    private void setAuthCookies(HttpServletResponse response, Object tokens) {
        var authTokens = (com.example.balancing.payload.response.AuthenticationResponse) tokens;
        cookieService.setAccessToken(response, authTokens.getAccessToken());
        cookieService.setRefreshToken(response, authTokens.getRefreshToken());
    }



}
