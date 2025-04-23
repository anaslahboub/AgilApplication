package com.ensa.miniproject.services.project;

import com.ensa.miniproject.DTO.EquipeDevelopementDTO;
import com.ensa.miniproject.DTO.ProductOwnerDTO;
import com.ensa.miniproject.DTO.ProjectDTO;
import com.ensa.miniproject.DTO.ScrumMasterDTO;
import com.ensa.miniproject.entities.Project;
import com.ensa.miniproject.entities.ScrumMaster;
import com.ensa.miniproject.execptions.InvalidDateException;
import com.ensa.miniproject.mapping.EquipeDevelopementMapper;
import com.ensa.miniproject.mapping.ProductOwnerMapper;
import com.ensa.miniproject.mapping.ProjectMapper;
import com.ensa.miniproject.mapping.ScrumMasterMapper;
import com.ensa.miniproject.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ScrumMasterMapper scrumMasterMapper;
    private final ProductOwnerMapper productOwnerMapper;
    private final EquipeDevelopementMapper equipeDevelopementMapper;

    @Override
    public ProjectDTO addProject(ProjectDTO projectDTO) {
        if (projectDTO.dateDebut().isAfter(projectDTO.dateFin())) {
            throw new InvalidDateException("La date de début doit être inférieure à la date de fin.");
        }
        Project project = projectMapper.toEntity(projectDTO);
        project = projectRepository.save(project);
        return projectMapper.fromEntity(project);
    }

    @Override
    public ProjectDTO updateProject(ProjectDTO projectDTO) {
        if (projectDTO.dateDebut().isAfter(projectDTO.dateFin())) {
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

    @Override
    public ScrumMasterDTO addScrumMasterToProject(Long idProject, ScrumMasterDTO scrumMasterDTO) {
        Project project = projectRepository.getProjectById(idProject);
        project.setScrumMaster(scrumMasterMapper.toEntity(scrumMasterDTO));

        return scrumMasterDTO;
    }

    @Override
    public ProductOwnerDTO addProductOwnerToProject(Long idProject, ProductOwnerDTO productOwnerDTO) {
        Project project = projectRepository.getProjectById(idProject);
        project.setOwner(productOwnerMapper.toEntity(productOwnerDTO));

        return productOwnerDTO;
    }

    @Override
    public EquipeDevelopementDTO addEquipeDevelopementToProject(Long idProject, EquipeDevelopementDTO equipeDevelopementDTO) {
        Project project = projectRepository.getProjectById(idProject);
        project.setEquipeDevelopement(equipeDevelopementMapper.toEntity(equipeDevelopementDTO));
        return equipeDevelopementDTO;
    }

}
