package com.ensa.miniproject.services.project;

import com.ensa.miniproject.dto.EquipeDevelopementDTO;
import com.ensa.miniproject.dto.ProductOwnerDTO;
import com.ensa.miniproject.dto.ProjectDTO;
import com.ensa.miniproject.dto.ScrumMasterDTO;
import com.ensa.miniproject.entities.Project;
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
    @Transactional
    public ProjectDTO updateProject(ProjectDTO projectDTO) {
        if (projectDTO.dateDebut().isAfter(projectDTO.dateFin())) {
            throw new InvalidDateException("La date de début doit être inférieure à la date de fin.");
        }
        Project project = projectRepository.findById(projectDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("Projet avec cette ID " + projectDTO.id() + " non trouvé"));
        if (projectDTO.nom()!=null) project.setNom(projectDTO.nom());
        if (projectDTO.dateFin()!=null) project.setDateFin(projectDTO.dateFin());
        if (projectDTO.productBacklog()!=null) project.setProductBacklog(projectDTO.productBacklog());
        if (projectDTO.owner()!=null) project.setOwner(projectDTO.owner());
        if (projectDTO.scrumMaster()!=null) project.setScrumMaster(projectDTO.scrumMaster());

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
                .toList();
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
