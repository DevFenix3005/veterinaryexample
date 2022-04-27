package com.rebirth.veterinaryexample.app.utils;

import com.rebirth.veterinaryexample.app.configuration.ConfigurationConstants;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class ServiceUtilComponent {

    @Value("${keycloak.resource}")
    private String resource;

    public boolean areYouAnAdmin(AccessToken accessToken) {
        AccessToken.Access res = accessToken.getResourceAccess().get(resource);
        Set<String> roles = res.getRoles();
        boolean isAdmin = false;
        for (String administrativeRole : ConfigurationConstants.ADMINISTRATIVE_ROLES) {
            if (roles.contains(administrativeRole)) {
                isAdmin = true;
                break;
            }
        }
        return isAdmin;
    }

    public String accessTokenAsSubjectUUID(AccessToken accessToken) {
        return accessToken.getSubject();
        //return UUID.fromString(subject);
    }

}
