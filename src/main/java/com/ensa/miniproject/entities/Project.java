package com.ensa.miniproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Project {
    @Id
    private Long id;
    private String nom;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    @ManyToOne
    private ProductOwner owner;
    @ManyToOne
    private ScrumMaster scrumMaster;
    @ManyToOne
    private EquipeDevelopement equipeDevelopement;
    @OneToMany
    private List<ProductBacklog> productBacklogs;

}
