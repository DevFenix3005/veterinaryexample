package com.rebirth.veterinaryexample.app.services.mappers.utils;

import com.rebirth.veterinaryexample.app.domain.models.Breed;
import com.rebirth.veterinaryexample.app.domain.repositories.BreedRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class PetMapperUtil {

    private final BreedRepository breedRepository;

    public Breed getBreedByUUID(String uuid) {
        Optional<Breed> opBreed = breedRepository.findByUuid(UUID.fromString(uuid));
        if (opBreed.isPresent()) {
            return opBreed.get();
        } else {
            throw new RuntimeException("Breed Not Found by UUID");
        }
    }

    public Breed getBreedByUUIDOrStayWithOld(String uuid, Breed breed) {
        if (uuid == null) return breed;
        else return this.getBreedByUUID(uuid);
    }

    public String getBreedName(Breed breed) {
        return breed.getName();
    }

    public UUID string2UUID(String uuid) {
        return UUID.fromString(uuid);
    }

    public String UUID2String(UUID uuid) {
        return uuid.toString();
    }

}
