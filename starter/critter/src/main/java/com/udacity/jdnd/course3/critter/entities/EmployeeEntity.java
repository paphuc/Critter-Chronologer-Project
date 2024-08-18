package com.udacity.jdnd.course3.critter.entities;
import lombok.Data;
import com.udacity.jdnd.course3.critter.models.user.*;
import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.*;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "employees")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ElementCollection
    private Set<EmployeeSkill> skills;

    @ElementCollection
    private Set<DayOfWeek> daysAvailable;

    public EmployeeDTO toEmployeeDTO(EmployeeEntity employeeEntity) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employeeEntity.getId());
        employeeDTO.setName(employeeEntity.getName());
        employeeDTO.setSkills(employeeEntity.getSkills());
        employeeDTO.setDaysAvailable(employeeEntity.getDaysAvailable());
        return employeeDTO;
    }
}