package com.ensa.miniproject.services.userstory;

import com.ensa.miniproject.dto.TaskDTO;
import com.ensa.miniproject.dto.UserStoryDTO;
import com.ensa.miniproject.entities.Etat;
import com.ensa.miniproject.entities.Priorite;
import com.ensa.miniproject.entities.Task;
import com.ensa.miniproject.entities.UserStory;
import com.ensa.miniproject.mapping.TaskMapper;
import com.ensa.miniproject.mapping.UserStoryMapper;
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
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserStoryServiceImplTest {

    @Mock
    private UserStoryRepository userStoryRepository;
    @Mock
    private UserStoryMapper userStoryMapper;
    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private UserStoryServiceImpl userStoryService;

    private UserStory userStory;
    private UserStoryDTO userStoryDTO;
    private Task task;
    private TaskDTO taskDTO;

    @BeforeEach
    void setUp() {
        // Setup Task
        task = new Task();
        task.setId(100L);
        task.setEtat(Etat.EN_COURS);

        taskDTO = new TaskDTO(100L, "Implement Login", "Desc", null, Etat.EN_COURS, null);

        // Setup UserStory Entity
        userStory = new UserStory();
        userStory.setId(1L);
        userStory.setTitle("Login Feature");
        userStory.setEtat(Etat.EN_ATTENTE);
        userStory.setPriority(Priorite.MUST_HAVE);
        // IMPORTANT: Initialize as ArrayList to ensure it is mutable for addTask/removeTask tests
        userStory.setTasks(new ArrayList<>());

        // Setup UserStory DTO
        userStoryDTO = new UserStoryDTO(
                1L,
                "Login Feature",
                "Description",
                Priorite.MUST_HAVE,
                0L,
                Etat.EN_ATTENTE,
                new ArrayList<>()
        );
    }

    // ------------------------- CRUD OPERATIONS -------------------------

    @Test
    @DisplayName("Create UserStory")
    void createUserStoryTest() {
        // Arrange
        when(userStoryMapper.toEntity(userStoryDTO)).thenReturn(userStory);
        when(userStoryRepository.save(userStory)).thenReturn(userStory);
        when(userStoryMapper.fromEntity(userStory)).thenReturn(userStoryDTO);

        // Act
        UserStoryDTO result = userStoryService.createUserStory(userStoryDTO);

        // Assert
        assertNotNull(result);
        assertEquals(userStoryDTO.title(), result.title());
        verify(userStoryRepository).save(userStory);
    }

    @Test
    @DisplayName("Get UserStory By ID - Found")
    void getUserStoryByIdFoundTest() {
        // Arrange
        when(userStoryRepository.findById(1L)).thenReturn(Optional.of(userStory));
        when(userStoryMapper.fromEntity(userStory)).thenReturn(userStoryDTO);

        // Act
        UserStoryDTO result = userStoryService.getUserStoryById(1L);

        // Assert
        assertEquals(1L, result.id());
    }

    @Test
    @DisplayName("Get UserStory By ID - Not Found")
    void getUserStoryByIdNotFoundTest() {
        // Arrange
        when(userStoryRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userStoryService.getUserStoryById(99L));
    }

    @Test
    @DisplayName("Update UserStory - Success")
    void updateUserStoryTest() {
        // Arrange
        when(userStoryRepository.findById(userStoryDTO.id())).thenReturn(Optional.of(userStory));
        when(userStoryRepository.save(userStory)).thenReturn(userStory);
        when(userStoryMapper.fromEntity(userStory)).thenReturn(userStoryDTO);

        // Act
        UserStoryDTO result = userStoryService.updateUserStory(userStoryDTO);

        // Assert
        assertNotNull(result);
        verify(userStoryRepository).save(userStory);
        assertEquals(userStoryDTO.title(), userStory.getTitle());
    }

    @Test
    @DisplayName("Update UserStory - Not Found")
    void updateUserStoryNotFoundTest() {
        // Arrange
        when(userStoryRepository.findById(userStoryDTO.id())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userStoryService.updateUserStory(userStoryDTO));
    }

    @Test
    @DisplayName("Delete UserStory - Success")
    void deleteUserStoryTest() {
        // Arrange
        when(userStoryRepository.findById(1L)).thenReturn(Optional.of(userStory));

        // Act
        userStoryService.deleteUserStory(1L);

        // Assert
        verify(userStoryRepository).delete(userStory);
    }

    @Test
    @DisplayName("Delete UserStory - Not Found")
    void deleteUserStoryNotFoundTest() {
        // Arrange
        when(userStoryRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userStoryService.deleteUserStory(99L));
    }

    @Test
    @DisplayName("Get All UserStories")
    void getAllUserStoriesTest() {
        // Arrange
        when(userStoryRepository.findAll()).thenReturn(List.of(userStory));
        when(userStoryMapper.fromEntity(userStory)).thenReturn(userStoryDTO);

        // Act
        List<UserStoryDTO> result = userStoryService.getUserStories();

        // Assert
        assertEquals(1, result.size());
    }

    // ------------------------- TASK MANAGEMENT -------------------------

    @Test
    @DisplayName("Add Task to UserStory - Success")
    void addTaskTest() {
        // Arrange
        when(userStoryRepository.findById(1L)).thenReturn(Optional.of(userStory));
        when(taskMapper.toEntity(taskDTO)).thenReturn(task);
        when(userStoryRepository.save(userStory)).thenReturn(userStory);

        // Act
        TaskDTO result = userStoryService.addTask(1L, taskDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, userStory.getTasks().size());
        assertEquals(task, userStory.getTasks().get(0));
        verify(userStoryRepository).save(userStory);
    }

    @Test
    @DisplayName("Add Task - UserStory Not Found")
    void addTaskNotFoundTest() {
        // Arrange
        when(userStoryRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userStoryService.addTask(99L, taskDTO));
    }

    @Test
    @DisplayName("Delete Task from UserStory - Success")
    void deleteTaskSuccessTest() {
        // Arrange
        userStory.getTasks().add(task); // Add task to be deleted
        when(userStoryRepository.findById(1L)).thenReturn(Optional.of(userStory));

        // Act
        userStoryService.deleteTask(1L, taskDTO);

        // Assert
        assertTrue(userStory.getTasks().isEmpty());
        verify(userStoryRepository).save(userStory);
    }

    @Test
    @DisplayName("Delete Task - Task Not Found in List")
    void deleteTaskNotFoundInListTest() {
        // Arrange
        when(userStoryRepository.findById(1L)).thenReturn(Optional.of(userStory));

        // Act & Assert
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                userStoryService.deleteTask(1L, taskDTO)
        );
        assertTrue(ex.getMessage().contains("Task not found with id"));
    }

    @Test
    @DisplayName("Delete Task - UserStory Not Found")
    void deleteTaskUserStoryNotFoundTest() {
        // Arrange
        when(userStoryRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userStoryService.deleteTask(99L, taskDTO));
    }

    // ------------------------- BUSINESS LOGIC (CALCULATION) -------------------------

    @Test
    @DisplayName("Calculer Taux - 0% (Empty List)")
    void calculerTauxEmptyListTest() {
        // Arrange
        when(userStoryRepository.findById(1L)).thenReturn(Optional.of(userStory));
        when(userStoryRepository.save(userStory)).thenReturn(userStory);
        when(userStoryMapper.fromEntity(userStory)).thenReturn(userStoryDTO);

        // Act
        userStoryService.calculerTauxDavancement(1L);

        // Assert
        assertEquals(0L, userStory.getTauxDavancenement());
    }

    @Test
    @DisplayName("Calculer Taux - 50% (1 Validated, 1 In Progress)")
    void calculerTauxCalculationTest() {
        // Arrange
        Task task1 = new Task();
        task1.setId(101L);
        task1.setEtat(Etat.VALIDEE);

        Task task2 = new Task();
        task2.setId(102L);
        task2.setEtat(Etat.EN_COURS);

        userStory.getTasks().add(task1);
        userStory.getTasks().add(task2);

        when(userStoryRepository.findById(1L)).thenReturn(Optional.of(userStory));
        when(userStoryRepository.save(userStory)).thenReturn(userStory);
        when(userStoryMapper.fromEntity(userStory)).thenReturn(userStoryDTO);

        // Act
        userStoryService.calculerTauxDavancement(1L);

        // Assert
        assertEquals(50L, userStory.getTauxDavancenement());
    }

    @Test
    @DisplayName("Calculer Taux - Ignore Tasks with Null State")
    void calculerTauxNullStateTest() {
        // Arrange
        Task task1 = new Task();
        task1.setId(101L);
        task1.setEtat(null); // Null state should be filtered out

        userStory.getTasks().add(task1);

        when(userStoryRepository.findById(1L)).thenReturn(Optional.of(userStory));
        when(userStoryRepository.save(userStory)).thenReturn(userStory);
        when(userStoryMapper.fromEntity(userStory)).thenReturn(userStoryDTO);

        // Act
        userStoryService.calculerTauxDavancement(1L);

        // Assert
        assertEquals(0L, userStory.getTauxDavancenement());
    }

    @Test
    @DisplayName("Calculer Taux - Not Found")
    void calculerTauxNotFoundTest() {
        when(userStoryRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userStoryService.calculerTauxDavancement(99L));
    }
}