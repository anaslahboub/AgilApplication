package com.ensa.miniproject.services;

import com.ensa.miniproject.entities.ProductBacklog;
import com.ensa.miniproject.entities.ProductOwner;
import com.ensa.miniproject.entities.Project;
import com.ensa.miniproject.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductOwnerServiceImpl implements ProductOwnerService {
    private ProjectRepository projectRepository;
    @Override
    public Project addProject(Project project) {
        return projectRepository.save(project) ;
    }

    @Override
    public Project updateProject(Project project) {

        return projectRepository.save(project) ;
    }

    @Override
    public Project getProject(Long id) {
        return null;
    }

    @Override
    public List<Project> getProjects() {
        return List.of();
    }

    @Override
    public void deleteProject(Long id) {

    }

    @Override
    public ProductOwner addProductOwner(ProductOwner productOwner) {
        return null;
    }

    @Override
    public ProductOwner updateProductOwner(ProductOwner productOwner) {
        return null;
    }

    @Override
    public ProductOwner getProductOwner(Long id) {
        return null;
    }

    @Override
    public List<ProductOwner> getProductOwners() {
        return List.of();
    }

    @Override
    public ProductBacklog addProductBacklog(ProductBacklog productBacklog) {
        return null;
    }

    @Override
    public ProductBacklog updateProductBacklog(ProductBacklog productBacklog) {
        return null;
    }

    @Override
    public ProductBacklog getProductBacklog(Long id) {
        return null;
    }

    @Override
    public List<ProductBacklog> getProductBacklogs() {
        return List.of();
    }

    @Override
    public void deleteProductBacklog(Long id) {

    }

    @Override
    public void affectBacklogToProject(Long idProduct, ProductBacklog productBacklog) {

    }

    @Override
    public void affectProjrctToBacklog(ProductBacklog backlog, Project project) {

    }
}
