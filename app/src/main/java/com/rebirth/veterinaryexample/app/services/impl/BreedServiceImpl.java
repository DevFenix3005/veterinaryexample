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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    @Cacheable(value = "breeds", key = "#uuid.toString()")
    public Optional<BreedDto> read(UUID uuid) {
        return breedRepository.findByUuid(uuid)
                .map(this.breedDtoMapper::breedToBreedDto);
    }

    @Override
    @Cacheable(value = "listOfbreeds")
    public List<BreedDto> readAll() {
        return this.breedRepository.findAll()
                .stream()
                .map(this.breedDtoMapper::breedToBreedDto)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "listOfbreeds", allEntries = true)
    public BreedDto create(BreedCreate breedCreate) {
        Breed newBreed = this.breedDtoMapper.breedCreateToBreed(breedCreate);
        newBreed = this.breedRepository.save(newBreed);
        return this.breedDtoMapper.breedToBreedDto(newBreed);
    }

    @Caching(evict = @CacheEvict(value = "listOfbreeds", allEntries = true),
            put = @CachePut(value = "breeds", key = "#uuid.toString()")
    )
    @Override
    public Optional<BreedDto> update(UUID uuid, BreedUpdate breedUpdate) {
        return breedRepository.findByUuid(uuid)
                .map(breed -> {
                    this.breedDtoMapper.breedUpdateToBreed(breed, breedUpdate);
                    Breed updatedBreed = this.breedRepository.saveAndFlush(breed);
                    return this.breedDtoMapper.breedToBreedDto(updatedBreed);
                });
    }

    @Caching(evict = {
            @CacheEvict(value = "listOfbreeds", allEntries = true),
            @CacheEvict(value = "breeds", key = "#uuid.toString()"),
    })
    @Override
    public void delete(UUID uuid) {
        breedRepository.findByUuid(uuid)
                .ifPresent(this.breedRepository::delete);
    }

}
