package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.Project;

import java.time.LocalDate;

public record ProjectRespDTO(
        Long id,
        String nom,
        LocalDate dateDebut,
        LocalDate dateFin) {
    public static ProjectRespDTO toProjectRespDTO(Project project) {
        return new ProjectRespDTO(
                project.getId(),
                project.getNom(),
                project.getDateDebut(),
                project.getDateFin()
        );
    }
    public Project toEntity() {
        Project project = new Project();
        project.setId(id);
        project.setNom(nom);
        project.setDateDebut(dateDebut);
        project.setDateFin(dateFin);
        return project;

    }
}
