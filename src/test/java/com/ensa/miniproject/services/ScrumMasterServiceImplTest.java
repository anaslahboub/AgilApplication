package com.ensa.miniproject.services;

import com.ensa.miniproject.DTO.ScrumMasterDTO;
import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.entities.Priorite;
import com.ensa.miniproject.entities.ScrumMaster;
import com.ensa.miniproject.entities.UserStory;
import com.ensa.miniproject.repository.EpicRepository;
import com.ensa.miniproject.repository.ScrumMasterRepository;
import com.ensa.miniproject.repository.UserStoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Enables Mockito in JUnit 5
class ScrumMasterServiceImplTest {

    @Mock
    private ScrumMasterRepository scrumMasterRepository;

    @Mock
    private UserStoryRepository userStoryRepository;

    @Mock
    private EpicRepository epicRepository;

    @InjectMocks
    private ScrumMasterServiceImpl scrumMasterService;

    private ScrumMasterDTO scrumMasterDTO;
    private UserStory userStory;
    private Epic epic;

    @BeforeEach
    void setUp() {
        scrumMasterDTO = new ScrumMasterDTO();
        scrumMasterDTO.setId(1L);

        userStory = new UserStory();
        userStory.setId(1L);

        epic = new Epic();
        epic.setId(1L);
    }

    @Test
    void testCreateScrumMaster() {
        when(scrumMasterRepository.save(scrumMaster)).thenReturn(scrumMaster);

        ScrumMaster savedScrumMaster = scrumMasterService.createScrumMaster(scrumMaster);

        assertNotNull(savedScrumMaster);
        assertEquals(1L, savedScrumMaster.getId());
        verify(scrumMasterRepository, times(1)).save(scrumMaster);

        System.out.println("✅ testCreateScrumMaster passed!");
    }

    @Test
    void testGetScrumMasterById_Success() {
        when(scrumMasterRepository.findById(1L)).thenReturn(Optional.of(scrumMaster));

        ScrumMaster found = scrumMasterService.getScrumMasterById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
        verify(scrumMasterRepository, times(1)).findById(1L);

        System.out.println("✅ testGetScrumMasterById_Success passed!");
    }

    @Test
    void testGetScrumMasterById_NotFound() {
        when(scrumMasterRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> scrumMasterService.getScrumMasterById(1L));

        verify(scrumMasterRepository, times(1)).findById(1L);

        System.out.println("✅ testGetScrumMasterById_NotFound passed! (Exception expected)");
    }

    @Test
    void testUpdateScrumMaster() {
        when(scrumMasterRepository.save(scrumMaster)).thenReturn(scrumMaster);

        ScrumMaster updated = scrumMasterService.updateScrumMaster(scrumMaster);

        assertNotNull(updated);
        verify(scrumMasterRepository, times(1)).save(scrumMaster);

        System.out.println("✅ testUpdateScrumMaster passed!");
    }

    @Test
    void testDeleteScrumMaster() {
        when(scrumMasterRepository.findById(1L)).thenReturn(Optional.of(scrumMaster));
        doNothing().when(scrumMasterRepository).delete(scrumMaster);

        scrumMasterService.deleteScrumMaster(1L);

        verify(scrumMasterRepository, times(1)).delete(scrumMaster);

        System.out.println("✅ testDeleteScrumMaster passed!");
    }

    @Test
    void testCreateUserStory() {
        when(userStoryRepository.save(userStory)).thenReturn(userStory);

        UserStory savedStory = scrumMasterService.createUserStory(userStory);

        assertNotNull(savedStory);
        assertEquals(1L, savedStory.getId());
        verify(userStoryRepository, times(1)).save(userStory);

        System.out.println("✅ testCreateUserStory passed!");
    }

    @Test
    void testAssignEpicToUserStory() {
        when(userStoryRepository.findById(1L)).thenReturn(Optional.of(userStory));

        scrumMasterService.affectUserStoryToEpic(1L, epic);

        assertEquals(epic, userStory.getEpic()); // Ensure the user story is assigned to the epic
        verify(userStoryRepository, times(1)).findById(1L);

        System.out.println("✅ testAssignEpicToUserStory passed!");
    }
}
