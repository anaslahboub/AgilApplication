package com.ensa.miniproject.services;

import com.ensa.miniproject.DTO.ProductBacklogDTO;
import com.ensa.miniproject.DTO.ProductOwnerDTO;
import com.ensa.miniproject.DTO.ProjectDTO;
import com.ensa.miniproject.entities.ProductBacklog;
import com.ensa.miniproject.entities.ProductOwner;
import com.ensa.miniproject.entities.Project;
import com.ensa.miniproject.execptions.InvalidDateException;
import com.ensa.miniproject.mapping.ProductBacklogMapper;
import com.ensa.miniproject.mapping.ProductOwnerMapper;
import com.ensa.miniproject.mapping.ProjectMapper;
import com.ensa.miniproject.repository.ProductBacklogRepository;
import com.ensa.miniproject.repository.ProductOwnerRepository;
import com.ensa.miniproject.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductOwnerServiceImpl implements ProductOwnerService {

    private final ProjectRepository projectRepository;
    private final ProductOwnerRepository productOwnerRepository;
    private final ProductBacklogRepository productBacklogRepository;
    private final ProjectMapper projectMapper;
    private final ProductOwnerMapper productOwnerMapper;
    private final ProductBacklogMapper productBacklogMapper;

    @Autowired
    public ProductOwnerServiceImpl(ProjectRepository projectRepository,
                                   ProductOwnerRepository productOwnerRepository,
                                   ProductBacklogRepository productBacklogRepository,
                                   ProjectMapper projectMapper,
                                   ProductOwnerMapper productOwnerMapper,
                                   ProductBacklogMapper productBacklogMapper) {
        this.projectRepository = projectRepository;
        this.productOwnerRepository = productOwnerRepository;
        this.productBacklogRepository = productBacklogRepository;
        this.projectMapper = projectMapper;
        this.productOwnerMapper = productOwnerMapper;
        this.productBacklogMapper = productBacklogMapper;
    }

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

    @Override
    public ProductOwnerDTO addProductOwner(ProductOwnerDTO productOwnerDTO) {
        ProductOwner productOwner = productOwnerMapper.toEntity(productOwnerDTO);
        productOwner = productOwnerRepository.save(productOwner);
        return productOwnerMapper.fromEntity(productOwner);
    }

    @Override
    public ProductOwnerDTO updateProductOwner(ProductOwnerDTO productOwnerDTO) {
        ProductOwner productOwner = productOwnerMapper.toEntity(productOwnerDTO);
        productOwner = productOwnerRepository.save(productOwner);
        return productOwnerMapper.fromEntity(productOwner);
    }

    @Override
    public ProductOwnerDTO getProductOwnerById(Long id) {
        ProductOwner productOwner = productOwnerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductOwner avec ID " + id + " non trouvé"));
        return productOwnerMapper.fromEntity(productOwner);
    }

    @Override
    public List<ProductOwnerDTO> getProductOwners() {
        return productOwnerRepository.findAll()
                .stream()
                .map(productOwnerMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProductOwner(Long id) {
        productOwnerRepository.deleteById(id);
    }

    @Override
    public ProductBacklogDTO addProductBacklog(ProductBacklogDTO productBacklogDTO) {
        ProductBacklog productBacklog = productBacklogMapper.toEntity(productBacklogDTO);
        productBacklog = productBacklogRepository.save(productBacklog);
        return productBacklogMapper.fromEntity(productBacklog);
    }

    @Override
    public ProductBacklogDTO updateProductBacklog(ProductBacklogDTO productBacklogDTO) {
        ProductBacklog productBacklog = productBacklogMapper.toEntity(productBacklogDTO);
        productBacklog = productBacklogRepository.save(productBacklog);
        return productBacklogMapper.fromEntity(productBacklog);
    }

    @Override
    public ProductBacklogDTO getProductBacklogById(Long id) {
        ProductBacklog productBacklog = productBacklogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductBacklog avec ID " + id + " non trouvé"));
        return productBacklogMapper.fromEntity(productBacklog);
    }

    @Override
    public List<ProductBacklogDTO> getProductBacklogs() {
        return productBacklogRepository.findAll()
                .stream()
                .map(productBacklogMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProductBacklog(Long id) {
        productBacklogRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void affectBacklogToProject(Long idProject, ProductBacklogDTO productBacklogDTO) {
        Project project = projectRepository.findById(idProject)
                .orElseThrow(() -> new EntityNotFoundException("Projet avec ID " + idProject + " non trouvé"));
        ProductBacklog productBacklog = productBacklogMapper.toEntity(productBacklogDTO);
        project.setProductBacklog(productBacklog);
        projectRepository.save(project);
    }

    @Override
    @Transactional
    public void affectProjectToBacklog(ProductBacklogDTO backlogDTO, ProjectDTO projectDTO) {
        ProductBacklog backlog = productBacklogMapper.toEntity(backlogDTO);
        Project project = projectMapper.toEntity(projectDTO);
        project.setProductBacklog(backlog);
        projectRepository.save(project);
    }
    //    public void assignBacklogToProject(Long projectId, Long backlogId) {
    //        Project project = projectRepository.findById(projectId)
    //                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));
    //
    //        ProductBacklog backlog = productBacklogRepository.findById(backlogId)
    //                .orElseThrow(() -> new EntityNotFoundException("Backlog not found with id: " + backlogId));
    //
    //        // Établir la relation bidirectionnelle
    //        project.getProductBacklogs().add(backlog);
    //        backlog.setProject(project);
    //
    //        // L'annotation @Transactional garantit que les changements seront persistés
    //    }
}