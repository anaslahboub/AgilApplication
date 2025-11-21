package com.ensa.miniproject.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Setter @Getter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
public class Developer extends User{
    private String speciality;
    private int experienceYears;

    @ManyToOne(optional = true)
    @JsonBackReference
    private EquipeDevelopement equipe;
}
