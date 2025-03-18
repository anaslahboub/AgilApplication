package com.ensa.miniproject.services;

import com.ensa.miniproject.DTO.*;
import com.ensa.miniproject.entities.ProductBacklog;
import com.ensa.miniproject.entities.ProductOwner;
import com.ensa.miniproject.entities.Project;

import java.util.List;

public interface ProductOwnerService {

    ///  CRUD METHAUDE  FOR PROJECT ENTITY///
    public ProjectRespDTO addProject(ProjectCreateDTO project);
    public ProjectRespDTO updateProject(ProjectRespDTO project);
    public ProjectRespDTO getProjectById(Long id);
    public List<ProjectRespDTO> getProjects();
    public void deleteProject(Long id);

    /// CRUD METHAUDE FOR PRODUCT OWNER
    public ProductOwnerRespDTO addProductOwner(ProductOwnerCreateDTO productOwner);
    public ProductOwnerRespDTO updateProductOwner(ProductOwnerRespDTO productOwner);
    public ProductOwnerRespDTO getProductOwnerById(Long id);
    public List<ProductOwnerRespDTO> getProductOwners();
    public void deleteProductOwner(Long id);

    /// product backlog crud
    ///

    public ProductBackLogRespDTO addProductBacklog(ProductBackLogCreateDTO productBacklog);
    public ProductBackLogRespDTO updateProductBacklog(ProductBackLogRespDTO productBacklog);
    public ProductBackLogRespDTO getProductBacklogById(Long id);
    public List<ProductBackLogRespDTO> getProductBacklogs();
    public void deleteProductBacklog(Long id);



    public void affectBacklogToProject(Long idProduct, ProductBacklog productBacklog);
    public void affectProjrctToBacklog(ProductBacklog backlog, Project project);




}