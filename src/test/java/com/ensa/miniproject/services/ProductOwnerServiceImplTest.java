package com.ensa.miniproject.services;

import com.ensa.miniproject.DTO.*;
import com.ensa.miniproject.entities.*;
import com.ensa.miniproject.mapping.*;
import com.ensa.miniproject.repository.*;
import com.ensa.miniproject.services.productOwner.ProductOwnerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductOwnerServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProductOwnerRepository productOwnerRepository;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private ProductOwnerMapper productOwnerMapper;

    @Mock
    private ProductBacklogMapper productBacklogMapper;

    @InjectMocks
    private ProductOwnerServiceImpl productOwnerService;

    private ProductOwner productOwner;
    private ProductOwnerDTO productOwnerDTO;
    private Project project;
    private ProjectDTO projectDTO;
    private ProductBacklog productBacklog;
    private ProductBacklogDTO productBacklogDTO;

    @BeforeEach
    void setUp() {
        // Setup ProductOwner
        productOwner = new ProductOwner();
        productOwner.setId(1L);
        productOwner.setUsername("testOwner");
        productOwner.setPrenom("John");
        productOwner.setPassword("password");
        productOwner.setEmail("john@test.com");

        productOwnerDTO = new ProductOwnerDTO(
                1L, "testOwner", "John", "password", "john@test.com");

        // Setup Project
        project = new Project();
        project.setId(1L);
        project.setNom("Test Project");

        projectDTO = new ProjectDTO(
                1L, "Test Project", null, null, null, null, null, null
        );

        // Setup ProductBacklog
        productBacklog = new ProductBacklog();
        productBacklog.setId(1L);
        productBacklog.setTitle("Test Backlog");

        productBacklogDTO = new ProductBacklogDTO(
                1L, "Test Backlog", null, null, null, null,null
        );
    }

    @Test
    @DisplayName("Add ProductOwner - Should return saved DTO")
    void addProductOwner_ShouldReturnSavedProductOwnerDTO() {
        // Arrange
        when(productOwnerMapper.toEntity(any(ProductOwnerDTO.class))).thenReturn(productOwner);
        when(productOwnerRepository.save(any(ProductOwner.class))).thenReturn(productOwner);
        when(productOwnerMapper.fromEntity(any(ProductOwner.class))).thenReturn(productOwnerDTO);

        // Act
        ProductOwnerDTO result = productOwnerService.addProductOwner(productOwnerDTO);

        // Assert
        assertNotNull(result);
        assertEquals(productOwnerDTO.id(), result.id());
        assertEquals(productOwnerDTO.username(), result.username());
        verify(productOwnerMapper).toEntity(productOwnerDTO);
        verify(productOwnerRepository).save(productOwner);
        verify(productOwnerMapper).fromEntity(productOwner);
    }

    @Test
    @DisplayName("Update ProductOwner - Should return updated DTO")
    void updateProductOwner_ShouldReturnUpdatedProductOwnerDTO() {
        // Arrange
        ProductOwnerDTO updatedDTO = new ProductOwnerDTO(
                1L, "updatedOwner", "Updated", "newPass", "updated@test.com");

        when(productOwnerMapper.toEntity(any(ProductOwnerDTO.class))).thenReturn(productOwner);
        when(productOwnerRepository.save(any(ProductOwner.class))).thenReturn(productOwner);
        when(productOwnerMapper.fromEntity(any(ProductOwner.class))).thenReturn(updatedDTO);

        // Act
        ProductOwnerDTO result = productOwnerService.updateProductOwner(updatedDTO);

        // Assert
        assertNotNull(result);
        assertEquals("updatedOwner", result.username());
        assertEquals("Updated", result.prenom());
        verify(productOwnerRepository).save(productOwner);
    }

    @Test
    @DisplayName("Get ProductOwner by ID - When exists - Should return DTO")
    void getProductOwnerById_WhenExists_ShouldReturnProductOwnerDTO() {
        // Arrange
        when(productOwnerRepository.findById(1L)).thenReturn(Optional.of(productOwner));
        when(productOwnerMapper.fromEntity(any(ProductOwner.class))).thenReturn(productOwnerDTO);

        // Act
        ProductOwnerDTO result = productOwnerService.getProductOwnerById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("testOwner", result.username());
        verify(productOwnerRepository).findById(1L);
    }

    @Test
    @DisplayName("Get ProductOwner by ID - When not exists - Should throw exception")
    void getProductOwnerById_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(productOwnerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                productOwnerService.getProductOwnerById(1L));
        verify(productOwnerRepository).findById(1L);
    }

    @Test
    @DisplayName("Get all ProductOwners - Should return list of DTOs")
    void getProductOwners_ShouldReturnListOfProductOwnerDTOs() {
        // Arrange
        when(productOwnerRepository.findAll()).thenReturn(List.of(productOwner));
        when(productOwnerMapper.fromEntity(any(ProductOwner.class))).thenReturn(productOwnerDTO);

        // Act
        List<ProductOwnerDTO> results = productOwnerService.getProductOwners();

        // Assert
        assertEquals(1, results.size());
        assertEquals(1L, results.get(0).id());
        verify(productOwnerRepository).findAll();
    }

    @Test
    @DisplayName("Delete ProductOwner - Should call repository delete")
    void deleteProductOwner_ShouldCallRepositoryDelete() {
        // Act
        productOwnerService.deleteProductOwner(1L);

        // Assert
        verify(productOwnerRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Affect Backlog to Project - Should update project")
    void affectBacklogToProject_ShouldUpdateProject() {
        // Arrange
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(productBacklogMapper.toEntity(any(ProductBacklogDTO.class))).thenReturn(productBacklog);

        // Act
        productOwnerService.affectBacklogToProject(1L, productBacklogDTO);

        // Assert
        verify(projectRepository).findById(1L);
        verify(projectRepository).save(project);
        assertEquals(productBacklog, project.getProductBacklog());
    }

    @Test
    @DisplayName("Affect Backlog to Project - When project not found - Should throw exception")
    void affectBacklogToProject_WhenProjectNotFound_ShouldThrowException() {
        // Arrange
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                productOwnerService.affectBacklogToProject(1L, productBacklogDTO));
        verify(projectRepository).findById(1L);
    }

    @Test
    @DisplayName("Affect Project to Backlog - Should update project")
    void affectProjectToBacklog_ShouldUpdateProject() {
        // Arrange
        when(productBacklogMapper.toEntity(any(ProductBacklogDTO.class))).thenReturn(productBacklog);
        when(projectMapper.toEntity(any(ProjectDTO.class))).thenReturn(project);

        // Act
        productOwnerService.affectProjectToBacklog(productBacklogDTO, projectDTO);

        // Assert
        verify(projectRepository).save(project);
        assertEquals(productBacklog, project.getProductBacklog());
    }

}