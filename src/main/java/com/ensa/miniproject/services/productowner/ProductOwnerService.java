package com.ensa.miniproject.services.productowner;

import com.ensa.miniproject.dto.ProductBacklogDTO;
import com.ensa.miniproject.dto.ProductOwnerDTO;
import com.ensa.miniproject.dto.ProjectDTO;
import java.util.List;

public interface ProductOwnerService {

    ProductOwnerDTO addProductOwner(ProductOwnerDTO productOwnerDTO);
    ProductOwnerDTO updateProductOwner(ProductOwnerDTO productOwnerDTO);
    ProductOwnerDTO getProductOwnerById(Long id);
    List<ProductOwnerDTO> getProductOwners();
    void deleteProductOwner(Long id);
    void affectBacklogToProject(Long idProject, ProductBacklogDTO productBacklogDTO);
    void affectProjectToBacklog(ProductBacklogDTO backlogDTO, ProjectDTO projectDTO);
}