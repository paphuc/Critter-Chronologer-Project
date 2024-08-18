package com.udacity.jdnd.course3.critter.controllers;
import com.udacity.jdnd.course3.critter.models.user.*;
import com.udacity.jdnd.course3.critter.entities.EmployeeEntity;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/employee")
    public EmployeeDTO saveCustomer(@RequestBody EmployeeDTO dto) {
        EmployeeEntity savedDto = employeeService.add(dto);
        return savedDto.toEmployeeDTO(savedDto);
    }

    @GetMapping("/employee")
    public List<EmployeeDTO> getAllEmployee() {
        List<EmployeeEntity> entities = employeeService.findAll();
        List<EmployeeDTO> dtos = new ArrayList<>();
        for (EmployeeEntity entity : entities) {
            dtos.add(entity.toEmployeeDTO(entity));
        }
        return dtos;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        EmployeeEntity entity = employeeService.findById(employeeId);
        EmployeeDTO dto = entity.toEmployeeDTO(entity);
        dto.setDaysAvailable(daysAvailable);
        employeeService.add(dto);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<EmployeeEntity> entities = employeeService.findByAvailability(employeeDTO.getSkills(), employeeDTO.getDate());
        List<EmployeeDTO> dtos = new ArrayList<>();
        for (EmployeeEntity entity : entities) {
            dtos.add(entity.toEmployeeDTO(entity));
        }
        return dtos;
    }
}