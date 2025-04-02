package com.ensa.miniproject.services;

import com.ensa.miniproject.DTO.EpicDTO;
import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.entities.ProductBacklog;
import com.ensa.miniproject.mapping.EpicMapper;
import com.ensa.miniproject.repository.EpicRepository;
import com.ensa.miniproject.services.Epic.EpicServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EpicServiceImplTest {

    @Mock
    private EpicRepository epicRepository;

    @Mock
    private EpicMapper epicMapper;

    @InjectMocks
    private EpicServiceImpl epicService;

    private Epic epic;
    private EpicDTO epicDTO;

    @BeforeEach
    void setUp() {
        ProductBacklog productBacklog = new ProductBacklog();
        productBacklog.setId(1L);

        epic = new Epic();
        epic.setId(1L);
        epic.setTitle("Test Epic");
        epic.setDescription("Test Description");

        epicDTO = new EpicDTO(
                1L,
                "Test Epic",
                "Test Description",
                null
        );
    }

    @Test
    void createEpic_ShouldReturnSavedEpicDTO() {
        // Arrange
        when(epicMapper.toEntity(any(EpicDTO.class))).thenReturn(epic);
        when(epicRepository.save(any(Epic.class))).thenReturn(epic);
        when(epicMapper.fromEntity(any(Epic.class))).thenReturn(epicDTO);

        // Act
        EpicDTO result = epicService.createEpic(epicDTO);

        // Assert
        assertNotNull(result);
        assertEquals(epicDTO.id(), result.id());
        assertEquals(epicDTO.title(), result.title());

        verify(epicMapper).toEntity(epicDTO);
        verify(epicRepository).save(epic);
        verify(epicMapper).fromEntity(epic);
    }

    @Test
    void getEpicById_WhenExists_ShouldReturnEpicDTO() {
        // Arrange
        when(epicRepository.findById(1L)).thenReturn(Optional.of(epic));
        when(epicMapper.fromEntity(any(Epic.class))).thenReturn(epicDTO);

        // Act
        EpicDTO result = epicService.getEpicById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(epicDTO.id(), result.id());
        verify(epicRepository).findById(1L);
    }

    @Test
    void getEpicById_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(epicRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> epicService.getEpicById(1L));
        verify(epicRepository).findById(1L);
        verify(epicMapper, never()).fromEntity(any());
    }

    @Test
    void updateEpic_WhenExists_ShouldReturnUpdatedEpicDTO() {
        // Arrange
        EpicDTO updatedDTO = new EpicDTO(
                1L,
                "Updated Epic",
                "Updated Description",
                null
        );

        when(epicRepository.findById(1L)).thenReturn(Optional.of(epic));
        when(epicRepository.save(any(Epic.class))).thenReturn(epic);
        when(epicMapper.fromEntity(any(Epic.class))).thenReturn(updatedDTO);

        // Act
        EpicDTO result = epicService.updateEpic(updatedDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Epic", result.title());
        assertEquals("Updated Description", result.description());

        verify(epicRepository).findById(1L);
        verify(epicRepository).save(epic);
    }

    @Test
    void deleteEpic_WhenExists_ShouldDelete() {
        // Arrange
        when(epicRepository.findById(1L)).thenReturn(Optional.of(epic));

        // Act
        epicService.deleteEpic(1L);

        // Assert
        verify(epicRepository).findById(1L);
        verify(epicRepository).delete(epic);
    }

    @Test
    void getEpics_ShouldReturnListOfEpicDTOs() {
        // Arrange
        List<Epic> epics = List.of(epic);
        when(epicRepository.findAll()).thenReturn(epics);
        when(epicMapper.fromEntity(any(Epic.class))).thenReturn(epicDTO);

        // Act
        List<EpicDTO> results = epicService.getEpics();

        // Assert
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(epicDTO.id(), results.get(0).id());

        verify(epicRepository).findAll();
        verify(epicMapper).fromEntity(epic);
    }

}