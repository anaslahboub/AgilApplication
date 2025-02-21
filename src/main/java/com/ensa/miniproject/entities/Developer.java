package com.ensa.miniproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Developer extends User{
    @Id
    private Long id;
    @ManyToOne
    private EquipeDevelopement equipeDevelopement;
    @OneToMany
    List<Project> projects;
}
