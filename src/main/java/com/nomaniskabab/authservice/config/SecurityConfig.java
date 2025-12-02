package com.nomaniskabab.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // PUBLIC endpoints
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()

                        // Everything else → secure with login popup
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // <-- Login Popup for Swagger

        return http.build();
    }
}
