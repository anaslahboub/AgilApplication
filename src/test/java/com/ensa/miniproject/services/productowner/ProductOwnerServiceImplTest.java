package com.ensa.miniproject.services.productowner;

import com.ensa.miniproject.dto.*;
import com.ensa.miniproject.entities.*;
import com.ensa.miniproject.mapping.*;
import com.ensa.miniproject.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
                1L, "Test Backlog", null, null, null, null, null
        );
    }

    // ------------------------- ADD -------------------------

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
        verify(productOwnerRepository).save(productOwner);
    }

    // ------------------------- UPDATE (CRUCIAL POUR COUVERTURE) -------------------------

    @Test
    @DisplayName("Update ProductOwner - Full Update")
    void updateProductOwner_FullUpdate() {
        // Arrange
        ProductOwnerDTO updateDto = new ProductOwnerDTO(1L, "NewUser", "NewPrenom", "NewPass", "new@mail.com");

        when(productOwnerRepository.findById(1L)).thenReturn(Optional.of(productOwner));
        // Mock the mapper response
        when(productOwnerMapper.fromEntity(any(ProductOwner.class))).thenReturn(updateDto);

        // Act
        ProductOwnerDTO result = productOwnerService.updateProductOwner(updateDto);

        // Assert
        assertNotNull(result);
        // Verify setters were called on the entity
        assertEquals("NewUser", productOwner.getUsername());
        assertEquals("NewPrenom", productOwner.getPrenom());
        assertEquals("new@mail.com", productOwner.getEmail());
    }

    @Test
    @DisplayName("Update ProductOwner - Partial Update (Some fields null)")
    void updateProductOwner_PartialUpdate() {
        // Arrange
        // Only update Username, others are null
        ProductOwnerDTO partialDto = new ProductOwnerDTO(1L, "NewUserOnly", null, null, null);

        when(productOwnerRepository.findById(1L)).thenReturn(Optional.of(productOwner));
        when(productOwnerMapper.fromEntity(any(ProductOwner.class))).thenReturn(partialDto);

        // Act
        productOwnerService.updateProductOwner(partialDto);

        // Assert
        // Verify Username changed
        assertEquals("NewUserOnly", productOwner.getUsername());
        // Verify others did NOT change (remained as set in setUp)
        assertEquals("John", productOwner.getPrenom());
        assertEquals("john@test.com", productOwner.getEmail());
    }

    @Test
    @DisplayName("Update ProductOwner - Not Found")
    void updateProductOwner_NotFound() {
        // Arrange
        when(productOwnerRepository.findById(99L)).thenReturn(Optional.empty());
        ProductOwnerDTO dto = new ProductOwnerDTO(99L, null, null, null, null);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> productOwnerService.updateProductOwner(dto));
    }

    // ------------------------- GET BY ID -------------------------

    @Test
    @DisplayName("Get ProductOwner by ID - When exists")
    void getProductOwnerById_WhenExists() {
        // Arrange
        when(productOwnerRepository.findById(1L)).thenReturn(Optional.of(productOwner));
        when(productOwnerMapper.fromEntity(any(ProductOwner.class))).thenReturn(productOwnerDTO);

        // Act
        ProductOwnerDTO result = productOwnerService.getProductOwnerById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    @DisplayName("Get ProductOwner by ID - Not Found")
    void getProductOwnerById_NotFound() {
        // Arrange
        when(productOwnerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> productOwnerService.getProductOwnerById(1L));
    }

    // ------------------------- GET ALL -------------------------

    @Test
    @DisplayName("Get all ProductOwners")
    void getProductOwners_ShouldReturnList() {
        // Arrange
        when(productOwnerRepository.findAll()).thenReturn(List.of(productOwner));
        when(productOwnerMapper.fromEntity(any(ProductOwner.class))).thenReturn(productOwnerDTO);

        // Act
        List<ProductOwnerDTO> results = productOwnerService.getProductOwners();

        // Assert
        assertEquals(1, results.size());
    }

    // ------------------------- DELETE -------------------------

    @Test
    @DisplayName("Delete ProductOwner")
    void deleteProductOwner_Success() {
        // Act
        productOwnerService.deleteProductOwner(1L);

        // Assert
        verify(productOwnerRepository).deleteById(1L);
    }

    // ------------------------- AFFECTATION -------------------------

    @Test
    @DisplayName("Affect Backlog to Project - Success")
    void affectBacklogToProject_Success() {
        // Arrange
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(productBacklogMapper.toEntity(any(ProductBacklogDTO.class))).thenReturn(productBacklog);

        // Act
        productOwnerService.affectBacklogToProject(1L, productBacklogDTO);

        // Assert
        ArgumentCaptor<Project> projectCaptor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(projectCaptor.capture());

        assertEquals(productBacklog, projectCaptor.getValue().getProductBacklog());
    }

    @Test
    @DisplayName("Affect Backlog to Project - Project Not Found")
    void affectBacklogToProject_NotFound() {
        // Arrange
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                productOwnerService.affectBacklogToProject(1L, productBacklogDTO));
    }

    @Test
    @DisplayName("Affect Project to Backlog - Success")
    void affectProjectToBacklog_Success() {
        // Arrange
        when(productBacklogMapper.toEntity(any(ProductBacklogDTO.class))).thenReturn(productBacklog);
        when(projectMapper.toEntity(any(ProjectDTO.class))).thenReturn(project);

        // Act
        productOwnerService.affectProjectToBacklog(productBacklogDTO, projectDTO);

        // Assert
        ArgumentCaptor<Project> projectCaptor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(projectCaptor.capture());

        assertEquals(productBacklog, projectCaptor.getValue().getProductBacklog());
    }
}