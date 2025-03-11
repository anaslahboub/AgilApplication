package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.Project;

import java.time.LocalDate;

public record ProjectCreateDTO(
         String nom,
         LocalDate dateDebut,
         LocalDate dateFin) {

    public Project toEntity() {
        Project project = new Project();
        project.setNom(nom);
        project.setDateDebut(dateDebut);
        project.setDateFin(dateFin);
        return project;
    }
}
