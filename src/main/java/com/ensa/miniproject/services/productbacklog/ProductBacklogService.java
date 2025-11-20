package com.ensa.miniproject.services.productbacklog;

import com.ensa.miniproject.dto.ProductBacklogDTO;
import java.util.List;

public interface ProductBacklogService {
    ProductBacklogDTO addProductBacklog(ProductBacklogDTO productBacklogDTO);
    ProductBacklogDTO updateProductBacklog(ProductBacklogDTO productBacklogDTO);
    ProductBacklogDTO getProductBacklogById(Long id);
    List<ProductBacklogDTO> getProductBacklogs();
    void deleteProductBacklog(Long id);
    ProductBacklogDTO addEpicsToProductBacklog(Long productBacklogId, List<Long> epicIds);
    ProductBacklogDTO removeEpicFromProductBacklog(Long productBacklogId, Long epicId);

}
