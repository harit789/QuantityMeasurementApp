package com.QuantityMeasurementApp.config;

import com.QuantityMeasurementApp.security.JwtFilter;
import com.QuantityMeasurementApp.security.OAuth2SuccessHandler;
import com.QuantityMeasurementApp.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private OAuth2SuccessHandler oauth2SuccessHandler;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Authentication provider - uses our UserDetailsService
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // Authentication manager - needed by AuthController
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
                // Public endpoints - no token needed
                .requestMatchers("/auth/register").permitAll()
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/auth/oauth2/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                // All other endpoints need JWT token
                .anyRequest().authenticated()
            )
            // Stateless session - no server side sessions
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // Allow H2 console frames
            .headers(headers -> headers
                .frameOptions(frame -> frame.disable())
            )
            // CORS configuration for frontend
            .cors(cors -> cors.configurationSource(request -> {
                var config = new org.springframework.web.cors.CorsConfiguration();
                config.setAllowedOrigins(java.util.List.of("*"));
                config.setAllowedMethods(java.util.List.of(
                    "GET", "POST", "PUT", "DELETE"));
                config.setAllowedHeaders(java.util.List.of("*"));
                return config;
            }))
            // Add OAuth2 login support
            .oauth2Login(oauth2 -> oauth2
                .successHandler(oauth2SuccessHandler)
            )
            // Add JWT filter before Spring's default filter
            .addFilterBefore(jwtFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
