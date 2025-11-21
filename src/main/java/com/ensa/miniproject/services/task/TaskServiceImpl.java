package com.ensa.miniproject.services.task;

import com.ensa.miniproject.dto.TaskDTO;
import com.ensa.miniproject.entities.Critere;
import com.ensa.miniproject.entities.Task;
import com.ensa.miniproject.mapping.TaskMapper;
import com.ensa.miniproject.repository.CritereRepository;
import com.ensa.miniproject.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final CritereRepository critereRepository;
    private static final  String TASK_NOT_FOUND = "Task not found with id: ";

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        task = taskRepository.save(task);
        return taskMapper.fromEntity(task);
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND + id));
        return taskMapper.fromEntity(task);
    }

    @Override
    public TaskDTO updateTask(TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(taskDTO.id())
                .orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND + taskDTO.id()));

        // Update fields
        existingTask.setTaskName(taskDTO.task());
        existingTask.setDescription(taskDTO.description());
        existingTask.setCritere(taskDTO.critere());

        // Save the updated entity
        existingTask = taskRepository.save(existingTask);
        return taskMapper.fromEntity(existingTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND + id));
        taskRepository.delete(task);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public TaskDTO linkTaskToCritere(Long taskId, Long critereId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND + taskId));

        Critere critere = critereRepository.findById(critereId)
                .orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND + critereId));

        // Check if the critere is already linked to another task
        Task existingTaskWithCritere = taskRepository.findByCritereId(critereId);
        if (existingTaskWithCritere != null && !existingTaskWithCritere.getId().equals(taskId)) {
            throw new IllegalStateException("Critere is already linked to another task");
        }

        task.setCritere(critere);
        task = taskRepository.save(task);
        return taskMapper.fromEntity(task);
    }

    @Override
    @Transactional
    public TaskDTO unlinkTaskFromCritere(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND + taskId));

        if (task.getCritere() == null) {
            throw new IllegalStateException("Task is not linked to any critere");
        }

        task.setCritere(null);
        task = taskRepository.save(task);
        return taskMapper.fromEntity(task);
    }
}