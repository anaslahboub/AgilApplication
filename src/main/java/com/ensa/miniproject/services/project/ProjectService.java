package com.ensa.miniproject.services.project;

import com.ensa.miniproject.DTO.ProjectDTO;

import java.util.List;

public interface ProjectService {
    ProjectDTO addProject(ProjectDTO projectDTO);
    ProjectDTO updateProject(ProjectDTO projectDTO);
    ProjectDTO getProjectById(Long id);
    List<ProjectDTO> getProjects();
    void deleteProject(Long id);
}
