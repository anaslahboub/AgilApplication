package com.ensa.miniproject.services.sprint;

import com.ensa.miniproject.dto.SprintDto;
import com.ensa.miniproject.entities.Sprint;
import com.ensa.miniproject.entities.SprintBacklog;
import com.ensa.miniproject.mapping.SprintMapper;
import com.ensa.miniproject.repository.SprintBacklogRepository;
import com.ensa.miniproject.repository.SprintRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SprintServiceImplTest {

    @Mock
    private SprintRepository sprintRepository;
    @Mock
    private SprintBacklogRepository sprintBacklogRepository;
    @Mock
    private SprintMapper sprintMapper;

    @InjectMocks
    private SprintServiceImpl sprintService;

    private Sprint sprint;
    private SprintDto sprintDto;
    private SprintBacklog sprintBacklog;

    @BeforeEach
    void setUp() {
        // Setup SprintBacklog
        sprintBacklog = new SprintBacklog();
        sprintBacklog.setId(100L); // Assuming ID is Long based on previous context

        // Setup Sprint Entity
        sprint = new Sprint();
        sprint.setId(1);
        sprint.setName("Sprint 1");
        sprint.setDays(14L);

        // Setup Sprint DTO
        sprintDto = new SprintDto();
        sprintDto.setId(1);
        sprintDto.setName("Sprint 1");
        sprintDto.setDays(14L);
    }

    // ------------------------- SAVE -------------------------

    @Test
    @DisplayName("Test Save Sprint")
    void saveSprintTest() {
        // Arrange
        when(sprintMapper.mapToEntity(sprintDto)).thenReturn(sprint);
        when(sprintRepository.save(sprint)).thenReturn(sprint);
        when(sprintMapper.mapToDto(sprint)).thenReturn(sprintDto);

        // Act
        SprintDto result = sprintService.saveSprint(sprintDto);

        // Assert
        assertNotNull(result);
        assertEquals("Sprint 1", result.getName());
        verify(sprintRepository).save(sprint);
    }

    // ------------------------- GET ALL -------------------------

    @Test
    @DisplayName("Test Get All Sprints")
    void getAllSprintsTest() {
        // Arrange
        when(sprintRepository.findAll()).thenReturn(List.of(sprint));
        when(sprintMapper.mapToDto(sprint)).thenReturn(sprintDto);

        // Act
        List<SprintDto> result = sprintService.getAllSprints();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(sprintRepository).findAll();
    }

    // ------------------------- GET BY ID -------------------------

    @Test
    @DisplayName("Test Get Sprint By ID - Found")
    void getSprintByIdFoundTest() {
        // Arrange
        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));
        when(sprintMapper.mapToDto(sprint)).thenReturn(sprintDto);

        // Act
        Optional<SprintDto> result = sprintService.getSprintById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    @DisplayName("Test Get Sprint By ID - Not Found")
    void getSprintByIdNotFoundTest() {
        // Arrange
        when(sprintRepository.findById(99)).thenReturn(Optional.empty());

        // Act
        Optional<SprintDto> result = sprintService.getSprintById(99);

        // Assert
        assertTrue(result.isEmpty());
    }

    // ------------------------- UPDATE -------------------------

    @Test
    @DisplayName("Test Update Sprint - Basic Fields Success")
    void updateSprintSuccessTest() {
        // Arrange
        SprintDto updateDto = new SprintDto(1, "Updated Name", 20L, new SprintBacklog()); // Empty backlog for basic test

        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));
        when(sprintRepository.save(sprint)).thenReturn(sprint);
        when(sprintMapper.mapToDto(sprint)).thenReturn(updateDto);

        // Act
        SprintDto result = sprintService.updateSprint(1, updateDto);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());

        // Verify entity was actually updated
        assertEquals("Updated Name", sprint.getName());
        assertEquals(20L, sprint.getDays());
    }

    @Test
    @DisplayName("Test Update Sprint - With Backlog Association")
    void updateSprintWithBacklogTest() {
        // Arrange
        // Create a DTO that has a SprintBacklog inside it
        SprintBacklog backlogInDto = new SprintBacklog();
        backlogInDto.setId(100L);

        SprintDto updateDto = new SprintDto(1, "Sprint With Backlog", 10L, backlogInDto);

        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));
        when(sprintBacklogRepository.findById(100L)).thenReturn(Optional.of(sprintBacklog));
        when(sprintRepository.save(sprint)).thenReturn(sprint);
        when(sprintMapper.mapToDto(sprint)).thenReturn(updateDto);

        // Act
        sprintService.updateSprint(1, updateDto);

        // Assert
        // Verify that the service fetched the backlog and set it on the sprint
        verify(sprintBacklogRepository).findById(100L);
        assertEquals(sprintBacklog, sprint.getSprintBacklog());
    }

    @Test
    @DisplayName("Test Update Sprint - Not Found Exception")
    void updateSprintNotFoundTest() {
        // Arrange
        when(sprintRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                sprintService.updateSprint(99, sprintDto)
        );
        assertTrue(ex.getMessage().contains("Sprint not found"));
    }

    // ------------------------- DELETE -------------------------

    @Test
    @DisplayName("Test Delete Sprint")
    void deleteSprintTest() {
        // Act
        sprintService.deleteSprint(1);

        // Assert
        verify(sprintRepository).deleteById(1);
    }

    // ------------------------- BUSINESS LOGIC -------------------------

    @Test
    @DisplayName("Find Sprints by Duration")
    void findSprintsByDurationTest() {
        // Arrange
        Long duration = 14L;
        when(sprintRepository.findByDays(duration)).thenReturn(List.of(sprint));
        when(sprintMapper.mapToDto(sprint)).thenReturn(sprintDto);

        // Act
        List<SprintDto> result = sprintService.findSprintsByDuration(duration);

        // Assert
        assertEquals(1, result.size());
        assertEquals(14L, result.get(0).getDays());
    }

    @Test
    @DisplayName("Assign Sprint Backlog - Success")
    void assignSprintBacklogSuccessTest() {
        // Arrange
        int sprintId = 1;
        Long backlogId = 100L;

        when(sprintRepository.findById(sprintId)).thenReturn(Optional.of(sprint));
        when(sprintBacklogRepository.findById(backlogId)).thenReturn(Optional.of(sprintBacklog));
        when(sprintRepository.save(sprint)).thenReturn(sprint);
        when(sprintMapper.mapToDto(sprint)).thenReturn(sprintDto);

        // Act
        sprintService.assignSprintBacklog(sprintId, backlogId);

        // Assert
        assertEquals(sprintBacklog, sprint.getSprintBacklog());
        verify(sprintRepository).save(sprint);
    }

    @Test
    @DisplayName("Assign Sprint Backlog - Sprint Not Found")
    void assignSprintBacklogSprintNotFoundTest() {
        // Arrange
        when(sprintRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                sprintService.assignSprintBacklog(99, 100L)
        );
        assertTrue(ex.getMessage().contains("Sprint not found"));
    }

    @Test
    @DisplayName("Assign Sprint Backlog - Backlog Not Found")
    void assignSprintBacklogBacklogNotFoundTest() {
        // Arrange
        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));
        when(sprintBacklogRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                sprintService.assignSprintBacklog(1, 999L)
        );
        assertTrue(ex.getMessage().contains("sprintbacklog not found"));
    }

    @Test
    @DisplayName("Get Sprints Without Backlog")
    void getSprintsWithoutBacklogTest() {
        // Arrange
        when(sprintRepository.findBySprintBacklogIsNull()).thenReturn(List.of(sprint));
        when(sprintMapper.mapToDto(sprint)).thenReturn(sprintDto);

        // Act
        List<SprintDto> result = sprintService.getSprintsWithoutBacklog();

        // Assert
        assertEquals(1, result.size());
        verify(sprintRepository).findBySprintBacklogIsNull();
    }

    @Test
    @DisplayName("Is Sprint Active - True")
    void isSprintActiveTrueTest() {
        // Arrange
        sprint.setDays(5L);
        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));

        // Act
        boolean isActive = sprintService.isSprintActive(1);

        // Assert
        assertTrue(isActive);
    }

    @Test
    @DisplayName("Is Sprint Active - False (Days is 0)")
    void isSprintActiveFalseDaysTest() {
        // Arrange
        sprint.setDays(0L);
        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));

        // Act
        boolean isActive = sprintService.isSprintActive(1);

        // Assert
        assertFalse(isActive);
    }

    @Test
    @DisplayName("Is Sprint Active - False (Not Found)")
    void isSprintActiveNotFoundTest() {
        // Arrange
        when(sprintRepository.findById(99)).thenReturn(Optional.empty());

        // Act
        boolean isActive = sprintService.isSprintActive(99);

        // Assert
        assertFalse(isActive);
    }
}