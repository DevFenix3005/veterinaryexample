package com.rebirth.veterinaryexample.app.services.impl;

import com.rebirth.veterinaryexample.app.domain.models.PetDetail;
import com.rebirth.veterinaryexample.app.domain.repositories.PetDetailRepository;
import com.rebirth.veterinaryexample.app.services.PetDetailService;
import com.rebirth.veterinaryexample.app.services.dtos.petdetail.PetDetailBase;
import com.rebirth.veterinaryexample.app.services.mappers.PetDetailMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class PetDetailServiceImpl implements PetDetailService {

    private final PetDetailRepository petDetailRepository;
    private final PetDetailMapper petDetailMapper;

    @Override
    public PetDetailBase.PetDetailDto create(PetDetailBase.PetDetailCreate petDetailCreate) {
        PetDetail newPetDetail = this.petDetailMapper.petDetailCreate2PetDetail(petDetailCreate);
        newPetDetail = this.petDetailRepository.save(newPetDetail);
        return this.petDetailMapper.petDetail2PetilDetailDto(newPetDetail);
    }

    public List<PetDetailBase.PetDetailDto> readAllByPet(UUID uuid) {
        return this.petDetailRepository.findByPetUuid(uuid)
                .stream()
                .map(this.petDetailMapper::petDetail2PetilDetailDto)
                .collect(Collectors.toList());

    }


}
