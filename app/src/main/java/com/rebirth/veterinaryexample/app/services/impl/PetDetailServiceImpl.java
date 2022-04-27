package com.rebirth.veterinaryexample.app.services.impl;

import com.rebirth.veterinaryexample.app.domain.models.PetDetail;
import com.rebirth.veterinaryexample.app.domain.repositories.PetDetailRepository;
import com.rebirth.veterinaryexample.app.services.PetDetailService;
import com.rebirth.veterinaryexample.app.services.dtos.petdetail.PetDetailBase;
import com.rebirth.veterinaryexample.app.services.mappers.PetDetailMapper;
import com.rebirth.veterinaryexample.app.utils.ServiceUtilComponent;
import lombok.AllArgsConstructor;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class PetDetailServiceImpl implements PetDetailService {

    private final PetDetailRepository petDetailRepository;
    private final PetDetailMapper petDetailMapper;

    private final ServiceUtilComponent serviceUtilComponent;

    @Override
    @CacheEvict(value = "details", key = "#petDetailCreate.getPetUuid")
    public PetDetailBase.PetDetailDto create(PetDetailBase.PetDetailCreate petDetailCreate) {
        PetDetail newPetDetail = this.petDetailMapper.petDetailCreate2PetDetail(petDetailCreate);
        newPetDetail = this.petDetailRepository.save(newPetDetail);
        return this.petDetailMapper.petDetail2PetilDetailDto(newPetDetail);
    }

    @Cacheable(value = "details", key = "#uuid.toString()")
    public List<PetDetailBase.PetDetailDto> readAllByPet(UUID uuid, AccessToken accessToken) {
        if (this.serviceUtilComponent.areYouAnAdmin(accessToken)) {
            return this.petDetailRepository.findByPetUuid(uuid)
                    .stream()
                    .map(this.petDetailMapper::petDetail2PetilDetailDto)
                    .collect(Collectors.toList());
        } else {
            var ownerId = this.serviceUtilComponent.accessTokenAsSubjectUUID(accessToken);
            return this.petDetailRepository.findByPetUuid(uuid, ownerId)
                    .stream()
                    .map(this.petDetailMapper::petDetail2PetilDetailDto)
                    .collect(Collectors.toList());
        }
    }


}
