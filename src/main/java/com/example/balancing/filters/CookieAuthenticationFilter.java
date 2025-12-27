package com.example.balancing.filters;

import com.example.balancing.services.cookie.CookieHttpOnlyService;
import com.example.balancing.services.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class CookieAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final CookieHttpOnlyService cookieService;

    public CookieAuthenticationFilter(UserService userService, CookieHttpOnlyService cookieService) {
        this.userService = userService;
        this.cookieService = cookieService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            authenticate(request, response);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticate(HttpServletRequest request, HttpServletResponse response) {
        // Сначала пробуем access token
        Optional<String> accessToken = cookieService.getAccessToken(request);
        if (accessToken.isPresent() && cookieService.isValidAccessToken(accessToken.get())) {
            setAuthentication(request, cookieService.getUsernameFromAccessToken(accessToken.get()));
            return;
        }

        // Если access token недействителен, пробуем refresh token
        Optional<String> refreshToken = cookieService.getRefreshToken(request);
        if (refreshToken.isPresent() && cookieService.isValidRefreshToken(refreshToken.get())) {
            String username = cookieService.getUsernameFromRefreshToken(refreshToken.get());
            setAuthentication(request, username);

            // Генерируем новый access token и ставим cookie
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
            String newAccessToken = cookieService.generateToken(userDetails);
            cookieService.setAccessToken(response, newAccessToken);
        }
    }

    private void setAuthentication(HttpServletRequest request, String username) {
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
        var authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
