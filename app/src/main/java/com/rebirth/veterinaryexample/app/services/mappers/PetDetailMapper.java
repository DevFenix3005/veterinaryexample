package com.rebirth.veterinaryexample.app.services.mappers;

import com.rebirth.veterinaryexample.app.domain.models.PetDetail;
import com.rebirth.veterinaryexample.app.services.annotations.ToCreateEntity;
import com.rebirth.veterinaryexample.app.services.annotations.ToEntity;
import com.rebirth.veterinaryexample.app.services.dtos.petdetail.PetDetailBase;
import com.rebirth.veterinaryexample.app.services.mappers.utils.PetDetailMapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", uses = {PetDetailMapperUtil.class})
public interface PetDetailMapper {

    @ToCreateEntity()
    @Mapping(target = "pet", source = "petUuid")
    @Mapping(target = "createBy", ignore = true)
    PetDetail petDetailCreate2PetDetail(PetDetailBase.PetDetailCreate petDetailCreate);

    @ToEntity()
    @Mapping(target = "pet", expression = "java(petDetailMapperUtil.getPetByUuid(petDetailUpdate.getPetUuid(), petDetail.getPet()))")
    @Mapping(target = "weight", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "height", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "details", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "createBy", ignore = true)
    void petDetailUpdatePassValues2PetDetail(@MappingTarget PetDetail petDetail, PetDetailBase.PetDetailUpdate petDetailUpdate);

    @Mapping(target = "picUrl", source = "pet")
    @Mapping(target = "petUuid", source = "pet.uuid")
    @Mapping(target = "createAt", source = "createAt")
    PetDetailBase.PetDetailDto petDetail2PetilDetailDto(PetDetail petDetail);

}
