package com.rebirth.veterinaryexample.app.services.mappers.utils;

import com.rebirth.veterinaryexample.app.domain.models.Pet;
import com.rebirth.veterinaryexample.app.domain.repositories.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class PetDetailMapperUtil {

    private final PetRepository petRepository;

    public Pet getPetByUuid(String uuid) {
        return petRepository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new RuntimeException("No se encontro el Pet con este UUID"));
    }

    public Pet getPetByUuid(String uuid, Pet currentPet) {
        if (uuid != null) return this.getPetByUuid(uuid);
        else return currentPet;
    }

}
