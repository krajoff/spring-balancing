package com.example.balancing.filters;

import com.example.balancing.exception.token.RefreshTokenException;
import com.example.balancing.services.tokens.access.AccessTokenService;
import com.example.balancing.services.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AccessAuthenticationFilter extends OncePerRequestFilter {

    //   private final HandlerExceptionResolver handlerExceptionResolver;
    private final AccessTokenService accessTokenService;
    private final UserService userService;

    @Autowired
    public AccessAuthenticationFilter(
            AccessTokenService accessTokenService,
            UserService userService
            // HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.accessTokenService = accessTokenService;
        this.userService = userService;
        //  this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException, RefreshTokenException {
        final String authHeader = request.getHeader("Authorization");

        // Проверяем заголовок
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final String username = accessTokenService.extractUsername(jwt);

            Authentication authentication = SecurityContextHolder
                    .getContext().getAuthentication();

            if (username != null && authentication == null) {
                UserDetails userDetails = userService.userDetailsService()
                        .loadUserByUsername(username);

                if (accessTokenService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token");
        }
    }
}
