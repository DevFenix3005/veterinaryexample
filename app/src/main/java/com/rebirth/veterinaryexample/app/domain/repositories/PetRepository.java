package com.rebirth.veterinaryexample.app.domain.repositories;

import com.rebirth.veterinaryexample.app.domain.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("SELECT p0 FROM Pet p0 INNER JOIN FETCH p0.breed b0 WHERE p0.uuid = :uuid")
    Optional<Pet> findByUuid(UUID uuid);

    @Query("SELECT p0.petPic FROM Pet p0 WHERE p0.uuid = :uuid")
    Optional<String> findPetPicUUIDByUuid(@Param("uuid") UUID uuid);

    @Query("SELECT p0 FROM Pet p0 INNER JOIN FETCH p0.breed b0 WHERE p0.ownerId = :ownerId")
    List<Pet> findAllbyOwnerUUID(@Param("ownerId") String ownerId);

    @Query("SELECT p0 FROM Pet p0 INNER JOIN FETCH p0.breed b0")
    List<Pet> findAll();
}
