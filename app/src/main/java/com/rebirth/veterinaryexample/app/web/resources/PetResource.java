package com.rebirth.veterinaryexample.app.web.resources;

import com.rebirth.veterinaryexample.app.services.PetDetailService;
import com.rebirth.veterinaryexample.app.services.PetService;
import com.rebirth.veterinaryexample.app.services.dtos.petdetail.PetDetailBase;
import com.rebirth.veterinaryexample.app.services.dtos.pets.PetBase;
import lombok.AllArgsConstructor;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<List<PetBase.PetDto>> get() {
        AccessToken token = getAccessToken();
        List<PetBase.PetDto> pets = this.petService.readAll(token);
        return ResponseEntity.ok(pets);
    }

    @GetMapping(path = "/{uuid}/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PetDetailBase.PetDetailDto>> getDetails(@PathVariable("uuid") UUID uuid) {
        AccessToken token = getAccessToken();
        List<PetDetailBase.PetDetailDto> petDetail = this.petDetailService.readAllByPet(uuid, token);
        return ResponseEntity.ok(petDetail);
    }

    @GetMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PetBase.PetDto> get(@PathVariable("uuid") UUID uuid) {
        AccessToken token = getAccessToken();
        Optional<PetBase.PetDto> breed = this.petService.read(uuid, token);
        return breed.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{uuid}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PetBase.PetDto> put(@PathVariable("uuid") UUID uuid,
                                              @RequestBody @Valid PetBase.PetUpdate petUpdate) {
        AccessToken token = getAccessToken();
        Optional<PetBase.PetDto> breed = this.petService.update(uuid, petUpdate, token);
        return breed.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("uuid") UUID uuid) {
        AccessToken token = getAccessToken();
        this.petService.delete(uuid, token);
        return ResponseEntity.noContent().build();
    }


}
