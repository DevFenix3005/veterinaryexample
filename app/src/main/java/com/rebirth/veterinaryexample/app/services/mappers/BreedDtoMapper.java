package com.rebirth.veterinaryexample.app.services.mappers;

import com.rebirth.veterinaryexample.app.domain.models.Breed;
import com.rebirth.veterinaryexample.app.services.annotations.ToCreateEntity;
import com.rebirth.veterinaryexample.app.services.annotations.ToEntity;
import com.rebirth.veterinaryexample.app.services.dtos.breeds.BreedCreate;
import com.rebirth.veterinaryexample.app.services.dtos.breeds.BreedDto;
import com.rebirth.veterinaryexample.app.services.dtos.breeds.BreedUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface BreedDtoMapper {

    @ToCreateEntity()
    @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "size", defaultValue = "Medium")
    @Mapping(target = "pets", ignore = true)
    @Mapping(target = "createBy",ignore = true)
    Breed breedCreateToBreed(BreedCreate breedCreate);

    @ToEntity()
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "name", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "size", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "description", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "pets", ignore = true)
    @Mapping(target = "createBy",ignore = true)
    void breedUpdateToBreed(@MappingTarget Breed breed, BreedUpdate breedCreate);

    BreedDto breedToBreedDto(Breed breed);

}
