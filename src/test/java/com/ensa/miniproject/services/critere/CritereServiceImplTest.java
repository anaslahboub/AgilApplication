package com.ensa.miniproject.services.critere;

import com.ensa.miniproject.dto.CritereDTO;
import com.ensa.miniproject.entities.Critere;
import com.ensa.miniproject.mapping.CritereMapper;
import com.ensa.miniproject.repository.CritereRepository;
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
 class CritereServiceImplTest {

    @Mock
    private CritereRepository critereRepository;

    @Mock
    private CritereMapper critereMapper;

    @InjectMocks
    private CritereServiceImpl critereService;

    private Critere critere;
    private CritereDTO critereDTO;

    @BeforeEach
    void setUp() {
        // Setup Entity
        critere = new Critere();
        critere.setId(1L);
        critere.setScenario("Login successful");
        critere.setGiven("User is on login page");
        critere.setWhenn("User enters valid creds");
        critere.setThenn("Redirect to dashboard");
        critere.setAndd("Show welcome message");

        // Setup DTO (Record)
        critereDTO = new CritereDTO(
                1L,
                "Login successful",
                "User enters valid creds",
                "User is on login page",
                "Redirect to dashboard",
                "Show welcome message"
        );
    }

    // ------------------------- CREATE -------------------------

    @Test
    @DisplayName("Test create critere")
    void createCritereTest() {
        // Arrange
        when(critereMapper.toEntity(critereDTO)).thenReturn(critere);
        when(critereRepository.save(critere)).thenReturn(critere);
        when(critereMapper.fromEntity(critere)).thenReturn(critereDTO);

        // Act
        CritereDTO result = critereService.createCritere(critereDTO);

        // Assert
        assertNotNull(result);
        assertEquals(critereDTO.Scenario(), result.Scenario());
        verify(critereRepository).save(critere);
    }

    // ------------------------- GET BY ID -------------------------

    @Test
    @DisplayName("Test get critere by id - Found")
    void getCritereByIdFoundTest() {
        // Arrange
        when(critereRepository.findById(1L)).thenReturn(Optional.of(critere));
        when(critereMapper.fromEntity(critere)).thenReturn(critereDTO);

        // Act
        CritereDTO result = critereService.getCritereById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    @DisplayName("Test get critere by id - Not Found")
    void getCritereByIdNotFoundTest() {
        // Arrange
        when(critereRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            critereService.getCritereById(99L);
        });

        assertTrue(exception.getMessage().contains("Critere not found"));
    }

    // ------------------------- UPDATE -------------------------

    @Test
    @DisplayName("Test update critere - Success")
    void updateCritereSuccessTest() {
        // Arrange
        CritereDTO updateDto = new CritereDTO(
                1L,
                "Updated Scenario",
                "Updated When",
                "Updated Given",
                "Updated Then",
                "Updated And"
        );

        when(critereRepository.findById(1L)).thenReturn(Optional.of(critere));
        when(critereRepository.save(critere)).thenReturn(critere);
        when(critereMapper.fromEntity(critere)).thenReturn(updateDto);

        // Act
        CritereDTO result = critereService.updateCritere(updateDto);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Scenario", result.Scenario());

        // Verify that the entity was actually updated in memory before saving
        assertEquals("Updated Scenario", critere.getScenario());
        assertEquals("Updated Given", critere.getGiven());

        verify(critereRepository).save(critere);
    }

    @Test
    @DisplayName("Test update critere - Not Found")
    void updateCritereNotFoundTest() {
        // Arrange
        when(critereRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            critereService.updateCritere(critereDTO);
        });

        verify(critereRepository, never()).save(any());
    }

    // ------------------------- DELETE -------------------------

    @Test
    @DisplayName("Test delete critere - Success")
    void deleteCritereSuccessTest() {
        // Arrange
        when(critereRepository.findById(1L)).thenReturn(Optional.of(critere));

        // Act
        critereService.deleteCritere(1L);

        // Assert
        verify(critereRepository).delete(critere);
    }

    @Test
    @DisplayName("Test delete critere - Not Found")
    void deleteCritereNotFoundTest() {
        // Arrange
        when(critereRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            critereService.deleteCritere(99L);
        });

        verify(critereRepository, never()).delete(any());
    }

    // ------------------------- GET ALL -------------------------

    @Test
    @DisplayName("Test get all criteres")
    void getAllCriteresTest() {
        // Arrange
        when(critereRepository.findAll()).thenReturn(List.of(critere));
        when(critereMapper.fromEntity(critere)).thenReturn(critereDTO);

        // Act
        List<CritereDTO> result = critereService.getAllCriteres();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(critereDTO.Scenario(), result.get(0).Scenario());
        verify(critereRepository).findAll();
    }
}