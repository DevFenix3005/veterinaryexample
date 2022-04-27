package com.rebirth.veterinaryexample.app.services;

import com.rebirth.veterinaryexample.app.domain.models.Pet;
import com.rebirth.veterinaryexample.app.services.dtos.pets.PetBase;
import com.rebirth.veterinaryexample.app.services.dtos.teedy.DocumentCreate;
import com.rebirth.veterinaryexample.app.services.dtos.teedy.RawAndMeta;
import org.keycloak.representations.AccessToken;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface PetService extends BaseService<PetBase.PetCreate, PetBase.PetUpdate, PetBase.PetDto, UUID> {

    PetBase.PetDto create(PetBase.PetCreate petCreate, MultipartFile petPic, AccessToken accessToken);

    RawAndMeta getImageFromTeedy(UUID petUUID, String size);

    List<PetBase.PetDto> readAll(AccessToken token);
}
