package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.models.user.CustomerDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String phoneNumber;

    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<PetEntity> pets;

    public CustomerDTO toCustomerDTO(CustomerEntity entity) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setNotes(entity.getNotes());
        List<Long> petIds = entity.getPets().stream()
                .map(PetEntity::getId)
                .collect(Collectors.toList());
        dto.setPetIds(petIds);
        return dto;
    }
}