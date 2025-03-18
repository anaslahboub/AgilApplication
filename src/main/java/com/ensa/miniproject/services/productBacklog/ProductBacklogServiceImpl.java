package com.ensa.miniproject.services.productBacklog;

import com.ensa.miniproject.DTO.ProductBacklogDTO;
import com.ensa.miniproject.entities.ProductBacklog;
import com.ensa.miniproject.mapping.ProductBacklogMapper;
import com.ensa.miniproject.repository.ProductBacklogRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class ProductBacklogServiceImpl implements ProductBacklogService {
    private final ProductBacklogRepository productBacklogRepository;
    private final ProductBacklogMapper productBacklogMapper;
    @Override
    public ProductBacklogDTO addProductBacklog(ProductBacklogDTO productBacklogDTO) {
        ProductBacklog productBacklog = productBacklogMapper.toEntity(productBacklogDTO);
        productBacklog = productBacklogRepository.save(productBacklog);
        return productBacklogMapper.fromEntity(productBacklog);
    }

    @Override
    public ProductBacklogDTO updateProductBacklog(ProductBacklogDTO productBacklogDTO) {
        ProductBacklog productBacklog = productBacklogMapper.toEntity(productBacklogDTO);
        productBacklog = productBacklogRepository.save(productBacklog);
        return productBacklogMapper.fromEntity(productBacklog);
    }

    @Override
    public ProductBacklogDTO getProductBacklogById(Long id) {
        ProductBacklog productBacklog = productBacklogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductBacklog avec ID " + id + " non trouvé"));
        return productBacklogMapper.fromEntity(productBacklog);
    }

    @Override
    public List<ProductBacklogDTO> getProductBacklogs() {
        return productBacklogRepository.findAll()
                .stream()
                .map(productBacklogMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProductBacklog(Long id) {
        productBacklogRepository.deleteById(id);
    }

}
