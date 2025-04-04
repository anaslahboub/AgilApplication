package com.ensa.miniproject.services.productOwner;

import com.ensa.miniproject.DTO.ProductBacklogDTO;
import com.ensa.miniproject.DTO.ProductOwnerDTO;
import com.ensa.miniproject.DTO.ProjectDTO;

import java.util.List;

public interface ProductOwnerService {

    ///  CRUD METHODS FOR PROJECT ///


    /// CRUD METHODS FOR PRODUCT OWNER ///
    ProductOwnerDTO addProductOwner(ProductOwnerDTO productOwnerDTO);
    ProductOwnerDTO updateProductOwner(ProductOwnerDTO productOwnerDTO);
    ProductOwnerDTO getProductOwnerById(Long id);
    List<ProductOwnerDTO> getProductOwners();
    void deleteProductOwner(Long id);

    /// CRUD METHODS FOR PRODUCT BACKLOG ///


    /// ADDITIONAL METHODS ///
    void affectBacklogToProject(Long idProject, ProductBacklogDTO productBacklogDTO);
    void affectProjectToBacklog(ProductBacklogDTO backlogDTO, ProjectDTO projectDTO);
}