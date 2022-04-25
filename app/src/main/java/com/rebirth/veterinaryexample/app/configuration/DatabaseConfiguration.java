package com.rebirth.veterinaryexample.app.configuration;


import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class DatabaseConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) auth.getPrincipal();
            AccessToken token = keycloakPrincipal.getKeycloakSecurityContext().getToken();
            String subject = token.getSubject();
            System.out.println(subject);
            return Optional.of(subject);
        };
    }
}
