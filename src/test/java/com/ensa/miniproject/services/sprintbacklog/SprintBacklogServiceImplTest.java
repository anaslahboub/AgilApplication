package com.ensa.miniproject.services.sprintbacklog;

import com.ensa.miniproject.dto.SprintBacklogDTO;
import com.ensa.miniproject.entities.*;
import com.ensa.miniproject.execptions.ResourceNotFoundException;
import com.ensa.miniproject.mapping.SprintBacklogMapper;
import com.ensa.miniproject.repository.EpicRepository;
import com.ensa.miniproject.repository.SprintBacklogRepository;
import com.ensa.miniproject.repository.UserStoryRepository;
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
class SprintBacklogServiceImplTest {

    @Mock
    private SprintBacklogRepository sprintBacklogRepository;
    @Mock
    private EpicRepository epicRepository;
    @Mock
    private UserStoryRepository userStoryRepository;
    @Mock
    private SprintBacklogMapper sprintBacklogMapper;

    @InjectMocks
    private SprintBacklogServiceImpl sprintBacklogService;

    private SprintBacklog sprintBacklog;
    private SprintBacklogDTO sprintBacklogDTO;
    private Epic epic;
    private UserStory userStory;

    @BeforeEach
    void setUp() {
        // Entities
        epic = new Epic();
        epic.setId(100L);
        epic.setTitle("Epic 1");

        userStory = new UserStory();
        userStory.setId(200L);
        userStory.setTitle("Story 1");

        sprintBacklog = new SprintBacklog();
        sprintBacklog.setId(1L);
        sprintBacklog.setTitle("Sprint Backlog 1");
        sprintBacklog.setDescription("Desc");
        sprintBacklog.setStatus(Status.TODO);
        sprintBacklog.setPriority(Priorite.MUST_HAVE);
        sprintBacklog.setGoal("Goal");
        // IMPORTANT: Initialize lists to avoid NPE in service logic
        sprintBacklog.setEpics(new ArrayList<>());
        sprintBacklog.setUserStories(new ArrayList<>());

        // DTO
        sprintBacklogDTO = new SprintBacklogDTO(
                1L, "Sprint Backlog 1", "Desc", Status.TODO, Priorite.MUST_HAVE, "Goal",
                new ArrayList<>(), new ArrayList<>()
        );
    }

    // ------------------------- CRUD -------------------------

    @Test
    @DisplayName("Create SprintBacklog - Success")
    void createSprintBacklogTest() {
        // Arrange
        when(sprintBacklogMapper.toEntity(any(SprintBacklogDTO.class))).thenReturn(sprintBacklog);
        when(sprintBacklogRepository.save(any(SprintBacklog.class))).thenReturn(sprintBacklog);
        when(sprintBacklogMapper.fromEntity(any(SprintBacklog.class))).thenReturn(sprintBacklogDTO);

        // Act
        SprintBacklogDTO result = sprintBacklogService.createSprintBacklog(sprintBacklogDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(sprintBacklogRepository).save(sprintBacklog);
    }

    @Test
    @DisplayName("Get SprintBacklog By ID - Success")
    void getSprintBacklogByIdSuccessTest() {
        // Arrange
        when(sprintBacklogRepository.findById(1L)).thenReturn(Optional.of(sprintBacklog));
        when(sprintBacklogMapper.fromEntity(sprintBacklog)).thenReturn(sprintBacklogDTO);

        // Act
        SprintBacklogDTO result = sprintBacklogService.getSprintBacklogById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    @DisplayName("Get SprintBacklog By ID - Not Found")
    void getSprintBacklogByIdNotFoundTest() {
        // Arrange
        when(sprintBacklogRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () ->
                sprintBacklogService.getSprintBacklogById(99L));
    }

    @Test
    @DisplayName("Update SprintBacklog - Success")
    void updateSprintBacklogSuccessTest() {
        // Arrange
        SprintBacklogDTO updateDto = new SprintBacklogDTO(
                1L, "Updated Title", "Updated Desc", Status.IN_PROGRESS, Priorite.WONT_HAVE, "New Goal", null, null
        );

        when(sprintBacklogRepository.findById(1L)).thenReturn(Optional.of(sprintBacklog));
        when(sprintBacklogRepository.save(sprintBacklog)).thenReturn(sprintBacklog);
        when(sprintBacklogMapper.fromEntity(sprintBacklog)).thenReturn(updateDto);

        // Act
        SprintBacklogDTO result = sprintBacklogService.updateSprintBacklog(updateDto);

        // Assert
        assertNotNull(result);
        // Verify entity was updated in memory before save
        assertEquals("Updated Title", sprintBacklog.getTitle());
        assertEquals("New Goal", sprintBacklog.getGoal());
        assertEquals(Status.IN_PROGRESS, sprintBacklog.getStatus());
    }

    @Test
    @DisplayName("Update SprintBacklog - Not Found")
    void updateSprintBacklogNotFoundTest() {
        // Arrange
        when(sprintBacklogRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () ->
                sprintBacklogService.updateSprintBacklog(sprintBacklogDTO));
    }

    @Test
    @DisplayName("Delete SprintBacklog - Success")
    void deleteSprintBacklogSuccessTest() {
        // Arrange
        when(sprintBacklogRepository.findById(1L)).thenReturn(Optional.of(sprintBacklog));

        // Act
        sprintBacklogService.deleteSprintBacklog(1L);

        // Assert
        verify(sprintBacklogRepository).delete(sprintBacklog);
    }

    @Test
    @DisplayName("Delete SprintBacklog - Not Found")
    void deleteSprintBacklogNotFoundTest() {
        // Arrange
        when(sprintBacklogRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () ->
                sprintBacklogService.deleteSprintBacklog(99L));
    }

    @Test
    @DisplayName("Get All SprintBacklogs")
    void getAllSprintBacklogsTest() {
        // Arrange
        when(sprintBacklogRepository.findAll()).thenReturn(List.of(sprintBacklog));
        when(sprintBacklogMapper.fromEntity(sprintBacklog)).thenReturn(sprintBacklogDTO);

        // Act
        List<SprintBacklogDTO> result = sprintBacklogService.getAllSprintBacklogs();

        // Assert
        assertEquals(1, result.size());
    }

    // ------------------------- EPICS MANAGEMENT -------------------------

    @Test
    @DisplayName("Add Epics to Sprint - Success")
    void addEpicsToSprintSuccessTest() {
        // Arrange
        List<Long> epicIds = List.of(100L);
        List<Epic> foundEpics = List.of(epic);

        when(sprintBacklogRepository.findById(1L)).thenReturn(Optional.of(sprintBacklog));
        when(epicRepository.findAllById(epicIds)).thenReturn(foundEpics);
        when(sprintBacklogMapper.fromEntity(sprintBacklog)).thenReturn(sprintBacklogDTO);

        // Act
        sprintBacklogService.addEpicsToSprint(1L, epicIds);

        // Assert
        assertTrue(sprintBacklog.getEpics().contains(epic));
        verify(epicRepository).saveAll(foundEpics);
    }

    @Test
    @DisplayName("Add Epics - Mismatch (ID missing)")
    void addEpicsToSprintMismatchTest() {
        // Arrange
        when(sprintBacklogRepository.findById(1L)).thenReturn(Optional.of(sprintBacklog));

        List<Long> requestedIds = List.of(100L, 999L); // Requesting 2 IDs
        List<Epic> foundEpics = List.of(epic);         // Finding only 1

        when(epicRepository.findAllById(requestedIds)).thenReturn(foundEpics);

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                sprintBacklogService.addEpicsToSprint(1L, requestedIds));

        assertTrue(ex.getMessage().contains("Missing epic IDs"));
    }

    @Test
    @DisplayName("Remove Epic from Sprint - Success")
    void removeEpicFromSprintSuccessTest() {
        // Arrange
        sprintBacklog.getEpics().add(epic); // Add first

        when(sprintBacklogRepository.findById(1L)).thenReturn(Optional.of(sprintBacklog));
        when(epicRepository.findById(100L)).thenReturn(Optional.of(epic));
        when(sprintBacklogMapper.fromEntity(sprintBacklog)).thenReturn(sprintBacklogDTO);

        // Act
        sprintBacklogService.removeEpicFromSprint(1L, 100L);

        // Assert
        assertFalse(sprintBacklog.getEpics().contains(epic));
        verify(epicRepository).save(epic);
    }

    @Test
    @DisplayName("Remove Epic - Sprint Not Found")
    void removeEpicSprintNotFoundTest() {
        // Arrange
        when(sprintBacklogRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () ->
                sprintBacklogService.removeEpicFromSprint(99L, 100L));
    }

    @Test
    @DisplayName("Remove Epic - Epic Not Found")
    void removeEpicEpicNotFoundTest() {
        // Arrange
        when(sprintBacklogRepository.findById(1L)).thenReturn(Optional.of(sprintBacklog));
        when(epicRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () ->
                sprintBacklogService.removeEpicFromSprint(1L, 999L));
    }

    // ------------------------- USER STORIES MANAGEMENT -------------------------

    @Test
    @DisplayName("Add UserStories to Sprint - Success")
    void addUserStoriesToSprintSuccessTest() {
        // Arrange
        List<Long> ids = List.of(200L);
        List<UserStory> foundStories = List.of(userStory);

        when(sprintBacklogRepository.findById(1L)).thenReturn(Optional.of(sprintBacklog));
        when(userStoryRepository.findAllById(ids)).thenReturn(foundStories);
        when(sprintBacklogMapper.fromEntity(sprintBacklog)).thenReturn(sprintBacklogDTO);

        // Act
        sprintBacklogService.addUserStoriesToSprint(1L, ids);

        // Assert
        assertTrue(sprintBacklog.getUserStories().contains(userStory));
        verify(userStoryRepository).saveAll(foundStories);
    }

    @Test
    @DisplayName("Add UserStories - Mismatch")
    void addUserStoriesMismatchTest() {
        // Arrange
        when(sprintBacklogRepository.findById(1L)).thenReturn(Optional.of(sprintBacklog));

        List<Long> requestedIds = List.of(200L, 999L);
        List<UserStory> foundStories = List.of(userStory);

        when(userStoryRepository.findAllById(requestedIds)).thenReturn(foundStories);

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                sprintBacklogService.addUserStoriesToSprint(1L, requestedIds));

        assertTrue(ex.getMessage().contains("Missing userstory IDs"));
    }

    @Test
    @DisplayName("Remove UserStory from Sprint - Success")
    void removeUserStoryFromSprintSuccessTest() {
        // Arrange
        sprintBacklog.getUserStories().add(userStory);

        when(sprintBacklogRepository.findById(1L)).thenReturn(Optional.of(sprintBacklog));
        when(userStoryRepository.findById(200L)).thenReturn(Optional.of(userStory));
        when(sprintBacklogMapper.fromEntity(sprintBacklog)).thenReturn(sprintBacklogDTO);

        // Act
        sprintBacklogService.removeUserStoryFromSprint(1L, 200L);

        // Assert
        assertFalse(sprintBacklog.getUserStories().contains(userStory));
        verify(userStoryRepository).save(userStory);
    }
}