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

    @ManyToOne(optional = true)
    private ProductOwner owner;

    @ManyToOne(optional = true)
    private ScrumMaster scrumMaster;

    @ManyToOne(optional = true)
    private EquipeDevelopement equipeDevelopement;

    @OneToOne(optional = true)
    private ProductBacklog productBacklog;
}