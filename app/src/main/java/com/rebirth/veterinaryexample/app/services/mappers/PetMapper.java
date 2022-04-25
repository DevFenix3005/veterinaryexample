package com.rebirth.veterinaryexample.app.services.mappers;

import com.rebirth.veterinaryexample.app.domain.models.Pet;
import com.rebirth.veterinaryexample.app.services.annotations.ToCreateEntity;
import com.rebirth.veterinaryexample.app.services.annotations.ToEntity;
import com.rebirth.veterinaryexample.app.services.dtos.pets.PetBase;
import com.rebirth.veterinaryexample.app.services.mappers.utils.PetMapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", uses = {PetMapperUtil.class})
public interface PetMapper {

    @ToCreateEntity()
    @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "petDocument", ignore = true)
    @Mapping(target = "petPic", ignore = true)
    @Mapping(target = "breed", source = "breedUuid")
    @Mapping(target = "detail", ignore = true)
    Pet petCreate2Pet(PetBase.PetCreate petCreate);

    @ToEntity()
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "petDocument", ignore = true)
    @Mapping(target = "petPic", ignore = true)
    @Mapping(target = "breed", expression = "java(petMapperUtil.getBreedByUUIDOrStayWithOld(petUpdate.getBreedUuid(), pet.getBreed()))")
    @Mapping(target = "detail", ignore = true)
    @Mapping(target = "name", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "gender", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "status", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "birthday", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "primaryColor", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "secundaryColor", nullValuePropertyMappingStrategy = IGNORE)
    void petUpdatePassValues2Pet(@MappingTarget Pet pet, PetBase.PetUpdate petUpdate);

    PetBase.PetDto pet2PetDto(Pet pet);

}
