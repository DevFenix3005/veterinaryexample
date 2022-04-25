package com.rebirth.veterinaryexample.app.services.impl;

import com.google.common.collect.Lists;
import com.rebirth.veterinaryexample.app.services.KeycloakAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Log4j2
public class KeycloakAdminServiceImpl implements KeycloakAdminService {

    private final KeycloakRestTemplate keycloakRestTemplate;

    @Value("${keycloak.auth-server-url}")
    private String keycloakHost;
    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Override
    public List<UserRepresentation> getAllUsersWithMemberRolesFromKeycloak() {
        String url = keycloakHost + "/admin/realms/" + keycloakRealm + "/roles/App-Member/users";
        ResponseEntity<UserRepresentation[]> response = keycloakRestTemplate.getForEntity(url, UserRepresentation[].class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public UserRepresentation createUserWithMemberRole(UserRepresentation userRepresentation) {
        String url = keycloakHost + "/admin/realms/" + keycloakRealm + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserRepresentation> request = new HttpEntity<>(userRepresentation, headers);

        ResponseEntity<?> response = keycloakRestTemplate.postForEntity(url, request, Void.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            String locationId = response.getHeaders().getFirst("Location");
            addMemberRole(locationId);
            log.info("New user location: {}", locationId);
            userRepresentation.setSelf(locationId);
            return userRepresentation;
        } else {
            return null;
        }
    }

    private void addMemberRole(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setId("77355f7b-7c88-43be-88ab-2b7f28d0c9d1");
        roleRepresentation.setName("App-Member");
        ArrayList<RoleRepresentation> roleList = Lists.newArrayList(roleRepresentation);
        HttpEntity<List<RoleRepresentation>> request = new HttpEntity<>(roleList, headers);
        ResponseEntity<?> response = keycloakRestTemplate.postForEntity(url + "/role-mappings/realm", request, Void.class);
        log.info("RESPUESTA: {}", response.toString());

    }
}
