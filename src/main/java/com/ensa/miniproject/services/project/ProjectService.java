package com.ensa.miniproject.services.project;

import com.ensa.miniproject.DTO.EquipeDevelopementDTO;
import com.ensa.miniproject.DTO.ProductOwnerDTO;
import com.ensa.miniproject.DTO.ProjectDTO;
import com.ensa.miniproject.DTO.ScrumMasterDTO;

import java.util.List;

public interface ProjectService {
    ProjectDTO addProject(ProjectDTO projectDTO);
    ProjectDTO updateProject(ProjectDTO projectDTO);
    ProjectDTO getProjectById(Long id);
    List<ProjectDTO> getProjects();
    void deleteProject(Long id);

    ScrumMasterDTO addScrumMasterToProject(Long idProject,ScrumMasterDTO scrumMasterDTO);
    ProductOwnerDTO addProductOwnerToProject(Long idProject,ProductOwnerDTO productOwnerDTO);
    EquipeDevelopementDTO addEquipeDevelopementToProject(Long idProject,EquipeDevelopementDTO equipeDevelopementDTO);
}
