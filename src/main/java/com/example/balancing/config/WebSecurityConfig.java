package com.example.balancing.config;

import com.example.balancing.services.jwt.JwtAuthenticationFilter;
import com.example.balancing.services.user.UserService;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Value;
=======
>>>>>>> e1a94a2 (Fix some stuff but it not work properly)
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    //@Value("${spring.security.debug:false}")
   // boolean securityDebug;
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
    public AuthenticationProvider authenticationProvider() {
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

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userService.userDetailsService());
//        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
//        return authProvider;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        authorize -> authorize
<<<<<<< HEAD
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
=======
                                .requestMatchers("/", "/login",
                                        "/register", "/static/**", "/css/**", "/error", "/auth/**").permitAll()
                               // .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                                .requestMatchers("/unit/**").hasAuthority("USER")
                                .requestMatchers("/record/**").hasAuthority("USER")
                                .requestMatchers("/swagger-ui/*").hasAuthority("ADMIN")
                                .requestMatchers("/api/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated())
//                .cors(cors -> cors.configurationSource(request -> {
//                    var corsConfiguration = new CorsConfiguration();
//                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
//                    corsConfiguration.setAllowedMethods(
//                            List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                    corsConfiguration.setAllowedHeaders(List.of("*"));
//                    corsConfiguration.setAllowCredentials(true);
//                    return corsConfiguration;
//                }))
//                .formLogin(
//                        formLogin -> formLogin
//                                .loginPage("/auth/sign-in")
//                                .permitAll()
//                                .defaultSuccessUrl("/unit/"))
//                .logout(logout ->
//                        logout
//                                .logoutUrl("/logout")
//                                .permitAll()
//                                .logoutSuccessUrl("/"))
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
>>>>>>> e1a94a2 (Fix some stuff but it not work properly)
                .csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

}
