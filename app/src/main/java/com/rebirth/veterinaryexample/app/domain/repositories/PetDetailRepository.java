package com.rebirth.veterinaryexample.app.domain.repositories;

import com.rebirth.veterinaryexample.app.domain.models.PetDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PetDetailRepository extends CrudRepository<PetDetail, Long> {

    @Query("""
            SELECT d0
            FROM PetDetail d0 LEFT JOIN FETCH d0.pet p0
            WHERE p0.uuid=:petUUID
            order by d0.createAt Desc
            """)
    List<PetDetail> findByPetUuid(UUID petUUID);

    @Query("""
            SELECT d0
            FROM PetDetail d0 LEFT JOIN FETCH d0.pet p0
            WHERE p0.uuid=:petUUID and p0.ownerId=:ownerId
            order by d0.createAt Desc
            """)
    List<PetDetail> findByPetUuid(@Param("petUUID") UUID petUUID, @Param("ownerId") String ownerId);

}
