package com.rebirth.veterinaryexample.app.services;

import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;


public interface KeycloakAdminService {

    List<UserRepresentation> getAllUsersWithMemberRolesFromKeycloak();

    UserRepresentation createUserWithMemberRole(UserRepresentation userRepresentation);



}
