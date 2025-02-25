package com.ensa.miniproject.services;

import com.ensa.miniproject.entities.ProductBacklog;
import com.ensa.miniproject.entities.ProductOwner;
import com.ensa.miniproject.entities.Project;

import java.util.List;

public interface ProductOwnerService {

    ///  CRUD METHAUDE  FOR PROJECT ENTITY///
    public Project addProject(Project project);
    public Project updateProject(Project project);
    public Project getProjectById(Long id);
    public List<Project> getProjects();
    public void deleteProject(Long id);

    /// CRUD METHAUDE FOR PRODUCT OWNER
    public ProductOwner addProductOwner(ProductOwner productOwner);
    public ProductOwner updateProductOwner(ProductOwner productOwner);
    public ProductOwner getProductOwnerById(Long id);
    public List<ProductOwner> getProductOwners();
    public void deleteProductOwner(Long id);

    /// product backlog crud

    public ProductBacklog addProductBacklog(ProductBacklog productBacklog);
    public ProductBacklog updateProductBacklog(ProductBacklog productBacklog);
    public ProductBacklog getProductBacklogById(Long id);
    public List<ProductBacklog> getProductBacklogs();
    public void deleteProductBacklog(Long id);




    public void affectBacklogToProject(Long idProduct, ProductBacklog productBacklog);
    public void affectProjrctToBacklog(ProductBacklog backlog, Project project);




}


