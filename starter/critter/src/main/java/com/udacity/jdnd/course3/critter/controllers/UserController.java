package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.entities.*;
import com.udacity.jdnd.course3.critter.models.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.models.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.models.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private CustomerService customerService;

    @Autowired
    private PetService petService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO dto) {
        CustomerEntity savedDto = customerService.add(dto);
        return savedDto.toCustomerDTO(savedDto);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerEntity> entities = customerService.findAll();
        List<CustomerDTO> dtos = new ArrayList<>();
        for (CustomerEntity entity : entities) {
            dtos.add(entity.toCustomerDTO(entity));
        }
        return dtos;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        CustomerEntity customerEntity = customerService.findByPetId(petId);
        return customerEntity.toCustomerDTO(customerEntity);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeEntity savedEntity = employeeService.add(employeeDTO);
        return savedEntity.toEmployeeDTO(savedEntity);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        EmployeeEntity entity = employeeService.findById(employeeId);
        return entity.toEmployeeDTO(entity);
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