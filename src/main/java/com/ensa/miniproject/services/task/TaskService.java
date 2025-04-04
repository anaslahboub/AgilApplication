package com.ensa.miniproject.services.task;

import com.ensa.miniproject.DTO.TaskDTO;

import java.util.List;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO);
    TaskDTO getTaskById(Long id);
    TaskDTO updateTask(TaskDTO taskDTO);
    void deleteTask(Long id);
    List<TaskDTO> getAllTasks();

    TaskDTO linkTaskToCritere(Long taskId, Long critereId);
    TaskDTO unlinkTaskFromCritere(Long taskId);
}