package com.rebirth.veterinaryexample.app.web.resources;

import com.rebirth.veterinaryexample.app.services.KeycloakAdminService;
import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rebirth.veterinaryexample.app.web.resources.WebResourceConstants.API_USER;

@RestController("adminuser-resource")
@RequestMapping(API_USER)
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class AdminUserResource extends BaseResource {

    private final KeycloakAdminService keycloakAdminService;

    @GetMapping("/")
    public List<UserRepresentation> getAllUsers() {
        return keycloakAdminService.getAllUsersWithMemberRolesFromKeycloak();
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRepresentation postUser(@RequestBody UserRepresentation userRepresentation) {
        return keycloakAdminService.createUserWithMemberRole(userRepresentation);
    }


}
