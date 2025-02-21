package com.ensa.miniproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class ProductOwner extends User{
    @id
    private Long id;
    @OneToMany
    List<Project> projects;
}
