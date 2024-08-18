package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.entities.ScheduleEntity;
import com.udacity.jdnd.course3.critter.models.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        ScheduleEntity savedDto = scheduleService.add(scheduleDTO);
        return savedDto.toScheduleDTO(savedDto);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleEntity> entities = scheduleService.findAll();
        List<ScheduleDTO> dtos = new ArrayList<>();
        for (ScheduleEntity entity : entities) {
            dtos.add(entity.toScheduleDTO(entity));
        }
        return dtos;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleEntity> entities = scheduleService.findByPets(petId);
        List<ScheduleDTO> dtos = new ArrayList<>();
        for (ScheduleEntity entity : entities) {
            dtos.add(entity.toScheduleDTO(entity));
        }
        return dtos;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleEntity> entities = scheduleService.findByEmployees(employeeId);
        List<ScheduleDTO> dtos = new ArrayList<>();
        for (ScheduleEntity entity : entities) {
            dtos.add(entity.toScheduleDTO(entity));
        }
        return dtos;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleEntity> entities = scheduleService.findByCustomer(customerId);
        List<ScheduleDTO> dtos = new ArrayList<>();
        for (ScheduleEntity entity : entities) {
            dtos.add(entity.toScheduleDTO(entity));
        }
        return dtos;
    }
}