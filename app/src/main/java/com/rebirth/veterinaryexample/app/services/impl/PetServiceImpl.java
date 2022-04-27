package com.rebirth.veterinaryexample.app.services.impl;

import com.rebirth.veterinaryexample.app.domain.models.Pet;
import com.rebirth.veterinaryexample.app.domain.repositories.PetRepository;
import com.rebirth.veterinaryexample.app.exceptions.PetNotFoundEx;
import com.rebirth.veterinaryexample.app.services.PetService;
import com.rebirth.veterinaryexample.app.services.TeedyService;
import com.rebirth.veterinaryexample.app.services.dtos.pets.PetBase;
import com.rebirth.veterinaryexample.app.services.dtos.teedy.DocumentCreate;
import com.rebirth.veterinaryexample.app.services.dtos.teedy.FileCreate;
import com.rebirth.veterinaryexample.app.services.dtos.teedy.RawAndMeta;
import com.rebirth.veterinaryexample.app.services.mappers.PetMapper;
import com.rebirth.veterinaryexample.app.utils.ServiceUtilComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final TeedyService teedyService;
    private final ServiceUtilComponent serviceUtilComponent;

    private final CacheManager cacheManager;

    @Override
    @CacheEvict(value = "listOfPets", allEntries = true)
    public PetBase.PetDto create(PetBase.PetCreate petCreate, MultipartFile petPic, AccessToken accessToken) {
        Pet newPet = this.petMapper.petCreate2Pet(petCreate);
        DocumentCreate documentCreate = teedyService.createDocument(newPet);
        newPet.setPetDocument(documentCreate.getId());

        try {
            FileCreate fileImage = teedyService.appendImage2Document(documentCreate, petPic);
            newPet.setPetPic(fileImage.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        newPet = this.petRepository.save(newPet);
        var cache = cacheManager.getCache("pets");
        if (cache != null) {
            cache.put(newPet.getUuid().toString(), newPet);
        }
        return this.petMapper.pet2PetDto(newPet);
    }

    @Override
    public RawAndMeta getImageFromTeedy(UUID petUUID, String size) {
        Optional<String> optionalPet = this.petRepository.findPetPicUUIDByUuid(petUUID);
        return optionalPet.map((uuid) -> this.teedyService.getImage(uuid, size))
                .orElseThrow(() -> new PetNotFoundEx(petUUID));
    }

    @Override
    @Cacheable(value = "pets", key = "#uuid.toString()")
    public Optional<PetBase.PetDto> read(UUID uuid, AccessToken accessToken) {
        if (this.serviceUtilComponent.areYouAnAdmin(accessToken)) {
            return this.petRepository.findByUuid(uuid)
                    .map(this.petMapper::pet2PetDto);
        } else {
            var ownerUUID = this.serviceUtilComponent.accessTokenAsSubjectUUID(accessToken);
            return this.petRepository.findByUuid(uuid, ownerUUID)
                    .map(this.petMapper::pet2PetDto);
        }
    }

    @Override
    @Cacheable(value = "listOfPets", key = "#accessToken.getSubject()")
    public List<PetBase.PetDto> readAll(AccessToken accessToken) {
        if (this.serviceUtilComponent.areYouAnAdmin(accessToken)) {
            return this.petRepository.findAll()
                    .stream()
                    .map(this.petMapper::pet2PetDto)
                    .collect(Collectors.toList());
        } else {
            var ownerUUID = this.serviceUtilComponent.accessTokenAsSubjectUUID(accessToken);
            return this.petRepository.findAll(ownerUUID)
                    .stream()
                    .map(this.petMapper::pet2PetDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    @Caching(
            evict = @CacheEvict(value = "listOfPets", key = "#accessToken.getSubject()"),
            put = @CachePut(value = "pets", key = "#uuid.toString()")
    )
    public Optional<PetBase.PetDto> update(UUID uuid, PetBase.PetUpdate petUpdate, AccessToken accessToken) {
        if (this.serviceUtilComponent.areYouAnAdmin(accessToken)) {
            return this.petRepository.findByUuid(uuid)
                    .map(pet -> {
                        this.petMapper.petUpdatePassValues2Pet(pet, petUpdate);
                        Pet updatePet = this.petRepository.save(pet);
                        return this.petMapper.pet2PetDto(updatePet);
                    });
        } else {
            var ownerUUID = this.serviceUtilComponent.accessTokenAsSubjectUUID(accessToken);
            return this.petRepository.findByUuid(uuid, ownerUUID)
                    .map(pet -> {
                        this.petMapper.petUpdatePassValues2Pet(pet, petUpdate);
                        Pet updatePet = this.petRepository.save(pet);
                        return this.petMapper.pet2PetDto(updatePet);
                    });
        }
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "pets", key = "#uuid.toString()"),
                    @CacheEvict(value = "listOfPets", key = "#accessToken.getSubject()")
            }
    )
    public void delete(UUID uuid, AccessToken accessToken) {
        if (this.serviceUtilComponent.areYouAnAdmin(accessToken)) {
            this.petRepository.findByUuid(uuid)
                    .ifPresent(this.petRepository::delete);
        } else {
            var ownerUUID = this.serviceUtilComponent.accessTokenAsSubjectUUID(accessToken);
            this.petRepository.findByUuid(uuid, ownerUUID)
                    .ifPresent(this.petRepository::delete);
        }
    }


}
