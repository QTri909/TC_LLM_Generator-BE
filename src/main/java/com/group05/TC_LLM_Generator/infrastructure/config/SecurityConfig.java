package com.group05.TC_LLM_Generator.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Security configuration for REST API with stateless authentication.
 * Configured for client-side rendering (no server-side login forms).
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for stateless REST API
            .csrf(csrf -> csrf.disable())

            // Configure CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Configure session management - stateless for REST API
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Configure authorization
            .authorizeHttpRequests(auth -> auth
                // Allow all requests for now (you can configure specific endpoints later)
                .anyRequest().permitAll()
            )

            // Disable form login (server-side rendering)
            .formLogin(form -> form.disable())

            // Disable HTTP Basic authentication
            .httpBasic(basic -> basic.disable());

        return http.build();
    }

    /**
     * Configure CORS to allow requests from frontend applications.
     * Adjust the allowed origins based on your frontend URL.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow requests from your frontend (adjust URLs as needed)
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",    // React/Next.js default
            "http://localhost:5173",    // Vite default
            "http://localhost:4200",    // Angular default
            "http://localhost:8080"     // Vue default
        ));

        // Allow all HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // Allow all headers
        configuration.setAllowedHeaders(List.of("*"));

        // Allow credentials (cookies, authorization headers)
        configuration.setAllowCredentials(true);

        // Cache preflight response for 1 hour
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
