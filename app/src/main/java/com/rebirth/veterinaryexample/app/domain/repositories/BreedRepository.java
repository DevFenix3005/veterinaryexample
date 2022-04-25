package com.rebirth.veterinaryexample.app.domain.repositories;

import com.rebirth.veterinaryexample.app.domain.models.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BreedRepository extends JpaRepository<Breed, Long> {

    Optional<Breed> findByUuid(UUID uuid);


}
