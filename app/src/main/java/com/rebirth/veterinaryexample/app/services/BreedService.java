package com.rebirth.veterinaryexample.app.services;

import com.rebirth.veterinaryexample.app.services.dtos.breeds.BreedCreate;
import com.rebirth.veterinaryexample.app.services.dtos.breeds.BreedDto;
import com.rebirth.veterinaryexample.app.services.dtos.breeds.BreedUpdate;

import java.util.UUID;

public interface BreedService extends BaseService<BreedCreate, BreedUpdate, BreedDto, UUID> {


}
