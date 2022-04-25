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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final TeedyService teedyService;

    @Value("${keycloak.resource}")
    private String resource;

    @Override
    public PetBase.PetDto create(PetBase.PetCreate petCreate) {
        Pet newPet = this.petMapper.petCreate2Pet(petCreate);
        DocumentCreate documentCreate = teedyService.createDocument(newPet);
        newPet.setPetDocument(documentCreate.getId());
        newPet = this.petRepository.save(newPet);
        return this.petMapper.pet2PetDto(newPet);
    }

    @Override
    public PetBase.PetDto create(PetBase.PetCreate petCreate, MultipartFile petPic) {

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
        return this.petMapper.pet2PetDto(newPet);
    }

    @Override
    public RawAndMeta getImageFromTeedy(UUID petUUID, String size) {
        Optional<String> optionalPet = this.petRepository.findPetPicUUIDByUuid(petUUID);
        return optionalPet.map((uuid)-> this.teedyService.getImage(uuid, size))
                .orElseThrow(() -> new PetNotFoundEx(petUUID));
    }

    @Override
    public Optional<PetBase.PetDto> read(UUID uuid) {
        return this.petRepository.findByUuid(uuid)
                .map(this.petMapper::pet2PetDto);
    }

    @Override
    public List<PetBase.PetDto> readAll() {
        throw new UnsupportedOperationException("No implementes this");
    }

    @Override
    public List<PetBase.PetDto> readAll(AccessToken token) {
        AccessToken.Access res = token.getResourceAccess().get(resource);
        Set<String> roles = res.getRoles();
        log.info("Roles asignados: {}", roles);
        if (roles.contains("Admin")) {
            return this.petRepository.findAll()
                    .stream()
                    .map(this.petMapper::pet2PetDto)
                    .collect(Collectors.toList());
        } else {
            String subject = token.getSubject();
            return this.petRepository.findAllbyOwnerUUID(subject)
                    .stream()
                    .map(this.petMapper::pet2PetDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Optional<PetBase.PetDto> update(UUID uuid, PetBase.PetUpdate petUpdate) {
        return this.petRepository.findByUuid(uuid)
                .map(pet -> {
                    this.petMapper.petUpdatePassValues2Pet(pet, petUpdate);
                    Pet updatePet = this.petRepository.save(pet);
                    return this.petMapper.pet2PetDto(updatePet);
                });
    }

    @Override
    public void delete(UUID uuid) {
        this.petRepository.findByUuid(uuid)
                .ifPresent(this.petRepository::delete);
    }
}
