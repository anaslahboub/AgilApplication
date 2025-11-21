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

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SprintServiceImplTest {

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
        sprintBacklog.setId(100L);

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
        // Initialize inner object to avoid NPE in tests that don't set it
        sprintDto.setSprintBacklog(null);
    }

    // ------------------------- SAVE -------------------------

    @Test
    @DisplayName("Save Sprint")
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
    @DisplayName("Get All Sprints")
    void getAllSprintsTest() {
        // Arrange
        when(sprintRepository.findAll()).thenReturn(List.of(sprint));
        when(sprintMapper.mapToDto(sprint)).thenReturn(sprintDto);

        // Act
        List<SprintDto> result = sprintService.getAllSprints();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    // ------------------------- GET BY ID -------------------------

    @Test
    @DisplayName("Get Sprint By ID - Found")
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
    @DisplayName("Get Sprint By ID - Not Found")
    void getSprintByIdNotFoundTest() {
        // Arrange
        when(sprintRepository.findById(99)).thenReturn(Optional.empty());

        // Act
        Optional<SprintDto> result = sprintService.getSprintById(99);

        // Assert
        assertTrue(result.isEmpty());
    }

    // ------------------------- UPDATE (CRUCIAL) -------------------------

    @Test
    @DisplayName("Update Sprint - With New Backlog Association")
    void updateSprint_WithBacklog() {
        // Arrange
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
        // Verify backlog was set
        assertEquals(sprintBacklog, sprint.getSprintBacklog());
    }

    @Test
    @DisplayName("Update Sprint - Null Backlog in DTO (Should set null)")
    void updateSprint_NullBacklogDto() {
        // Arrange
        // DTO has null SprintBacklog field
        SprintDto updateDto = new SprintDto(1, "No Backlog", 10L, null);

        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));
        when(sprintRepository.save(sprint)).thenReturn(sprint);
        when(sprintMapper.mapToDto(sprint)).thenReturn(updateDto);

        // Act
        sprintService.updateSprint(1, updateDto);

        // Assert
        assertNull(sprint.getSprintBacklog());
    }

    @Test
    @DisplayName("Update Sprint - Backlog ID null in DTO (Should set null)")
    void updateSprint_BacklogIdNull() {
        // Arrange
        // DTO has a Backlog object, but its ID is null
        SprintBacklog emptyBacklog = new SprintBacklog(); // ID is null
        SprintDto updateDto = new SprintDto(1, "No Backlog ID", 10L, emptyBacklog);

        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));
        when(sprintRepository.save(sprint)).thenReturn(sprint);
        when(sprintMapper.mapToDto(sprint)).thenReturn(updateDto);

        // Act
        sprintService.updateSprint(1, updateDto);

        // Assert
        assertNull(sprint.getSprintBacklog());
    }

    @Test
    @DisplayName("Update Sprint - Not Found")
    void updateSprint_NotFound() {
        // Arrange
        when(sprintRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                sprintService.updateSprint(99, sprintDto)
        );
    }

    // ------------------------- DELETE -------------------------

    @Test
    @DisplayName("Delete Sprint")
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
    }

    @Test
    @DisplayName("Assign Sprint Backlog - Success")
    void assignSprintBacklogSuccessTest() {
        // Arrange
        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));
        when(sprintBacklogRepository.findById(100L)).thenReturn(Optional.of(sprintBacklog));
        when(sprintRepository.save(sprint)).thenReturn(sprint);
        when(sprintMapper.mapToDto(sprint)).thenReturn(sprintDto);

        // Act
        sprintService.assignSprintBacklog(1, 100L);

        // Assert
        assertEquals(sprintBacklog, sprint.getSprintBacklog());
    }

    @Test
    @DisplayName("Assign Sprint Backlog - Sprint Not Found")
    void assignSprintBacklogSprintNotFoundTest() {
        // Arrange
        when(sprintRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                sprintService.assignSprintBacklog(99, 100L)
        );
    }

    @Test
    @DisplayName("Assign Sprint Backlog - Backlog Not Found")
    void assignSprintBacklogBacklogNotFoundTest() {
        // Arrange
        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));
        when(sprintBacklogRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                sprintService.assignSprintBacklog(1, 999L)
        );
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
    @DisplayName("Is Sprint Active - Not Found (False)")
    void isSprintActiveNotFoundTest() {
        // Arrange
        when(sprintRepository.findById(99)).thenReturn(Optional.empty());

        // Act
        boolean isActive = sprintService.isSprintActive(99);

        // Assert
        assertFalse(isActive);
    }
    @Test
    @DisplayName("Is Sprint Active - Should return False when days is Null")
    void isSprintActive_DaysNullTest() {
        // Arrange
        sprint.setDays(null); // Cas 1 : getDays() est null
        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));

        // Act
        boolean isActive = sprintService.isSprintActive(1);

        // Assert
        assertFalse(isActive);
    }

    @Test
    @DisplayName("Is Sprint Active - Should return False when days is Zero")
    void isSprintActive_DaysZeroTest() {
        // Arrange
        sprint.setDays(0L); // Cas 2 : getDays() n'est pas null, mais n'est pas > 0
        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));

        // Act
        boolean isActive = sprintService.isSprintActive(1);

        // Assert
        assertFalse(isActive);
    }
}