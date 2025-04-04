package com.ensa.miniproject.services.productBacklog;


import com.ensa.miniproject.DTO.EpicDTO;
import com.ensa.miniproject.DTO.ProductBacklogDTO;
import com.ensa.miniproject.DTO.UserDTO;

import java.util.List;

public interface ProductBacklogService {

    ProductBacklogDTO addProductBacklog(ProductBacklogDTO productBacklogDTO);
    ProductBacklogDTO updateProductBacklog(ProductBacklogDTO productBacklogDTO);
    ProductBacklogDTO getProductBacklogById(Long id);
    List<ProductBacklogDTO> getProductBacklogs();
    void deleteProductBacklog(Long id);

    UserDTO affectUserStoryToProductBackLog(Long id ,UserDTO userDTO);
    EpicDTO affectEpicToProductBackLog(Long id , EpicDTO userDTO);

    List<EpicDTO> getEpicsWithUserStories(Long productBacklogId);


}
