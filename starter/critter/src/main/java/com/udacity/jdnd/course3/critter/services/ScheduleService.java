package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.CustomerEntity;
import com.udacity.jdnd.course3.critter.entities.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entities.PetEntity;
import com.udacity.jdnd.course3.critter.entities.ScheduleEntity;
import com.udacity.jdnd.course3.critter.models.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<ScheduleEntity> findByCustomer(Long id) {
        List<PetEntity> pets = petRepository.findByCustomerId(id);
        List<ScheduleEntity> listEntity = new ArrayList<>();
        for (PetEntity pet : pets) {
            listEntity.addAll(scheduleRepository.findByPets(pet));
        }
        return listEntity;
    }

    public List<ScheduleEntity> findByPets(Long id) {
        return scheduleRepository.findByPets(petRepository.getOne(id));
    }

    public List<ScheduleEntity> findByEmployees(Long id) {
        return scheduleRepository.findByEmployees(
                employeeRepository.getOne(id));
    }

    public ScheduleEntity add(ScheduleDTO dto) {
        ScheduleEntity entity = new ScheduleEntity();
        List<PetEntity> listPet = petRepository.findAllById(dto.getPetIds());
        List<CustomerEntity> listCustomers = customerRepository.findAllById(dto.getCustomerIds());
        List<EmployeeEntity> listEmployee = employeeRepository.findAllById(dto.getEmployeeIds());
        entity.setDate(dto.getDate());
        entity.setEmployees(listEmployee);
        entity.setPets(listPet);
        entity.setActivities(dto.getActivities());

        //update customers
        for(CustomerEntity customer: listCustomers) {
            if(customer.getSchedules() == null) customer.setSchedules(new ArrayList<>());
            customer.getSchedules().add(entity);
            customerRepository.save(customer);
        }
        //update pets
        for(PetEntity pet : listPet){
            if(pet.getSchedules() == null) pet.setSchedules(new ArrayList<>());
            pet.getSchedules().add(entity);
            petRepository.save(pet);
        }
        return scheduleRepository.save(entity);
    }

    public List<ScheduleEntity> findAll() {
        return scheduleRepository.findAll();
    }
}