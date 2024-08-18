package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.models.pet.*;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pets")
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private PetType type;
    @Column
    private String name;

    @Column
    private long ownerId;

    @Column
    private LocalDate birthDate;

    @Column
    private String notes;

    @ManyToOne(optional = true,cascade = CascadeType.ALL)
    private CustomerEntity customer;

    @ManyToMany(mappedBy = "pets")
    private List<ScheduleEntity> schedules;

    public PetDTO toPetDTO(PetEntity petEntity) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(petEntity.getId());
        petDTO.setType(petEntity.getType());
        petDTO.setName(petEntity.getName());
        petDTO.setOwnerId(petEntity.getOwnerId());
        petDTO.setBirthDate(petEntity.getBirthDate());
        petDTO.setNotes(petEntity.getNotes());
        return petDTO;
    }
}