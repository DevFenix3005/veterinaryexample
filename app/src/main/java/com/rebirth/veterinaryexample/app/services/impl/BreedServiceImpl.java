package com.rebirth.veterinaryexample.app.services.impl;

import com.rebirth.veterinaryexample.app.domain.models.Breed;
import com.rebirth.veterinaryexample.app.domain.repositories.BreedRepository;
import com.rebirth.veterinaryexample.app.services.BreedService;
import com.rebirth.veterinaryexample.app.services.dtos.breeds.BreedCreate;
import com.rebirth.veterinaryexample.app.services.dtos.breeds.BreedDto;
import com.rebirth.veterinaryexample.app.services.dtos.breeds.BreedUpdate;
import com.rebirth.veterinaryexample.app.services.mappers.BreedDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class BreedServiceImpl implements BreedService {

    private final BreedRepository breedRepository;
    private final BreedDtoMapper breedDtoMapper;

    @Override
    public Optional<BreedDto> read(UUID uuid) {
        return breedRepository.findByUuid(uuid)
                .map(this.breedDtoMapper::breedToBreedDto);
    }

    @Override
    public List<BreedDto> readAll() {
        return this.breedRepository.findAll()
                .stream()
                .map(this.breedDtoMapper::breedToBreedDto)
                .collect(Collectors.toList());
    }

    @Override
    public BreedDto create(BreedCreate breedCreate) {
        Breed newBreed = this.breedDtoMapper.breedCreateToBreed(breedCreate);
        newBreed = this.breedRepository.save(newBreed);
        return this.breedDtoMapper.breedToBreedDto(newBreed);
    }

    @Override
    public Optional<BreedDto> update(UUID uuid, BreedUpdate breedUpdate) {
        return breedRepository.findByUuid(uuid)
                .map(breed -> {
                    this.breedDtoMapper.breedUpdateToBreed(breed, breedUpdate);
                    Breed updatedBreed = this.breedRepository.saveAndFlush(breed);
                    return this.breedDtoMapper.breedToBreedDto(updatedBreed);
                });
    }

    @Override
    public void delete(UUID uuid) {
        breedRepository.findByUuid(uuid)
                .ifPresent(this.breedRepository::delete);
    }

}
