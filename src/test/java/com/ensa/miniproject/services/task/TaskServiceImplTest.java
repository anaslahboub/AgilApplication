package com.ensa.miniproject.services.task;

import com.ensa.miniproject.dto.TaskDTO;
import com.ensa.miniproject.entities.Critere;
import com.ensa.miniproject.entities.Etat;
import com.ensa.miniproject.entities.Task;
import com.ensa.miniproject.mapping.TaskMapper;
import com.ensa.miniproject.repository.CritereRepository;
import com.ensa.miniproject.repository.TaskRepository;
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
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;
    @Mock
    private CritereRepository critereRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskDTO taskDTO;
    private Critere critere;

    @BeforeEach
    void setUp() {
        // Setup Critere
        critere = new Critere();
        critere.setId(50L);
        critere.setScenario("Scenario 1");

        // Setup Task Entity
        task = new Task();
        task.setId(1L);
        task.setTaskName("Implement Login");
        task.setDescription("Create login page");
        task.setEtat(Etat.EN_ATTENTE);

        // Setup Task DTO
        taskDTO = new TaskDTO(
                1L,
                "Implement Login",
                "Create login page",
                Etat.EN_ATTENTE,
                null,
                null // No critere initially
        );
    }

    // ------------------------- CRUD OPERATIONS -------------------------

    @Test
    @DisplayName("Create Task - Success")
    void createTaskTest() {
        // Arrange
        when(taskMapper.toEntity(taskDTO)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.fromEntity(task)).thenReturn(taskDTO);

        // Act
        TaskDTO result = taskService.createTask(taskDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Implement Login", result.task());
        verify(taskRepository).save(task);
    }

    @Test
    @DisplayName("Get Task By ID - Found")
    void getTaskByIdFoundTest() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.fromEntity(task)).thenReturn(taskDTO);

        // Act
        TaskDTO result = taskService.getTaskById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    @DisplayName("Get Task By ID - Not Found")
    void getTaskByIdNotFoundTest() {
        // Arrange
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> taskService.getTaskById(99L));
    }

    @Test
    @DisplayName("Update Task - Success")
    void updateTaskSuccessTest() {
        // Arrange
        TaskDTO updateDto = new TaskDTO(1L, "Updated Name", "Updated Desc", Etat.EN_ATTENTE, null, critere);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.fromEntity(task)).thenReturn(updateDto);

        // Act
        TaskDTO result = taskService.updateTask(updateDto);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.task());
        // Verify internal state
        assertEquals("Updated Name", task.getTaskName());
        assertEquals(critere, task.getCritere());
        verify(taskRepository).save(task);
    }

    @Test
    @DisplayName("Update Task - Not Found")
    void updateTaskNotFoundTest() {
        // Arrange
        // 1. On crée un DTO spécifique avec l'ID 99L pour correspondre au Mock
        TaskDTO notFoundDto = new TaskDTO(
                99L,
                "Any Name",
                "Any Desc",
                Etat.EN_ATTENTE,
                null,
                null
        );

        // 2. On configure le Mock pour l'ID 99L
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        // 3. On appelle le service avec le DTO qui contient l'ID 99L
        assertThrows(EntityNotFoundException.class, () -> taskService.updateTask(notFoundDto));
    }

    @Test
    @DisplayName("Delete Task - Success")
    void deleteTaskSuccessTest() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Act
        taskService.deleteTask(1L);

        // Assert
        verify(taskRepository).delete(task);
    }

    @Test
    @DisplayName("Delete Task - Not Found")
    void deleteTaskNotFoundTest() {
        // Arrange
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> taskService.deleteTask(99L));
    }

    @Test
    @DisplayName("Get All Tasks")
    void getAllTasksTest() {
        // Arrange
        when(taskRepository.findAll()).thenReturn(List.of(task));
        when(taskMapper.fromEntity(task)).thenReturn(taskDTO);

        // Act
        List<TaskDTO> result = taskService.getAllTasks();

        // Assert
        assertEquals(1, result.size());
    }

    // ------------------------- LINK CRITERE -------------------------

    @Test
    @DisplayName("Link Task to Critere - Success")
    void linkTaskToCritereSuccessTest() {
        // Arrange
        Long taskId = 1L;
        Long critereId = 50L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(critereRepository.findById(critereId)).thenReturn(Optional.of(critere));
        // Mock that no other task owns this critere
        when(taskRepository.findByCritereId(critereId)).thenReturn(null);

        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.fromEntity(task)).thenReturn(taskDTO);

        // Act
        taskService.linkTaskToCritere(taskId, critereId);

        // Assert
        assertEquals(critere, task.getCritere());
        verify(taskRepository).save(task);
    }

    @Test
    @DisplayName("Link Task to Critere - Already Linked to Other Task")
    void linkTaskToCritereAlreadyLinkedTest() {
        // Arrange
        Long taskId = 1L;
        Long critereId = 50L;

        Task otherTask = new Task();
        otherTask.setId(999L); // Different ID

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(critereRepository.findById(critereId)).thenReturn(Optional.of(critere));
        // Mock that another task DOES own this critere
        when(taskRepository.findByCritereId(critereId)).thenReturn(otherTask);

        // Act & Assert
        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                taskService.linkTaskToCritere(taskId, critereId)
        );
        assertEquals("Critere is already linked to another task", ex.getMessage());
        verify(taskRepository, never()).save(any());
    }

    @Test
    @DisplayName("Link Task to Critere - Idempotent (Same Task)")
    void linkTaskToCritereSameTaskTest() {
        // Arrange
        Long taskId = 1L;
        Long critereId = 50L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(critereRepository.findById(critereId)).thenReturn(Optional.of(critere));
        // Mock that THIS task already owns the critere
        when(taskRepository.findByCritereId(critereId)).thenReturn(task);

        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.fromEntity(task)).thenReturn(taskDTO);

        // Act
        taskService.linkTaskToCritere(taskId, critereId);

        // Assert
        verify(taskRepository).save(task); // Should proceed
    }

    @Test
    @DisplayName("Link Task - Task Not Found")
    void linkTaskNotFoundTest() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> taskService.linkTaskToCritere(99L, 50L));
    }

    @Test
    @DisplayName("Link Task - Critere Not Found")
    void linkCritereNotFoundTest() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(critereRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> taskService.linkTaskToCritere(1L, 99L));
    }

    // ------------------------- UNLINK CRITERE -------------------------

    @Test
    @DisplayName("Unlink Task from Critere - Success")
    void unlinkTaskFromCritereSuccessTest() {
        // Arrange
        task.setCritere(critere); // Pre-link
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.fromEntity(task)).thenReturn(taskDTO);

        // Act
        taskService.unlinkTaskFromCritere(1L);

        // Assert
        assertNull(task.getCritere());
        verify(taskRepository).save(task);
    }

    @Test
    @DisplayName("Unlink Task - Task Not Found")
    void unlinkTaskNotFoundTest() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> taskService.unlinkTaskFromCritere(99L));
    }

    @Test
    @DisplayName("Unlink Task - Not Currently Linked (IllegalState)")
    void unlinkTaskNotLinkedTest() {
        // Arrange
        task.setCritere(null); // Explicitly null
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Act & Assert
        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                taskService.unlinkTaskFromCritere(1L)
        );
        assertEquals("Task is not linked to any critere", ex.getMessage());
    }
}