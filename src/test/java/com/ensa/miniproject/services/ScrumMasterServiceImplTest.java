package com.ensa.miniproject.services;

import com.ensa.miniproject.DTO.ScrumMasterDTO;
import com.ensa.miniproject.entities.Project;
import com.ensa.miniproject.entities.ScrumMaster;
import com.ensa.miniproject.mapping.ScrumMasterMapper;
import com.ensa.miniproject.repository.ScrumMasterRepository;
import com.ensa.miniproject.services.ScrumMaster.ScrumMasterServiceImpl;
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
class ScrumMasterServiceImplTest {

    @Mock
    private ScrumMasterRepository scrumMasterRepository;

    @Mock
    private ScrumMasterMapper scrumMasterMapper;

    @InjectMocks
    private ScrumMasterServiceImpl scrumMasterService;

    private ScrumMaster scrumMaster;
    private ScrumMasterDTO scrumMasterDTO;
    private List<Project> projects;

    @BeforeEach
    void setUp() {
        // Setup test project
        Project project = new Project();
        project.setId(1L);
        project.setNom("Test Project");
        projects = List.of(project);

        // Setup ScrumMaster entity
        scrumMaster = new ScrumMaster();
        scrumMaster.setId(1L);
        scrumMaster.setUsername("scrumMaster1");
        scrumMaster.setPrenom("John");
        scrumMaster.setPassword("password123");
        scrumMaster.setEmail("john@example.com");

        // Setup ScrumMasterDTO record
        scrumMasterDTO = new ScrumMasterDTO(
                1L,
                "scrumMaster1",
                "John",
                "password123",
                "john@example.com"
        );
    }

    @Test
    @DisplayName("Create ScrumMaster - Should return saved DTO")
    void createScrumMaster_ShouldReturnSavedScrumMasterDTO() {
        // Arrange
        when(scrumMasterMapper.toEntity(any(ScrumMasterDTO.class))).thenReturn(scrumMaster);
        when(scrumMasterRepository.save(any(ScrumMaster.class))).thenReturn(scrumMaster);
        when(scrumMasterMapper.fromEntity(any(ScrumMaster.class))).thenReturn(scrumMasterDTO);

        // Act
        ScrumMasterDTO result = scrumMasterService.createScrumMaster(scrumMasterDTO);

        // Assert
        assertNotNull(result);
        assertEquals(scrumMasterDTO.id(), result.id());
        assertEquals(scrumMasterDTO.username(), result.username());

        verify(scrumMasterMapper).toEntity(scrumMasterDTO);
        verify(scrumMasterRepository).save(scrumMaster);
        verify(scrumMasterMapper).fromEntity(scrumMaster);
    }

    @Test
    @DisplayName("Get ScrumMaster by ID - When exists - Should return DTO")
    void getScrumMasterById_WhenExists_ShouldReturnScrumMasterDTO() {
        // Arrange
        when(scrumMasterRepository.findById(1L)).thenReturn(Optional.of(scrumMaster));
        when(scrumMasterMapper.fromEntity(any(ScrumMaster.class))).thenReturn(scrumMasterDTO);

        // Act
        ScrumMasterDTO result = scrumMasterService.getScrumMasterById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("scrumMaster1", result.username());
        verify(scrumMasterRepository).findById(1L);
    }

    @Test
    @DisplayName("Get ScrumMaster by ID - When not exists - Should throw exception")
    void getScrumMasterById_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(scrumMasterRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                scrumMasterService.getScrumMasterById(1L));
        verify(scrumMasterRepository).findById(1L);
        verify(scrumMasterMapper, never()).fromEntity(any());
    }

    @Test
    @DisplayName("Update ScrumMaster - Should return updated DTO")
    void updateScrumMaster_ShouldReturnUpdatedScrumMasterDTO() {
        // Arrange
        ScrumMasterDTO updatedDTO = new ScrumMasterDTO(
                1L,
                "updatedMaster",
                "Updated",
                "newPass",
                "updated@example.com"
        );

        when(scrumMasterRepository.findById(1L)).thenReturn(Optional.of(scrumMaster));
        when(scrumMasterRepository.save(any(ScrumMaster.class))).thenReturn(scrumMaster);
        when(scrumMasterMapper.fromEntity(any(ScrumMaster.class))).thenReturn(updatedDTO);

        // Act
        ScrumMasterDTO result = scrumMasterService.updateScrumMaster(updatedDTO);

        // Assert
        assertNotNull(result);
        assertEquals("updatedMaster", result.username());
        assertEquals("Updated", result.prenom());
        assertEquals("updated@example.com", result.email());

        verify(scrumMasterRepository).findById(1L);
        verify(scrumMasterRepository).save(scrumMaster);
    }

    @Test
    @DisplayName("Delete ScrumMaster - When exists - Should delete")
    void deleteScrumMaster_WhenExists_ShouldDelete() {
        // Arrange
        when(scrumMasterRepository.findById(1L)).thenReturn(Optional.of(scrumMaster));

        // Act
        scrumMasterService.deleteScrumMaster(1L);

        // Assert
        verify(scrumMasterRepository).findById(1L);
        verify(scrumMasterRepository).delete(scrumMaster);
    }

    @Test
    @DisplayName("Delete ScrumMaster - When not exists - Should throw exception")
    void deleteScrumMaster_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(scrumMasterRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                scrumMasterService.deleteScrumMaster(1L));
        verify(scrumMasterRepository).findById(1L);
        verify(scrumMasterRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Get all ScrumMasters - Should return list of DTOs")
    void getScrumMasters_ShouldReturnListOfScrumMasterDTOs() {
        // Arrange
        when(scrumMasterRepository.findAll()).thenReturn(List.of(scrumMaster));
        when(scrumMasterMapper.fromEntity(any(ScrumMaster.class))).thenReturn(scrumMasterDTO);

        // Act
        List<ScrumMasterDTO> results = scrumMasterService.getScrumMasters();

        // Assert
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(1L, results.get(0).id());
        verify(scrumMasterRepository).findAll();
    }

    @Test
    @DisplayName("INTENTIONAL FAILURE - Should show X in coverage")
    void intentionallyFailingTest() {
        // Setup
        when(scrumMasterRepository.findById(1L)).thenReturn(Optional.of(scrumMaster));
        when(scrumMasterMapper.fromEntity(any())).thenReturn(scrumMasterDTO);

        // This will intentionally fail
        ScrumMasterDTO result = scrumMasterService.getScrumMasterById(1L);
        assertEquals("wrongUsername", result.username());
    }
}