package com.example.balancing.config;

import com.example.balancing.services.jwt.JwtAuthenticationFilter;
import com.example.balancing.services.user.UserService;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
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
                                .requestMatchers("/", "/js/**", "/login", "/register",
                                        "/static/**", "/css/**", "/error", "/slider/*").permitAll()
                                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                                .requestMatchers("/unit/**").hasAuthority("USER")
                                .requestMatchers("/record/**").hasAuthority("USER")
                                .requestMatchers("/**").hasAuthority("ADMIN")
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
