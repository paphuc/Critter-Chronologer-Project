package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.EmployeeEntity;
import com.udacity.jdnd.course3.critter.models.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.models.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public List<EmployeeEntity> findAll() {
        return employeeRepository.findAll();
    }

    public EmployeeEntity findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public EmployeeEntity add(EmployeeDTO dto) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setSkills(dto.getSkills());
        entity.setDaysAvailable(dto.getDaysAvailable());
        return employeeRepository.save(entity);
    }

    public List<EmployeeEntity> findByAvailability(Set<EmployeeSkill> skills, LocalDate date) {
        List<EmployeeEntity> availableEmployees = employeeRepository.findByDaysAvailableContaining(date.getDayOfWeek());
        return availableEmployees.stream()
                .filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }
}