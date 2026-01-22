package com.example.balancing.config.security;

import com.example.balancing.filter.CookieAuthenticationFilter;
import com.example.balancing.service.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.balancing.controller.web.WebAuthenticationController.AUTH_LOGIN;
import static com.example.balancing.controller.web.WebAuthenticationController.SUCCESS_LOGIN;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private final CookieAuthenticationFilter cookieAuthenticationFilter;

    public SecurityConfig(UserService userService, CookieAuthenticationFilter cookieAuthenticationFilter) {
        this.userService = userService;
        this.cookieAuthenticationFilter = cookieAuthenticationFilter;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService::getUserByUsername;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/",
                                "/api/**",
                                "/auth/**",
                                "/css/**",
                                "/js/**",
                                "/errors/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                // Web login
                .formLogin(form -> form
                        .loginPage("/" + AUTH_LOGIN)
                        .loginProcessingUrl("/" + AUTH_LOGIN)
                        .defaultSuccessUrl(SUCCESS_LOGIN, true)
                        .failureUrl("/auth/login?error")
                        .permitAll()
                )
                // Logout
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "access_token", "refresh_token")
                        .permitAll()
                )
                // Session
                .sessionManagement(manager -> manager
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                // CSRF
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/auth/logout"))
                .cors(AbstractHttpConfigurer::disable)
                // Cookie auth only for API
                .addFilterBefore(cookieAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
