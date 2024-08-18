package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.entities.PetEntity;
import com.udacity.jdnd.course3.critter.models.pet.PetDTO;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        PetEntity savedDto = petService.add(petDTO);
        return savedDto.toPetDTO(savedDto);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        PetEntity entity = petService.findById(petId);
        return entity.toPetDTO(entity);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetEntity> entities = petService.findByOwnerId(ownerId);
        List<PetDTO> dtos = new ArrayList<>();
        for (PetEntity entity : entities) {
            dtos.add(entity.toPetDTO(entity));
        }
        return dtos;
    }
}