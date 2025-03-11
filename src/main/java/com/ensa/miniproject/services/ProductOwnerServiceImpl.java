package com.ensa.miniproject.services;

import com.ensa.miniproject.DTO.*;
import com.ensa.miniproject.entities.ProductBacklog;
import com.ensa.miniproject.entities.ProductOwner;
import com.ensa.miniproject.entities.Project;
import com.ensa.miniproject.execptions.InvalidDateException;
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
    @Autowired
    public  ProductOwnerServiceImpl(ProjectRepository projectRepository, ProductOwnerRepository productOwnerRepository, ProductBacklogRepository productBacklogRepository) {
        this.projectRepository = projectRepository;
        this.productOwnerRepository = productOwnerRepository;
        this.productBacklogRepository = productBacklogRepository;
    }



    @Override
    public ProjectRespDTO addProject(ProjectCreateDTO projectDTO) {
        Project project = projectDTO.toEntity();
        if (project .getDateDebut().isAfter(project.getDateFin())) {
            throw new InvalidDateException("la date debut doit etre inf a date fin ");
        }
        else{
        return ProjectRespDTO.toProjectRespDTO(projectRepository.save(project));
        }
    }

    @Override
    public ProjectRespDTO updateProject(ProjectRespDTO projectDTO) {
        Project project = projectDTO.toEntity();
        if (project.getDateDebut().isAfter(project.getDateFin())) {
            throw new InvalidDateException("la date debut doit etre inf a date fin ");
        }
        else{
        return ProjectRespDTO.toProjectRespDTO(projectRepository.save(project));
        }
    }

    @Override
    public ProjectRespDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projet avec ID " + id + " non trouvé"));
        return ProjectRespDTO.toProjectRespDTO(project);
    }

    @Override
    public List<ProjectRespDTO> getProjects() {
        return projectRepository.findAll().stream().map(ProjectRespDTO::toProjectRespDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new EntityNotFoundException("Projet avec ID " + id + " non trouvé");
        }
        projectRepository.deleteById(id);
    }

    @Override
    public ProductOwnerRespDTO addProductOwner(ProductOwnerCreateDTO productOwnerDTO) {
        ProductOwner productOwner = productOwnerDTO.toEntity();
        return ProductOwnerRespDTO.toProductOwnerRespDTO(productOwnerRepository.save(productOwner));
    }

    @Override
    public ProductOwnerRespDTO updateProductOwner(ProductOwnerRespDTO productOwnerDTO) {
        ProductOwner productOwner = productOwnerDTO.toEntity();
        return ProductOwnerRespDTO.toProductOwnerRespDTO(productOwnerRepository.save(productOwner));
    }

    @Override
    public ProductOwnerRespDTO getProductOwnerById(Long id) {
        return ProductOwnerRespDTO.toProductOwnerRespDTO(productOwnerRepository.getProductOwnerById(id));
    }

    @Override
    public List<ProductOwnerRespDTO> getProductOwners() {
        return productOwnerRepository.findAll().
                stream().map(ProductOwnerRespDTO::toProductOwnerRespDTO).collect(Collectors.toList());
    }
    @Override
    public void deleteProductOwner(Long id){
        if (!productOwnerRepository.existsById(id)) {
            throw new EntityNotFoundException("productOwner avec ID " + id + " non trouvé");
        }
        productOwnerRepository.deleteById(id);
    }

    @Override
    public ProductBackLogRespDTO addProductBacklog(ProductBackLogCreateDTO productBackLogCreateDTO) {
        ProductBacklog productBacklog = productBackLogCreateDTO.toEntity();
        return ProductBackLogRespDTO.toProductBackLogRespDTO(productBacklogRepository.save(productBacklog));
    }

    @Override
    public ProductBackLogRespDTO updateProductBacklog(ProductBackLogRespDTO productBackLogRespDTO) {
        ProductBacklog productBacklog = productBackLogRespDTO.toEntity();
        return ProductBackLogRespDTO.toProductBackLogRespDTO(productBacklogRepository.save(productBacklog));
    }

    @Override
    public ProductBackLogRespDTO getProductBacklogById(Long id) {
        ProductBacklog productBacklog = productBacklogRepository.findById(id).orElseThrow(()->new EntityNotFoundException("ce product backlog nexiste pas"));
        return ProductBackLogRespDTO.toProductBackLogRespDTO(productBacklogRepository.getProductBacklogById(id));
    }

    @Override
    public List<ProductBackLogRespDTO> getProductBacklogs() {
        return productBacklogRepository.findAll()
                .stream().map(ProductBackLogRespDTO::toProductBackLogRespDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteProductBacklog(Long id) {
        if (!productBacklogRepository.existsById(id)) {
            throw new EntityNotFoundException("lr product backlog qii contient cette ID " + id + "n'existe pas ");
        }
        productBacklogRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void affectBacklogToProject(Long idProject, ProductBacklog productBacklog) {

    }

    @Override
    @Transactional
    public void affectProjrctToBacklog(ProductBacklog backlog, Project project) {
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
