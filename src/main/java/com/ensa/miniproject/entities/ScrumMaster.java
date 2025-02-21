package com.ensa.miniproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class ScrumMaster extends User{
    @Id
    private Long id;
    @OneToMany
    List<Project> projects; 
}
