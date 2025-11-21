package com.ensa.miniproject.services.userstoryclone;

import com.ensa.miniproject.dto.UserStoryCloneDTO;
import com.ensa.miniproject.entities.Etat;
import com.ensa.miniproject.entities.Priorite;
import com.ensa.miniproject.entities.UserStory;
import com.ensa.miniproject.entities.UserStoryClone;
import com.ensa.miniproject.mapping.UserStoryCloneMapper;
import com.ensa.miniproject.repository.UserStoryCloneRepository;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserStoryCloneServiceImplTest {

    @Mock
    private UserStoryCloneRepository userStoryCloneRepository;
    @Mock
    private UserStoryRepository userStoryRepository;
    @Mock
    private UserStoryCloneMapper userStoryCloneMapper;

    @InjectMocks
    private UserStoryCloneServiceImpl userStoryCloneService;

    private UserStoryClone userStoryClone;
    private UserStoryCloneDTO userStoryCloneDTO;
    private UserStory originalUserStory;

    @BeforeEach
    void setUp() {
        // Entity
        userStoryClone = new UserStoryClone();
        userStoryClone.setId(1L);
        userStoryClone.setTitle("Clone Title");
        userStoryClone.setDescription("Clone Desc");
        userStoryClone.setEtat(Etat.EN_ATTENTE);
        userStoryClone.setPriority(Priorite.COULD_HAVE);

        // Original User Story
        originalUserStory = new UserStory();
        originalUserStory.setId(100L);
        originalUserStory.setTitle("Original Story");

        // DTO
        userStoryCloneDTO = new UserStoryCloneDTO(
                1L, "Clone Title", "Clone Desc", "As user", "I want", "So that",
                Etat.EN_ATTENTE, 0L, Priorite.COULD_HAVE, null, null
        );
    }

    // ------------------------- CRUD -------------------------

    @Test
    @DisplayName("Create Clone - Success")
    void createUserStoryCloneTest() {
        // Arrange
        when(userStoryCloneMapper.toEntity(userStoryCloneDTO)).thenReturn(userStoryClone);
        when(userStoryCloneRepository.save(userStoryClone)).thenReturn(userStoryClone);
        when(userStoryCloneMapper.fromEntity(userStoryClone)).thenReturn(userStoryCloneDTO);

        // Act
        UserStoryCloneDTO result = userStoryCloneService.createUserStoryClone(userStoryCloneDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(userStoryCloneRepository).save(userStoryClone);
    }

    @Test
    @DisplayName("Get Clone By ID - Success")
    void getUserStoryCloneByIdSuccessTest() {
        // Arrange
        when(userStoryCloneRepository.findById(1L)).thenReturn(Optional.of(userStoryClone));
        when(userStoryCloneMapper.fromEntity(userStoryClone)).thenReturn(userStoryCloneDTO);

        // Act
        UserStoryCloneDTO result = userStoryCloneService.getUserStoryCloneById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    @DisplayName("Get Clone By ID - Not Found")
    void getUserStoryCloneByIdNotFoundTest() {
        // Arrange
        when(userStoryCloneRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                userStoryCloneService.getUserStoryCloneById(99L));
    }

    @Test
    @DisplayName("Update Clone - Success")
    void updateUserStoryCloneSuccessTest() {
        // Arrange
        UserStoryCloneDTO updateDto = new UserStoryCloneDTO(
                1L, "Updated Title", "Updated Desc", "New As", "New Want", "New So",
                Etat.EN_COURS, 50L, Priorite.MUST_HAVE, null, null
        );

        when(userStoryCloneRepository.findById(1L)).thenReturn(Optional.of(userStoryClone));
        when(userStoryCloneRepository.save(userStoryClone)).thenReturn(userStoryClone);
        when(userStoryCloneMapper.fromEntity(userStoryClone)).thenReturn(updateDto);

        // Act
        UserStoryCloneDTO result = userStoryCloneService.updateUserStoryClone(updateDto);

        // Assert
        assertNotNull(result);
        // Capture the entity saved to verify setters were called
        ArgumentCaptor<UserStoryClone> captor = ArgumentCaptor.forClass(UserStoryClone.class);
        verify(userStoryCloneRepository).save(captor.capture());

        UserStoryClone savedClone = captor.getValue();
        assertEquals("Updated Title", savedClone.getTitle());
        assertEquals("New As", savedClone.getEnTantQue());
        assertEquals(Etat.EN_COURS, savedClone.getEtat());
    }

    @Test
    @DisplayName("Update Clone - Not Found")
    void updateUserStoryCloneNotFoundTest() {
        // Arrange
        when(userStoryCloneRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                userStoryCloneService.updateUserStoryClone(userStoryCloneDTO));
    }

    @Test
    @DisplayName("Delete Clone - Success")
    void deleteUserStoryCloneSuccessTest() {
        // Arrange
        when(userStoryCloneRepository.findById(1L)).thenReturn(Optional.of(userStoryClone));

        // Act
        userStoryCloneService.deleteUserStoryClone(1L);

        // Assert
        verify(userStoryCloneRepository).delete(userStoryClone);
    }

    @Test
    @DisplayName("Delete Clone - Not Found")
    void deleteUserStoryCloneNotFoundTest() {
        // Arrange
        when(userStoryCloneRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                userStoryCloneService.deleteUserStoryClone(99L));
    }

    @Test
    @DisplayName("Get All Clones")
    void getUserStoryClonesTest() {
        // Arrange
        when(userStoryCloneRepository.findAll()).thenReturn(List.of(userStoryClone));
        when(userStoryCloneMapper.fromEntity(userStoryClone)).thenReturn(userStoryCloneDTO);

        // Act
        List<UserStoryCloneDTO> result = userStoryCloneService.getUserStoryClones();

        // Assert
        assertEquals(1, result.size());
    }

    // ------------------------- AFFECTATION -------------------------

    @Test
    @DisplayName("Affect Clone to UserStory - Success")
    void affectToUserStorySuccessTest() {
        // Arrange
        when(userStoryCloneRepository.findById(1L)).thenReturn(Optional.of(userStoryClone));
        when(userStoryRepository.findById(100L)).thenReturn(Optional.of(originalUserStory));
        when(userStoryCloneRepository.save(userStoryClone)).thenReturn(userStoryClone);
        when(userStoryCloneMapper.fromEntity(userStoryClone)).thenReturn(userStoryCloneDTO);

        // Act
        userStoryCloneService.affectToUserStory(1L, 100L);

        // Assert
        assertEquals(originalUserStory, userStoryClone.getOriginalUserStory());
        verify(userStoryCloneRepository).save(userStoryClone);
    }

    @Test
    @DisplayName("Affect Clone - Clone Not Found")
    void affectToUserStoryCloneNotFoundTest() {
        // Arrange
        when(userStoryCloneRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                userStoryCloneService.affectToUserStory(99L, 100L));
    }

    @Test
    @DisplayName("Affect Clone - Original Story Not Found")
    void affectToUserStoryOriginalNotFoundTest() {
        // Arrange
        when(userStoryCloneRepository.findById(1L)).thenReturn(Optional.of(userStoryClone));
        when(userStoryRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                userStoryCloneService.affectToUserStory(1L, 999L));
    }

    // ------------------------- DELETE FROM USER STORY -------------------------

    @Test
    @DisplayName("Delete From UserStory (Detach) - Success")
    void deleteFromUserStorySuccessTest() {
        // Arrange
        userStoryClone.setOriginalUserStory(originalUserStory); // Pre-attach
        when(userStoryCloneRepository.findById(1L)).thenReturn(Optional.of(userStoryClone));
        when(userStoryCloneRepository.save(userStoryClone)).thenReturn(userStoryClone);

        // Act
        userStoryCloneService.deleteFromUserStory(1L);

        // Assert
        assertNull(userStoryClone.getOriginalUserStory());
        verify(userStoryCloneRepository).save(userStoryClone);
    }

    @Test
    @DisplayName("Delete From UserStory - Clone Not Found")
    void deleteFromUserStoryNotFoundTest() {
        // Arrange
        when(userStoryCloneRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                userStoryCloneService.deleteFromUserStory(99L));
    }
}