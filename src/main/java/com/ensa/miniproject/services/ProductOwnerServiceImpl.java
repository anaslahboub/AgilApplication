package com.ensa.miniproject.services;

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
    public Project addProject(Project project) {
        if (project .getDateDebut().isAfter(project.getDateFin())) {
            throw new InvalidDateException("la date debut doit etre inf a date fin ");
        }
        else{
        return projectRepository.save(project) ;
        }
    }

    @Override
    public Project updateProject(Project project) {
        if (project.getDateDebut().isAfter(project.getDateFin())) {
            throw new InvalidDateException("la date debut doit etre inf a date fin ");
        }
        else{
        return projectRepository.save(project) ;
        }
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projet avec ID " + id + " non trouvé"));
    }

    @Override
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @Override
    public void deleteProject(Long id) {
    projectRepository.deleteById(id);
    }

    @Override
    public ProductOwner addProductOwner(ProductOwner productOwner) {
        return productOwnerRepository.save(productOwner);
    }

    @Override
    public ProductOwner updateProductOwner(ProductOwner productOwner) {
        return productOwnerRepository.save(productOwner);
    }

    @Override
    public ProductOwner getProductOwnerById(Long id) {
        return productOwnerRepository.getProductOwnerById(id);
    }

    @Override
    public List<ProductOwner> getProductOwners() {
        return productOwnerRepository.findAll();
    }
    @Override
    public void deleteProductOwner(Long id){
        productOwnerRepository.deleteById(id);
    }

    @Override
    public ProductBacklog addProductBacklog(ProductBacklog productBacklog) {
        return productBacklogRepository.save(productBacklog);
    }

    @Override
    public ProductBacklog updateProductBacklog(ProductBacklog productBacklog) {
        return productBacklogRepository.save(productBacklog);
    }

    @Override
    public ProductBacklog getProductBacklogById(Long id) {
        return productBacklogRepository.findById(id).orElseThrow(()->new EntityNotFoundException("ce product backlog nexiste pas"));
    }

    @Override
    public List<ProductBacklog> getProductBacklogs() {
        return productBacklogRepository.findAll();
    }

    @Override
    public void deleteProductBacklog(Long id) {
        productBacklogRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void affectBacklogToProject(Long idProject, ProductBacklog productBacklog) {
        Project project = this.getProjectById(idProject);
        project.setProductBacklog(productBacklog);
        productBacklog.setProject(project);
    }

    @Override
    @Transactional
    public void affectProjrctToBacklog(ProductBacklog backlog, Project project) {
        backlog.setProject(project);
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
