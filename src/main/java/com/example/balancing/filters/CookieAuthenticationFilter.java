package com.example.balancing.filters;

import com.example.balancing.services.cookie.CookieHttpOnlyService;
import com.example.balancing.services.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Autowired
    public CookieAuthenticationFilter(
            UserService userService,
            CookieHttpOnlyService cookieService
    ) {
        this.userService = userService;
        this.cookieService = cookieService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        boolean isAuthenticated = authenticate(request, response);
        if (isAuthenticated) {
            filterChain.doFilter(request, response);
        } else {
            handleUnauthorized(response);
        }
    }

    private boolean authenticate(HttpServletRequest request, HttpServletResponse response) {
        return cookieService.getAccessToken(request)
                .filter(token -> authenticateUsingAccessToken(request, token))
                .or(() -> cookieService.getRefreshToken(request)
                        .filter(token -> authenticateUsingRefreshToken(request, response, token)))
                .isPresent();
    }

    private void setAuthentication(HttpServletRequest request, String username) {
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
        var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private boolean authenticateUsingAccessToken(HttpServletRequest request, String accessToken) {
        if (cookieService.isValidAccessToken(accessToken) && SecurityContextHolder.getContext().getAuthentication() == null) {
            setAuthentication(request, cookieService.getUsernameFromAccessToken(accessToken));
            return true;
        }
        return false;
    }

    private boolean authenticateUsingRefreshToken(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        if (cookieService.isValidRefreshToken(refreshToken) && SecurityContextHolder.getContext().getAuthentication() == null) {
            String username = cookieService.getUsernameFromRefreshToken(refreshToken);
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
            setAuthentication(request, username);

            String accessToken = cookieService.generateToken(userDetails);
            cookieService.addAccessTokenCookie(response, accessToken);
            return true;
        }
        return false;
    }

    private void handleUnauthorized(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid or missing token");
    }
}
