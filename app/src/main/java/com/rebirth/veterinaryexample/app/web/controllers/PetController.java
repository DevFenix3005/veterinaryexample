package com.rebirth.veterinaryexample.app.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebirth.veterinaryexample.app.exceptions.PetCreateEx;
import com.rebirth.veterinaryexample.app.services.PetService;
import com.rebirth.veterinaryexample.app.services.dtos.pets.PetBase;
import com.rebirth.veterinaryexample.app.services.dtos.teedy.RawAndMeta;
import com.rebirth.veterinaryexample.app.web.resources.BaseResource;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@Controller("pet-controller")
@RequestMapping(WebControllersContants.API_PETHELPER)
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class PetController extends BaseResource {

    private final PetService petService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @PostMapping(path = "/",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PetBase.PetDto> post(
            @Valid @ModelAttribute("petCreate") PetBase.PetCreate petCreateRequest,
            BindingResult bindingResult,
            @RequestPart(name = "pic") MultipartFile file
    ) {
        if (bindingResult.hasErrors()) {
            throw new PetCreateEx(bindingResult.getFieldErrors());
        } else {
            //PetBase.PetCreate petCreateRequest = objectMapper.readValue(body, PetBase.PetCreate.class);
            PetBase.PetDto newPetDto = this.petService.create(petCreateRequest, file);
            URI breedUri = this.generateUri(newPetDto.getUuid());
            return ResponseEntity.created(breedUri).body(newPetDto);
        }
    }

    @GetMapping(path = "/{uuid}/pic")
    public ResponseEntity<byte[]> getPic(@PathVariable("uuid") UUID uuid,
                                         @RequestParam(name = "size", defaultValue = "thumb") String size) {
        RawAndMeta picture = this.petService.getImageFromTeedy(uuid, size);
        String mime = picture.getMetadata().getMimetype();
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mime))
                .body(picture.getContent());
    }


}
