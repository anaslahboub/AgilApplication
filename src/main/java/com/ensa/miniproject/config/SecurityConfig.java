package com.ensa.miniproject.config;

import com.ensa.miniproject.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()

                        // Autorisation par rôle
                        .requestMatchers("/api/developer/**").hasRole("DEVELOPER")
                        .requestMatchers("/api/scrum-master/**").hasRole("SCRUMMASTER")
                        .requestMatchers("/api/product-owner/**").hasRole("PRODUCTOWNER")

                        // Sécurité des APIs principales (modifier selon ta logique métier)
                        .requestMatchers("/api/epics/**").hasAnyRole("PRODUCTOWNER", "SCRUMMASTER")
                        .requestMatchers("/api/product-backlogs/**").hasAnyRole("PRODUCTOWNER", "SCRUMMASTER")
                        .requestMatchers("/api/projects/**").hasAnyRole("PRODUCTOWNER", "SCRUMMASTER")
                        .requestMatchers("/api/sprint-backlogs/**").hasAnyRole("PRODUCTOWNER", "SCRUMMASTER", "DEVELOPER")
                        .requestMatchers("/api/user-stories/**").hasAnyRole("PRODUCTOWNER", "SCRUMMASTER", "DEVELOPER")

                        // Tout le reste nécessite une authentification
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}