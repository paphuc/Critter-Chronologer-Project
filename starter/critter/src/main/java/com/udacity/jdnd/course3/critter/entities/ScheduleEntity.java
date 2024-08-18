package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.models.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.models.user.EmployeeSkill;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "schedules")
public class ScheduleEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "schedules_employees", joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<EmployeeEntity> employees;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "schedules_pets",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id")
    )
    private List<PetEntity> pets;

    @Column
    private LocalDate date;

    @ElementCollection
    private Set<EmployeeSkill> activities;

    public ScheduleDTO toScheduleDTO(ScheduleEntity entity) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setEmployeeIds(entity.getEmployees().stream()
                .map(EmployeeEntity::getId)
                .collect(Collectors.toList()));
        dto.setPetIds(entity.getPets().stream()
                .map(PetEntity::getId)
                .collect(Collectors.toList()));
        dto.setActivities(entity.getActivities());
        return dto;
    }
}