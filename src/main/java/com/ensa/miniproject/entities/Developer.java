package com.ensa.miniproject.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Developer extends User{

    @ManyToOne
    @JsonBackReference
    private EquipeDevelopement equipeDevelopement;

    @ManyToMany
    private List<Project> projects;
}
