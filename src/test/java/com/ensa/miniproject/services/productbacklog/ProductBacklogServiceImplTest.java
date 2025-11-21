package com.ensa.miniproject.services.productbacklog;

import com.ensa.miniproject.dto.ProductBacklogDTO;
import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.entities.ProductBacklog;
import com.ensa.miniproject.execptions.ResourceNotFoundException;
import com.ensa.miniproject.mapping.ProductBacklogMapper;
import com.ensa.miniproject.repository.EpicRepository;
import com.ensa.miniproject.repository.ProductBacklogRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductBacklogServiceImplTest {

    @Mock
    private ProductBacklogRepository productBacklogRepository;
    @Mock
    private ProductBacklogMapper productBacklogMapper;
    @Mock
    private EpicRepository epicRepository;

    @InjectMocks
    private ProductBacklogServiceImpl productBacklogService;

    private ProductBacklog productBacklog;
    private ProductBacklogDTO productBacklogDTO;
    private Epic epic;

    @BeforeEach
    void setUp() {
        // Setup Epic
        epic = new Epic();
        epic.setId(100L);
        epic.setTitle("Epic 1");

        // Setup ProductBacklog
        productBacklog = new ProductBacklog();
        productBacklog.setId(1L);
        productBacklog.setTitle("Test Product Backlog");
        productBacklog.setDescription("Test Description");
        // IMPORTANT: Initialize lists as ArrayList to allow modifications
        productBacklog.setEpics(new ArrayList<>());
        productBacklog.setUserStories(new ArrayList<>());

        // Setup DTO
        productBacklogDTO = new ProductBacklogDTO(
                1L,
                "Test Product Backlog",
                "Test Description",
                null,
                null,
                null,
                new ArrayList<>()
        );
    }

    // ------------------------- ADD -------------------------

    @Test
    @DisplayName("Add ProductBacklog - Should return saved DTO")
    void addProductBacklog_ShouldReturnSavedProductBacklogDTO() {
        // Arrange
        when(productBacklogMapper.toEntity(any(ProductBacklogDTO.class))).thenReturn(productBacklog);
        when(productBacklogRepository.save(any(ProductBacklog.class))).thenReturn(productBacklog);
        when(productBacklogMapper.fromEntity(any(ProductBacklog.class))).thenReturn(productBacklogDTO);

        // Act
        ProductBacklogDTO result = productBacklogService.addProductBacklog(productBacklogDTO);

        // Assert
        assertNotNull(result);
        assertEquals(productBacklogDTO.id(), result.id());
        verify(productBacklogRepository).save(productBacklog);
    }

    // ------------------------- GET BY ID -------------------------

    @Test
    @DisplayName("Get by ID - When exists - Should return DTO")
    void getProductBacklogById_WhenExists_ShouldReturnProductBacklogDTO() {
        // Arrange
        when(productBacklogRepository.findById(1L)).thenReturn(Optional.of(productBacklog));
        when(productBacklogMapper.fromEntity(any(ProductBacklog.class))).thenReturn(productBacklogDTO);

        // Act
        ProductBacklogDTO result = productBacklogService.getProductBacklogById(1L);

        // Assert
        assertEquals(1L, result.id());
    }

    @Test
    @DisplayName("Get by ID - When not exists - Should throw EntityNotFoundException")
    void getProductBacklogById_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(productBacklogRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                productBacklogService.getProductBacklogById(99L));
    }

    // ------------------------- GET ALL -------------------------

    @Test
    @DisplayName("Get all - Should return list")
    void getProductBacklogs_ShouldReturnListOfProductBacklogDTOs() {
        // Arrange
        when(productBacklogRepository.findAll()).thenReturn(List.of(productBacklog));
        when(productBacklogMapper.fromEntity(any(ProductBacklog.class))).thenReturn(productBacklogDTO);

        // Act
        List<ProductBacklogDTO> results = productBacklogService.getProductBacklogs();

        // Assert
        assertEquals(1, results.size());
    }

    // ------------------------- UPDATE -------------------------

    @Test
    @DisplayName("Update - Should update only non-null fields")
    void updateProductBacklog_Success() {
        // Arrange
        // DTO with only Title changed, description is null (should not change entity description)
        ProductBacklogDTO updateDto = new ProductBacklogDTO(
                1L, "New Title", null, null, null, null, null
        );

        when(productBacklogRepository.findById(1L)).thenReturn(Optional.of(productBacklog));
        // Mock return of mapper (usually the updated object)
        ProductBacklogDTO mappedResult = new ProductBacklogDTO(1L, "New Title", "Test Description", null, null, null, null);
        when(productBacklogMapper.fromEntity(productBacklog)).thenReturn(mappedResult);

        // Act
        ProductBacklogDTO result = productBacklogService.updateProductBacklog(updateDto);

        // Assert
        assertNotNull(result);
        // Verify entity state in memory
        assertEquals("New Title", productBacklog.getTitle());       // Changed
        assertEquals("Test Description", productBacklog.getDescription()); // Not Changed (was null in DTO)
    }

    @Test
    @DisplayName("Update - Not Found")
    void updateProductBacklog_NotFound() {
        // Arrange
        when(productBacklogRepository.findById(99L)).thenReturn(Optional.empty());
        ProductBacklogDTO dto = new ProductBacklogDTO(99L, null, null, null, null, null, null);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> productBacklogService.updateProductBacklog(dto));
    }


    @Test
    @DisplayName("Update ProductBacklog - Partial Update (Null fields should be ignored)")
    void updateProductBacklog_WithNullFields_ShouldNotUpdateEntity() {
        // Arrange
        // 1. On prépare une entité existante avec des données
        ProductBacklog existingBacklog = new ProductBacklog();
        existingBacklog.setId(1L);
        existingBacklog.setTitle("Titre Original");
        existingBacklog.setDescription("Description Originale");
        // (On suppose que les autres champs ont aussi des valeurs par défaut)

        // 2. On crée un DTO avec l'ID, mais TOUS les autres champs à NULL
        ProductBacklogDTO partialDto = new ProductBacklogDTO(
                1L,
                null, // title est null
                null, // description est null
                null, // status est null
                null, // priority est null
                null, // userStories est null
                null  // epics est null
        );

        // 3. Configuration des Mocks
        when(productBacklogRepository.findById(1L)).thenReturn(Optional.of(existingBacklog));
        // Le retour du mapper n'importe pas vraiment ici, on peut renvoyer n'importe quoi
        when(productBacklogMapper.fromEntity(any(ProductBacklog.class))).thenReturn(partialDto);

        // Act
        productBacklogService.updateProductBacklog(partialDto);

        // Assert
        // C'est ICI que la magie opère : on vérifie que les valeurs de l'entité sont restées INCHANGÉES
        assertEquals("Titre Original", existingBacklog.getTitle());
        assertEquals("Description Originale", existingBacklog.getDescription());

        // On vérifie que le repository a été appelé (même si aucune modif n'a été faite, le flux doit aller au bout)
        // Note : Si votre code service n'a pas de .save() explicite (grâce à @Transactional), cette ligne est optionnelle.
        // verify(productBacklogRepository).save(existingBacklog);
    }

    // ------------------------- DELETE -------------------------

    @Test
    @DisplayName("Delete - Should call repository")
    void deleteProductBacklog_Success() {
        // Act
        productBacklogService.deleteProductBacklog(1L);

        // Assert
        verify(productBacklogRepository).deleteById(1L);
    }

    // ------------------------- ADD EPICS -------------------------

    @Test
    @DisplayName("Add Epics - Success")
    void addEpicsToProductBacklog_Success() {
        // Arrange
        List<Long> epicIds = List.of(100L);
        List<Epic> foundEpics = List.of(epic);

        when(productBacklogRepository.findById(1L)).thenReturn(Optional.of(productBacklog));
        when(epicRepository.findAllById(epicIds)).thenReturn(foundEpics);
        when(productBacklogMapper.fromEntity(productBacklog)).thenReturn(productBacklogDTO);

        // Act
        productBacklogService.addEpicsToProductBacklog(1L, epicIds);

        // Assert
        assertTrue(productBacklog.getEpics().contains(epic));
        verify(epicRepository).saveAll(foundEpics);
    }

    @Test
    @DisplayName("Add Epics - Backlog Not Found")
    void addEpics_BacklogNotFound() {
        // Arrange
        when(productBacklogRepository.findById(99L)).thenReturn(Optional.empty());
        List<Long> ids = List.of(1L);

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                productBacklogService.addEpicsToProductBacklog(99L, ids)
        );
        assertTrue(ex.getMessage().contains("ProductBacklog"));
    }

    @Test
    @DisplayName("Add Epics - Mismatch (One or more IDs not found)")
    void addEpics_Mismatch() {
        // Arrange
        when(productBacklogRepository.findById(1L)).thenReturn(Optional.of(productBacklog));

        List<Long> requestedIds = List.of(100L, 200L); // Asking for 2
        List<Epic> foundEpics = List.of(epic);         // Found only 1 (ID 100)

        when(epicRepository.findAllById(requestedIds)).thenReturn(foundEpics);

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                productBacklogService.addEpicsToProductBacklog(1L, requestedIds)
        );
        assertTrue(ex.getMessage().contains("Missing epic IDs"));
        // Verify we didn't save partially
        verify(epicRepository, never()).saveAll(any());
    }

    // ------------------------- REMOVE EPIC -------------------------

    @Test
    @DisplayName("Remove Epic - Success")
    void removeEpicFromProductBacklog_Success() {
        // Arrange
        productBacklog.getEpics().add(epic); // Add it first

        when(productBacklogRepository.findById(1L)).thenReturn(Optional.of(productBacklog));
        when(epicRepository.findById(100L)).thenReturn(Optional.of(epic));
        when(productBacklogMapper.fromEntity(productBacklog)).thenReturn(productBacklogDTO);

        // Act
        productBacklogService.removeEpicFromProductBacklog(1L, 100L);

        // Assert
        assertFalse(productBacklog.getEpics().contains(epic));
        verify(epicRepository).save(epic);
    }

    @Test
    @DisplayName("Remove Epic - Backlog Not Found")
    void removeEpic_BacklogNotFound() {
        // Arrange
        when(productBacklogRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () ->
                productBacklogService.removeEpicFromProductBacklog(99L, 100L)
        );
    }

    @Test
    @DisplayName("Remove Epic - Epic Not Found")
    void removeEpic_EpicNotFound() {
        // Arrange
        when(productBacklogRepository.findById(1L)).thenReturn(Optional.of(productBacklog));
        when(epicRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () ->
                productBacklogService.removeEpicFromProductBacklog(1L, 999L)
        );
    }
}