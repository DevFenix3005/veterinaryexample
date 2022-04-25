package com.rebirth.veterinaryexample.app.web.resources;

import com.rebirth.veterinaryexample.app.services.PetDetailService;
import com.rebirth.veterinaryexample.app.services.dtos.petdetail.PetDetailBase;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController("petdetail-controller")
@RequestMapping(path = WebResourceConstants.API_PETDETAIL)
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class PetDetailResource extends BaseResource {

    private final PetDetailService petDetailService;

    @GetMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PetDetailBase.PetDetailDto>> get(@PathVariable("uuid") UUID uuid) {
        List<PetDetailBase.PetDetailDto> petDetailDto = this.petDetailService.readAllByPet(uuid);
        return ResponseEntity.ok(petDetailDto);
    }

    @PostMapping(path = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PetDetailBase.PetDetailDto> post(@RequestBody @Valid PetDetailBase.PetDetailCreate petDetailCreateReq) {
        PetDetailBase.PetDetailDto newPetDto = this.petDetailService.create(petDetailCreateReq);
        URI breedUri = this.generateUri(newPetDto.getPetUuid());
        return ResponseEntity.created(breedUri).body(newPetDto);
    }

}
