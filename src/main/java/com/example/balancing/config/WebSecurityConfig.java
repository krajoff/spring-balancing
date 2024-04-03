package com.example.balancing.config;

import com.example.balancing.repository.UserRepository;
import com.example.balancing.services.jwt.JwtAuthenticationFilter;
import com.example.balancing.services.user.UserService;
import com.example.balancing.services.user.UserServiceImpl;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Value("${spring.security.debug:false}")
    boolean securityDebug;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

//    @Autowired
//    private UserRepository userRepository;

    @Bean(name = "passwordEncoder")
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserServiceImpl(userRepository, bCryptPasswordEncoder());
//    }
    @Bean
    public DaoAuthenticationProvider authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        authProvider.setUserDetailsService(userService.userDetailsService());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/", "/login", "/register", "/static/**", "/css/**", "/error").permitAll()
                                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                                .requestMatchers("/swagger-ui/*").hasAuthority("ADMIN")
                                .requestMatchers("/api/*").hasAuthority("ADMIN")
                                .requestMatchers("/unit").hasAuthority("USER")
                                .requestMatchers("/record").hasAuthority("USER")
                                .anyRequest().authenticated())
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/login")
                                .permitAll()
                                .defaultSuccessUrl("/unit/"))
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .permitAll())
                .csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

}
