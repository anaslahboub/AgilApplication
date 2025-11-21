package com.ensa.miniproject.services.productowner;

import com.ensa.miniproject.dto.*;
import com.ensa.miniproject.entities.*;
import com.ensa.miniproject.mapping.*;
import com.ensa.miniproject.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductOwnerServiceImpl implements ProductOwnerService {

    private final ProjectRepository projectRepository;
    private final ProductOwnerRepository productOwnerRepository;
    private final ProjectMapper projectMapper;
    private final ProductOwnerMapper productOwnerMapper;
    private final ProductBacklogMapper productBacklogMapper;


    @Override
    public ProductOwnerDTO addProductOwner(ProductOwnerDTO productOwnerDTO) {
        ProductOwner productOwner = productOwnerMapper.toEntity(productOwnerDTO);
        productOwner = productOwnerRepository.save(productOwner);
        return productOwnerMapper.fromEntity(productOwner);
    }

    @Override
    @Transactional
    public ProductOwnerDTO updateProductOwner(ProductOwnerDTO productOwnerDTO) {
        ProductOwner productOwner = productOwnerRepository.findById(productOwnerDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("ProductOwner avec ID " + productOwnerDTO.id() + " non trouvé"));
        if (productOwnerDTO.username() != null) productOwner.setUsername(productOwnerDTO.username());
        if (productOwnerDTO.email() != null) productOwner.setEmail(productOwnerDTO.email());
        if (productOwnerDTO.password() != null) productOwner.setPassword(productOwnerDTO.password());
        if (productOwnerDTO.prenom() != null) productOwner.setPrenom(productOwnerDTO.prenom());
        
        return productOwnerMapper.fromEntity(productOwner);
    }

    @Override
    public ProductOwnerDTO getProductOwnerById(Long id) {
        ProductOwner productOwner = productOwnerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductOwner avec ID " + id + " non trouvé in database "));
        return productOwnerMapper.fromEntity(productOwner);
    }

    @Override
    public List<ProductOwnerDTO> getProductOwners() {
        return productOwnerRepository.findAll()
                .stream()
                .map(productOwnerMapper::fromEntity)
                .toList();
    }

    @Override
    public void deleteProductOwner(Long id) {
        productOwnerRepository.deleteById(id);
    }



    @Override
    @Transactional
    public void affectBacklogToProject(Long idProject, ProductBacklogDTO productBacklogDTO) {
        Project project = projectRepository.findById(idProject)
                .orElseThrow(() -> new EntityNotFoundException("Projet avec ID " + idProject + " non trouvé"));
        ProductBacklog productBacklog = productBacklogMapper.toEntity(productBacklogDTO);
        project.setProductBacklog(productBacklog);
        projectRepository.save(project);
    }

    @Override
    @Transactional
    public void affectProjectToBacklog(ProductBacklogDTO backlogDTO, ProjectDTO projectDTO) {
        ProductBacklog backlog = productBacklogMapper.toEntity(backlogDTO);
        Project project = projectMapper.toEntity(projectDTO);
        project.setProductBacklog(backlog);
        projectRepository.save(project);
    }

}