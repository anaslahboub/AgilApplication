package com.ensa.miniproject.services.epic;

import com.ensa.miniproject.dto.EpicDTO;
import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.entities.UserStory;
import com.ensa.miniproject.execptions.ResourceNotFoundException;
import com.ensa.miniproject.mapping.EpicMapper;
import com.ensa.miniproject.repository.EpicRepository;
import com.ensa.miniproject.repository.UserStoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
class EpicServiceImplTest {

    @Mock
    private EpicRepository epicRepository;
    @Mock
    private EpicMapper epicMapper;
    @Mock
    private UserStoryRepository userStoryRepository;

    @InjectMocks
    private EpicServiceImpl epicService;

    private Epic epic;
    private EpicDTO epicDTO;
    private UserStory userStory;

    @BeforeEach
    void setUp() {
        // Setup UserStory
        userStory = new UserStory();
        userStory.setId(100L);
        userStory.setTitle("Story 1");

        // Setup Epic Entity
        epic = new Epic();
        epic.setId(1L);
        epic.setTitle("Epic Title");
        epic.setDescription("Epic Description");
        // IMPORTANT: Initialize list as ArrayList to allow modifications
        epic.setUserStories(new ArrayList<>());

        // Setup Epic DTO
        epicDTO = new EpicDTO(
                1L,
                "Epic Title",
                "Epic Description",
                new ArrayList<>()
        );
    }

    // ------------------------- CRUD -------------------------

    @Test
    @DisplayName("Test create Epic")
    void createEpicTest() {
        // Arrange
        when(epicMapper.toEntity(epicDTO)).thenReturn(epic);
        when(epicRepository.save(epic)).thenReturn(epic);
        when(epicMapper.fromEntity(epic)).thenReturn(epicDTO);

        // Act
        EpicDTO result = epicService.createEpic(epicDTO);

        // Assert
        assertNotNull(result);
        assertEquals(epicDTO.title(), result.title());
        verify(epicRepository).save(epic);
    }

    @Test
    @DisplayName("Test get Epic by ID - Found")
    void getEpicByIdFoundTest() {
        // Arrange
        when(epicRepository.findById(1L)).thenReturn(Optional.of(epic));
        when(epicMapper.fromEntity(epic)).thenReturn(epicDTO);

        // Act
        EpicDTO result = epicService.getEpicById(1L);

        // Assert
        assertEquals(1L, result.id());
    }

    @Test
    @DisplayName("Test get Epic by ID - Not Found")
    void getEpicByIdNotFoundTest() {
        // Arrange
        when(epicRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> epicService.getEpicById(99L));
    }

    @Test
    @DisplayName("Test update Epic - Success")
    void updateEpicSuccessTest() {
        // Arrange
        EpicDTO updateDto = new EpicDTO(1L, "New Title", "New Desc", null);

        when(epicRepository.findById(1L)).thenReturn(Optional.of(epic));
        when(epicRepository.save(any(Epic.class))).thenReturn(epic);
        when(epicMapper.fromEntity(any(Epic.class))).thenReturn(updateDto);

        // Act
        EpicDTO result = epicService.updateEpic(updateDto);

        // Assert
        assertNotNull(result);

        // Use ArgumentCaptor to verify setters
        ArgumentCaptor<Epic> epicCaptor = ArgumentCaptor.forClass(Epic.class);
        verify(epicRepository).save(epicCaptor.capture());

        assertEquals("New Title", epicCaptor.getValue().getTitle());
        assertEquals("New Desc", epicCaptor.getValue().getDescription());
    }

    @Test
    @DisplayName("Test update Epic - Not Found")
    void updateEpicNotFoundTest() {
        // Arrange
        when(epicRepository.findById(1L)).thenReturn(Optional.empty());
        EpicDTO updateDto = new EpicDTO(1L, "Title", "Desc", null);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> epicService.updateEpic(updateDto));
        verify(epicRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test delete Epic - Success")
    void deleteEpicSuccessTest() {
        // Arrange
        when(epicRepository.findById(1L)).thenReturn(Optional.of(epic));

        // Act
        epicService.deleteEpic(1L);

        // Assert
        verify(epicRepository).delete(epic);
    }

    @Test
    @DisplayName("Test delete Epic - Not Found")
    void deleteEpicNotFoundTest() {
        // Arrange
        when(epicRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> epicService.deleteEpic(99L));
    }

    @Test
    @DisplayName("Test get all Epics")
    void getEpicsTest() {
        // Arrange
        when(epicRepository.findAll()).thenReturn(List.of(epic));
        when(epicMapper.fromEntity(epic)).thenReturn(epicDTO);

        // Act
        List<EpicDTO> result = epicService.getEpics();

        // Assert
        assertEquals(1, result.size());
        verify(epicRepository).findAll();
    }

    // ------------------------- AFFECT USER STORIES -------------------------

    @Test
    @DisplayName("Affect UserStories - Success")
    void affectUserStoriesToEpicSuccessTest() {
        // Arrange
        List<Long> ids = List.of(100L);
        List<UserStory> stories = List.of(userStory);

        // Note: Service calls findById twice. Once at start, once at end.
        when(epicRepository.findById(1L)).thenReturn(Optional.of(epic));
        when(userStoryRepository.findAllById(ids)).thenReturn(stories);
        when(epicMapper.fromEntity(epic)).thenReturn(epicDTO);

        // Act
        epicService.affectUserStoriesToEpic(1L, ids);

        // Assert
        assertTrue(epic.getUserStories().contains(userStory));
        verify(userStoryRepository).saveAll(stories);
    }

    @Test
    @DisplayName("Affect UserStories - Epic Not Found")
    void affectUserStoriesEpicNotFoundTest() {
        // Arrange
        when(epicRepository.findById(99L)).thenReturn(Optional.empty());
        List<Long> ids = List.of(1L);

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                epicService.affectUserStoriesToEpic(99L, ids)
        );
        assertTrue(ex.getMessage().contains("epic"));
    }

    @Test
    @DisplayName("Affect UserStories - Mismatch (One or more IDs not found)")
    void affectUserStoriesMismatchTest() {
        // Arrange
        when(epicRepository.findById(1L)).thenReturn(Optional.of(epic));

        // We ask for 2 IDs, but repository returns only 1 story
        List<Long> ids = List.of(100L, 200L);
        List<UserStory> foundStories = List.of(userStory);

        when(userStoryRepository.findAllById(ids)).thenReturn(foundStories);

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                epicService.affectUserStoriesToEpic(1L, ids)
        );
        // Check the generic message from your service
        assertTrue(ex.getMessage().contains("UserStories not found"));
        verify(userStoryRepository, never()).saveAll(any());
    }

    // ------------------------- REMOVE USER STORY -------------------------

    @Test
    @DisplayName("Remove UserStory from Epic - Success")
    void removeUserStoryFromEpicSuccessTest() {
        // Arrange
        // Pre-fill the epic with a user story
        epic.getUserStories().add(userStory);

        when(epicRepository.findById(1L)).thenReturn(Optional.of(epic));
        when(userStoryRepository.findById(100L)).thenReturn(Optional.of(userStory));
        when(epicMapper.fromEntity(epic)).thenReturn(epicDTO);

        // Act
        epicService.removeUserStoryFromEpic(1L, 100L);

        // Assert
        // Verify the list is empty now (reference check)
        assertTrue(epic.getUserStories().isEmpty());
        verify(userStoryRepository).save(userStory);
    }

    @Test
    @DisplayName("Remove UserStory - Epic Not Found")
    void removeUserStoryEpicNotFoundTest() {
        // Arrange
        when(epicRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                epicService.removeUserStoryFromEpic(99L, 100L)
        );
        assertTrue(ex.getMessage().contains("epic"));
    }

    @Test
    @DisplayName("Remove UserStory - UserStory Not Found")
    void removeUserStoryNotFoundTest() {
        // Arrange
        when(epicRepository.findById(1L)).thenReturn(Optional.of(epic));
        when(userStoryRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                epicService.removeUserStoryFromEpic(1L, 999L)
        );
        assertTrue(ex.getMessage().contains("userstory"));
    }
}