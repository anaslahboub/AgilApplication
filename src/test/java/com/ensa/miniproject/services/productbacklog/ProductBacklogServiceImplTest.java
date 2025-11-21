package com.ensa.miniproject.services.productbacklog;

import com.ensa.miniproject.dto.ProductBacklogDTO;
import com.ensa.miniproject.entities.ProductBacklog;
import com.ensa.miniproject.mapping.ProductBacklogMapper;
import com.ensa.miniproject.repository.ProductBacklogRepository;
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
class ProductBacklogServiceImplTest {

    @Mock
    private ProductBacklogRepository productBacklogRepository;

    @Mock
    private ProductBacklogMapper productBacklogMapper;

    @InjectMocks
    private ProductBacklogServiceImpl productBacklogService;

    private ProductBacklog productBacklog;
    private ProductBacklogDTO productBacklogDTO;

    @BeforeEach
    void setUp() {
        productBacklog = new ProductBacklog();
        productBacklog.setId(1L);
        productBacklog.setTitle("Test Product Backlog");
        productBacklog.setDescription("Test Description");

        // Create record-based DTO
        productBacklogDTO = new ProductBacklogDTO(
                1L,
                "Test Product Backlog",
                "Test Description",
                null,  // status
                null,  // priority
                null ,
                null// epics
        );
    }

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
        assertEquals(productBacklogDTO.title(), result.title());
        assertEquals(productBacklogDTO.description(), result.description());

        verify(productBacklogMapper).toEntity(productBacklogDTO);
        verify(productBacklogRepository).save(productBacklog);
        verify(productBacklogMapper).fromEntity(productBacklog);
    }

    @Test
    @DisplayName("Get by ID - When exists - Should return DTO")
    void getProductBacklogById_WhenExists_ShouldReturnProductBacklogDTO() {
        // Arrange
        when(productBacklogRepository.findById(1L)).thenReturn(Optional.of(productBacklog));
        when(productBacklogMapper.fromEntity(any(ProductBacklog.class))).thenReturn(productBacklogDTO);

        // Act
        ProductBacklogDTO result = productBacklogService.getProductBacklogById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Test Product Backlog", result.title());
        verify(productBacklogRepository).findById(1L);
    }

    @Test
    @DisplayName("Get by ID - When not exists - Should throw exception")
    void getProductBacklogById_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(productBacklogRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                productBacklogService.getProductBacklogById(1L));
        verify(productBacklogRepository).findById(1L);
        verify(productBacklogMapper, never()).fromEntity(any());
    }

    @Test
    @DisplayName("Get all ProductBacklogs - Should return list of DTOs")
    void getProductBacklogs_ShouldReturnListOfProductBacklogDTOs() {
        // Arrange
        List<ProductBacklog> productBacklogs = List.of(productBacklog);
        when(productBacklogRepository.findAll()).thenReturn(productBacklogs);
        when(productBacklogMapper.fromEntity(any(ProductBacklog.class))).thenReturn(productBacklogDTO);

        // Act
        List<ProductBacklogDTO> results = productBacklogService.getProductBacklogs();

        // Assert
        assertEquals(1, results.size());
        ProductBacklogDTO firstResult = results.get(0);
        assertEquals(1L, firstResult.id());
        assertEquals("Test Product Backlog", firstResult.title());

        verify(productBacklogRepository).findAll();
        verify(productBacklogMapper).fromEntity(productBacklog);
    }

    @Test
    @DisplayName("Delete ProductBacklog - Should call repository delete")
    void deleteProductBacklog_ShouldCallRepositoryDelete() {
        // Act
        productBacklogService.deleteProductBacklog(1L);

        // Assert
        verify(productBacklogRepository).deleteById(1L);
    }

}