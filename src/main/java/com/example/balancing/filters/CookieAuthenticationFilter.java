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
        Optional<String> accessTokenOpt = cookieService.getAccessToken(request);
        Optional<String> refreshTokenOpt = cookieService.getRefreshToken(request);

        // Validate the access token

        if (accessTokenOpt.isPresent() && cookieService.isValidAccessToken(accessTokenOpt.get())) {
            String accessToken = accessTokenOpt.get();
            String username = cookieService.getUsernameFromAccessToken(accessToken);
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
            // Create an authentication token and set it in the SecurityContext
            BasicAuthenticationToken auth = new BasicAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        // If access token is invalid and refresh token is present
        else if (refreshTokenOpt.isPresent() && cookieService.isValidToken(refreshTokenOpt.get())) {
            String refreshToken = refreshTokenOpt.get();
            String username = cookieService.getUsernameFromRefreshToken(refreshToken); // Method to extract username from refresh token
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
            // Re-issue access token or perform additional logic to refresh the session
            String newAccessToken = cookieService.refreshAccessToken(refreshToken); // Implement this method to refresh the access token
            cookieService.addAccessTokenCookie(response, newAccessToken); // Add new access token cookie

            // Create an authentication token and set it in the SecurityContext
            BasicAuthenticationToken auth = new BasicAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            // If both tokens are invalid, handle unauthorized access
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return; // Stop the filter chain
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

    private boolean isValidAccessToken(String accessToken){

    }
}
