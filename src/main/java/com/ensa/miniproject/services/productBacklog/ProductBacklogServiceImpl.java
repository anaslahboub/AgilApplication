package com.ensa.miniproject.services.productBacklog;

import com.ensa.miniproject.DTO.ProductBacklogDTO;
import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.entities.ProductBacklog;
import com.ensa.miniproject.execptions.ResourceNotFoundException;
import com.ensa.miniproject.mapping.ProductBacklogMapper;
import com.ensa.miniproject.repository.EpicRepository;
import com.ensa.miniproject.repository.ProductBacklogRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class ProductBacklogServiceImpl implements ProductBacklogService {
    private final ProductBacklogRepository productBacklogRepository;
    private final ProductBacklogMapper productBacklogMapper;
    private final EpicRepository epicRepository;
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


    @Override
    @Transactional
    public ProductBacklogDTO addEpicsToProductBacklog(Long productBacklogId, List<Long> epicIds) {
        ProductBacklog productBacklog = productBacklogRepository.findById(productBacklogId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductBacklog", "id", productBacklogId));

        List<Epic> epics = epicRepository.findAllById(epicIds);

        // Verify all epics were found
        if (epics.size() != epicIds.size()) {
            Set<Long> foundIds = epics.stream().map(Epic::getId).collect(Collectors.toSet());
            String missingIds = epicIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            throw new ResourceNotFoundException("Missing Epic IDs: " + missingIds);
        }

        // Add to product backlog and save
        epics.forEach(epic -> {
            productBacklog.getEpics().add(epic);
        });
        epicRepository.saveAll(epics);

        return productBacklogMapper.fromEntity(productBacklog);
    }

    @Override
    @Transactional
    public ProductBacklogDTO removeEpicFromProductBacklog(Long productBacklogId, Long epicId) {
        ProductBacklog productBacklog = productBacklogRepository.findById(productBacklogId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductBacklog", "id", productBacklogId));

        Epic epic = epicRepository.findById(epicId)
                .orElseThrow(() -> new ResourceNotFoundException("Epic", "id", epicId));



        productBacklog.getEpics().remove(epic);
        epicRepository.save(epic);

        return productBacklogMapper.fromEntity(productBacklog);
    }
}
