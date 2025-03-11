package com.ensa.miniproject.services;

import com.ensa.miniproject.DTO.ProductBacklogDTO;
import com.ensa.miniproject.DTO.ProductOwnerDTO;
import com.ensa.miniproject.DTO.ProjectDTO;

import java.util.List;

public interface ProductOwnerService {

    ///  CRUD METHODS FOR PROJECT ///
    ProjectDTO addProject(ProjectDTO projectDTO);
    ProjectDTO updateProject(ProjectDTO projectDTO);
    ProjectDTO getProjectById(Long id);
    List<ProjectDTO> getProjects();
    void deleteProject(Long id);

    /// CRUD METHODS FOR PRODUCT OWNER ///
    ProductOwnerDTO addProductOwner(ProductOwnerDTO productOwnerDTO);
    ProductOwnerDTO updateProductOwner(ProductOwnerDTO productOwnerDTO);
    ProductOwnerDTO getProductOwnerById(Long id);
    List<ProductOwnerDTO> getProductOwners();
    void deleteProductOwner(Long id);

    /// CRUD METHODS FOR PRODUCT BACKLOG ///
    ProductBacklogDTO addProductBacklog(ProductBacklogDTO productBacklogDTO);
    ProductBacklogDTO updateProductBacklog(ProductBacklogDTO productBacklogDTO);
    ProductBacklogDTO getProductBacklogById(Long id);
    List<ProductBacklogDTO> getProductBacklogs();
    void deleteProductBacklog(Long id);

    /// ADDITIONAL METHODS ///
    void affectBacklogToProject(Long idProject, ProductBacklogDTO productBacklogDTO);
    void affectProjectToBacklog(ProductBacklogDTO backlogDTO, ProjectDTO projectDTO);
}