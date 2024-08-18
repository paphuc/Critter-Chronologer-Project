package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.CustomerEntity;
import com.udacity.jdnd.course3.critter.entities.PetEntity;
import com.udacity.jdnd.course3.critter.models.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<PetEntity> findAll() {
        return petRepository.findAll();
    }

    public PetEntity findById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    public PetEntity add(PetDTO dto) {
        PetEntity entity = new PetEntity();
        CustomerEntity customer = customerRepository.getOne(dto.getOwnerId());
        entity.setName(dto.getName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setType(dto.getType());
        entity.setNotes(dto.getNotes());
        entity.setOwnerId(customer.getId());

        entity.setCustomer(customer);
        entity = petRepository.save(entity);
        customer.getPets().add(entity);
        customerRepository.save(customer);
        return entity;
    }

    public List<PetEntity> findByOwnerId(Long ownerId) {
        return petRepository.findByCustomerId(ownerId);
    }
}