package com.example.balancing.config;

import com.example.balancing.filters.AccessAuthenticationFilter;
import com.example.balancing.services.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
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
public class SecurityConfig {

    private final UserService userService;
    private final AccessAuthenticationFilter accessAuthenticationFilter;

    public SecurityConfig(UserService userService,
                          AccessAuthenticationFilter accessAuthenticationFilter) {
        this.userService = userService;
        this.accessAuthenticationFilter = accessAuthenticationFilter;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService::getUserByUsername;
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

        // Делаем аутентификацию через токены обязательной для всех URL, кроме публичных
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**", "/js/**", "/login", "/signup",
                                "/refresh-token", "/static/**", "/css/**", "/errors").permitAll()
                        .anyRequest().authenticated())
                // Настройка формы логина для логина/пароля
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/login")
//                        .permitAll()
//                        .defaultSuccessUrl("/stations"))
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .permitAll())

                // Кросс-доменная политика
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                    corsConfiguration.setAllowedMethods(
                            List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))

                // Отключаем сессию, так как работаем в stateless режиме с токенами
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
//                .authenticationProvider(authenticationProvider())

                // Фильтр для работы с access токенами
                .addFilterBefore(accessAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)

                // Отключаем CSRF (не нужно для stateless-приложений)
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
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        authProvider.setUserDetailsService(userDetailsService());

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
        return new ProviderManager(List.of(authenticationProvider()));
       // return config.getAuthenticationManager();
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
