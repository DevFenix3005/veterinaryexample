package com.rebirth.veterinaryexample.app.web.resources;

import com.rebirth.veterinaryexample.app.services.PetDetailService;
import com.rebirth.veterinaryexample.app.services.PetService;
import com.rebirth.veterinaryexample.app.services.dtos.petdetail.PetDetailBase;
import com.rebirth.veterinaryexample.app.services.dtos.pets.PetBase;
import lombok.AllArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController("pet-resource")
@AllArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping(path = WebResourceConstants.API_PET)
public class PetResource extends BaseResource {

    private final PetService petService;
    private final PetDetailService petDetailService;


    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PetBase.PetDto>> get(Principal principal) {
        KeycloakAuthenticationToken keycloakPrincipal = (KeycloakAuthenticationToken) principal;
        AccessToken token = keycloakPrincipal.getAccount().getKeycloakSecurityContext().getToken();
        List<PetBase.PetDto> pets = this.petService.readAll(token);
        return ResponseEntity.ok(pets);
    }

    @GetMapping(path = "/{uuid}/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PetDetailBase.PetDetailDto> getDetail(@PathVariable("uuid") UUID uuid) {
        Optional<PetDetailBase.PetDetailDto> petDetail = this.petDetailService.read(uuid);
        return petDetail.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PetBase.PetDto> get(@PathVariable("uuid") UUID uuid) {
        Optional<PetBase.PetDto> breed = this.petService.read(uuid);
        return breed.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{uuid}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PetBase.PetDto> put(@PathVariable("uuid") UUID uuid,
                                              @RequestBody @Valid PetBase.PetUpdate petUpdate) {
        Optional<PetBase.PetDto> breed = this.petService.update(uuid, petUpdate);
        return breed.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("uuid") UUID uuid) {
        this.petService.delete(uuid);
        return ResponseEntity.noContent().build();
    }


}
