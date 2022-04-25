package com.rebirth.veterinaryexample.app.web.resources;

import com.rebirth.veterinaryexample.app.services.BreedService;
import com.rebirth.veterinaryexample.app.services.dtos.breeds.BreedCreate;
import com.rebirth.veterinaryexample.app.services.dtos.breeds.BreedDto;
import com.rebirth.veterinaryexample.app.services.dtos.breeds.BreedUpdate;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController("breed-controller")
@RequestMapping(path = WebResourceConstants.API_BREED)
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class BreedResource extends BaseResource {

    private final BreedService breedService;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BreedDto>> get() {
        List<BreedDto> breeds = this.breedService.readAll();
        return ResponseEntity.ok(breeds);
    }

    @GetMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BreedDto> get(@PathVariable("uuid") UUID uuid) {
        Optional<BreedDto> breed = this.breedService.read(uuid);
        return breed.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BreedDto> post(@RequestBody @Valid BreedCreate breedDtoRequest) {
        BreedDto newBreed = this.breedService.create(breedDtoRequest);
        URI breedUri = this.generateUri(newBreed.getUuid());
        return ResponseEntity.created(breedUri).body(newBreed);
    }

    @PutMapping(path = "/{uuid}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BreedDto> put(@PathVariable("uuid") UUID uuid,
                                        @RequestBody @Valid BreedUpdate breedUpdate) {
        Optional<BreedDto> breed = this.breedService.update(uuid, breedUpdate);
        return breed.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("uuid") UUID uuid) {
        this.breedService.delete(uuid);
        return ResponseEntity.noContent().build();
    }


}
