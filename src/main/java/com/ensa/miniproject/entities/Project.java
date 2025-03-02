package com.ensa.miniproject.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @ManyToOne
    @JsonManagedReference
    private ProductOwner owner;

    @ManyToOne
    @JsonManagedReference
    private ScrumMaster scrumMaster;

    @ManyToOne
    @JsonManagedReference
    private EquipeDevelopement equipeDevelopement;

    @OneToMany
    private List<ProductBacklog> productBacklogs;

    @OneToOne
    private ProductBacklog productBacklog;
}