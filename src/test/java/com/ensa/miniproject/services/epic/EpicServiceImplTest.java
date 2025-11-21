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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        // IMPORTANT: Initialize list as ArrayList to allow modifications (add/remove)
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
        when(epicRepository.save(epic)).thenReturn(epic);
        when(epicMapper.fromEntity(epic)).thenReturn(updateDto);

        // Act
        EpicDTO result = epicService.updateEpic(updateDto);

        // Assert
        assertNotNull(result);
        assertEquals("New Title", result.title());
        // Verify internal state change
        assertEquals("New Title", epic.getTitle());
        verify(epicRepository).save(epic);
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

    // ------------------------- AFFECT USER STORIES -------------------------


    @Test
    @DisplayName("Affect UserStories - Epic Not Found")
    void affectUserStoriesEpicNotFoundTest() {
        // Arrange
        when(epicRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                epicService.affectUserStoriesToEpic(99L, List.of(1L))
        );
        assertTrue(ex.getMessage().contains("epic"));
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
        // Verify the list is empty now
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