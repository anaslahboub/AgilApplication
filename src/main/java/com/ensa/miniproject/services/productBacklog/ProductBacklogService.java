package com.ensa.miniproject.services.productBacklog;


import com.ensa.miniproject.DTO.EpicDTO;
import com.ensa.miniproject.DTO.ProductBacklogDTO;
import com.ensa.miniproject.DTO.UserStoryDTO;
import com.ensa.miniproject.entities.UserStory;

import java.util.List;

public interface ProductBacklogService {

    ProductBacklogDTO addProductBacklog(ProductBacklogDTO productBacklogDTO);
    ProductBacklogDTO updateProductBacklog(ProductBacklogDTO productBacklogDTO);
    ProductBacklogDTO getProductBacklogById(Long id);
    List<ProductBacklogDTO> getProductBacklogs();
    void deleteProductBacklog(Long id);


    /////////////METIER //////////////////
    ProductBacklogDTO addEpicsToProductBacklog(Long productBacklogId, List<Long> epicIds);
    ProductBacklogDTO removeEpicFromProductBacklog(Long productBacklogId, Long epicId);

}
