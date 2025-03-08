package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProjectDTO {
    private Long id;
    private String nom;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private ProductOwner owner;
    private ScrumMaster scrumMaster;
    private EquipeDevelopement equipeDevelopement;
    private ProductBacklog productBacklog;


    public Project toEntity(){
        Project project = new Project();
        project.setId(id);
        project.setNom(nom);
        project.setDateDebut(dateDebut);
        project.setDateFin(dateFin);
        project.setOwner(owner);
        project.setScrumMaster(scrumMaster);
        project.setProductBacklog(productBacklog);
        project.setEquipeDevelopement(equipeDevelopement);
        return project;
    }
    public static ProjectDTO fromEntity(Project project){
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setNom(project.getNom());
        projectDTO.setDateDebut(project.getDateDebut());
        projectDTO.setDateFin(project.getDateFin());
        projectDTO.setOwner(project.getOwner());
        projectDTO.setScrumMaster(project.getScrumMaster());
        projectDTO.setProductBacklog(project.getProductBacklog());
        projectDTO.setEquipeDevelopement(project.getEquipeDevelopement());
        return projectDTO;
    }
}
