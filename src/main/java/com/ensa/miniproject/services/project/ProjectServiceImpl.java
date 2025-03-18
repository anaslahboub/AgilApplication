package com.ensa.miniproject.services.project;

import com.ensa.miniproject.DTO.ProjectDTO;
import com.ensa.miniproject.entities.Project;
import com.ensa.miniproject.execptions.InvalidDateException;
import com.ensa.miniproject.mapping.ProjectMapper;
import com.ensa.miniproject.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectDTO addProject(ProjectDTO projectDTO) {
        if (projectDTO.getDateDebut().isAfter(projectDTO.getDateFin())) {
            throw new InvalidDateException("La date de début doit être inférieure à la date de fin.");
        }
        Project project = projectMapper.toEntity(projectDTO);
        project = projectRepository.save(project);
        return projectMapper.fromEntity(project);
    }

    @Override
    public ProjectDTO updateProject(ProjectDTO projectDTO) {
        if (projectDTO.getDateDebut().isAfter(projectDTO.getDateFin())) {
            throw new InvalidDateException("La date de début doit être inférieure à la date de fin.");
        }
        Project project = projectMapper.toEntity(projectDTO);
        project = projectRepository.save(project);
        return projectMapper.fromEntity(project);
    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projet avec ID " + id + " non trouvé"));
        return projectMapper.fromEntity(project);
    }

    @Override
    public List<ProjectDTO> getProjects() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

}
