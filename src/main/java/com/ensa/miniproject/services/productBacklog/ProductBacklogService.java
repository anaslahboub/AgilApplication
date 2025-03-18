package com.ensa.miniproject.services.productBacklog;


import com.ensa.miniproject.DTO.ProductBacklogDTO;

import java.util.List;

public interface ProductBacklogService {

    ProductBacklogDTO addProductBacklog(ProductBacklogDTO productBacklogDTO);
    ProductBacklogDTO updateProductBacklog(ProductBacklogDTO productBacklogDTO);
    ProductBacklogDTO getProductBacklogById(Long id);
    List<ProductBacklogDTO> getProductBacklogs();
    void deleteProductBacklog(Long id);
}
