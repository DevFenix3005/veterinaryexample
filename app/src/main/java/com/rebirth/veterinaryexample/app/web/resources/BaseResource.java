package com.rebirth.veterinaryexample.app.web.resources;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public class BaseResource {

    protected URI generateUri(UUID uuid) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{uuid}")
                .build(uuid);
    }

}
