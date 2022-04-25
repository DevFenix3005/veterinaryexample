package com.rebirth.veterinaryexample.app.services;

import com.rebirth.veterinaryexample.app.services.dtos.petdetail.PetDetailBase;

import java.util.List;
import java.util.UUID;

public interface PetDetailService
        extends BaseService<PetDetailBase.PetDetailCreate, PetDetailBase.PetDetailUpdate, PetDetailBase.PetDetailDto, UUID> {

    List<PetDetailBase.PetDetailDto> readAllByPet(UUID uuid);

}
