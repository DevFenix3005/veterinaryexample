package com.rebirth.veterinaryexample.app.web.resources;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.UUID;

public class BaseResource {

    protected URI generateUri(UUID uuid) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{uuid}")
                .build(uuid);
    }

    protected AccessToken getAccessToken() {
        KeycloakAuthenticationToken keycloakPrincipal = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return keycloakPrincipal.getAccount().getKeycloakSecurityContext().getToken();
    }


}
