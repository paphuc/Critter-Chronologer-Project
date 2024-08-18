package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.entities.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Long> {
    @Query("SELECT p FROM PetEntity p WHERE p.customer.id = :id")
    List<PetEntity> findByCustomerId(Long id);

    @Query("SELECT p FROM PetEntity p WHERE p.id = :id")
    Optional<PetEntity> findById(Long id);
}
