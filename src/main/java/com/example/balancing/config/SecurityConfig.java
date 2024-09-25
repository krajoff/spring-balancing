package com.example.balancing.config;

import com.example.balancing.services.jwt.JwtAuthenticationFilter;
import com.example.balancing.services.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;


import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Конфигурация безопасности приложения.
 * Настраивает фильтры безопасности и правила доступа для различных URL-путей.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return userService::getUserByEmail;
    }

    /**
     * Конфигурация цепочки фильтров безопасности.
     *
     * @param http объект HttpSecurity, используемый для настройки
     *             безопасности веб-приложения
     * @return объект SecurityFilterChain, который настраивает безопасность
     * для различных URL-путей
     * @throws Exception в случае ошибок конфигурации безопасности
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/js/**", "/login", "/signup",
                                "/static/**", "/css/**", "/errors").permitAll()
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/stations"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll())
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                    corsConfiguration.setAllowedMethods(
                            List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    /**
     * Создает бин AuthenticationProvider для настройки процесса аутентификации.
     *
     * @return AuthenticationProvider, который использует UserDetailsService
     * и PasswordEncoder для проверки учетных данных пользователей.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        authProvider.setUserDetailsService(userService.userDetailsService());

        return authProvider;
    }

    /**
     * Создает бин AuthenticationManager для управления процессом аутентификации.
     *
     * @param config объект AuthenticationConfiguration, используемый для
     *               получения AuthenticationManager.
     * @return AuthenticationManager, который используется для аутентификации
     * пользователей.
     * @throws Exception в случае ошибок получения AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager
    (AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Создает бин BCryptPasswordEncoder для кодирования паролей пользователей.
     *
     * @return BCryptPasswordEncoder, используемый для безопасного хранения паролей.
     */
    @Bean(name = "passwordEncoder")
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
