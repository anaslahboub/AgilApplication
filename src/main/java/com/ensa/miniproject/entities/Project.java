package com.ensa.miniproject.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter

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
    @JsonManagedReference
    private List<ProductBacklog> productBacklogs;

}
