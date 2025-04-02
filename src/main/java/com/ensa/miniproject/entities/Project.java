package com.ensa.miniproject.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne
    private ProductBacklog productBacklog;
}