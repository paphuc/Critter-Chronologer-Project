package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.CustomerEntity;
import com.udacity.jdnd.course3.critter.entities.PetEntity;
import com.udacity.jdnd.course3.critter.models.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    public List<CustomerEntity> findAll() {
        return customerRepository.findAll();
    }

    public CustomerEntity add(CustomerDTO dto) {
        CustomerEntity entity = new CustomerEntity();
        List<PetEntity> petList = new ArrayList<>();
        List<Long> petId = dto.getPetIds();
        if (!CollectionUtils.isEmpty(petId)) {
            petId.forEach(e -> {
                Optional<PetEntity> petEntity = petRepository.findById(e);
                petEntity.ifPresent(petList::add);
            });
        }
        entity.setName(dto.getName());
        entity.setNotes(dto.getNotes());
        entity.setPets(petList);
        entity.setPhoneNumber(dto.getPhoneNumber());
        return customerRepository.save(entity);
    }

    public CustomerEntity findByPetId(Long petId) {
        return petRepository.getOne(petId).getCustomer();
    }
}