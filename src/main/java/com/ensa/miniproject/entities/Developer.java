package com.ensa.miniproject.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
public class Developer extends User{
    private String speciality;
    private int experienceYears;

    @ManyToOne
    @JsonBackReference
    private EquipeDevelopement equipe;
}
