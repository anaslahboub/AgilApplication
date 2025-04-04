package com.ensa.miniproject.services.UserStory;

import com.ensa.miniproject.DTO.TaskDTO;
import com.ensa.miniproject.DTO.UserStoryDTO;
import com.ensa.miniproject.entities.Etat;
import com.ensa.miniproject.entities.Task;
import com.ensa.miniproject.entities.UserStory;
import com.ensa.miniproject.mapping.TaskMapper;
import com.ensa.miniproject.mapping.UserStoryMapper;
import com.ensa.miniproject.repository.UserStoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class UserStoryServiceImpl implements UserStoryService {

    private final UserStoryRepository userStoryRepository;
    private final UserStoryMapper userStoryMapper;
    private final TaskMapper taskMapper;

    @Override
    public UserStoryDTO createUserStory(UserStoryDTO userStoryDTO) {
        UserStory userStory = userStoryMapper.toEntity(userStoryDTO);
        userStory = userStoryRepository.save(userStory);
        return userStoryMapper.fromEntity(userStory);
    }

    @Override
    public UserStoryDTO getUserStoryById(Long id) {
        UserStory userStory = userStoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + id));
        return userStoryMapper.fromEntity(userStory);
    }

    @Override
    public UserStoryDTO updateUserStory(UserStoryDTO userStoryDTO) {
        UserStory existingUserStory = userStoryRepository.findById(userStoryDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + userStoryDTO.id()));

        // Update fields
        existingUserStory.setTitle(userStoryDTO.title());
        existingUserStory.setDescription(userStoryDTO.description());
        existingUserStory.setPriority(userStoryDTO.priority());
        existingUserStory.setTasks(userStoryDTO.tasks());
        existingUserStory.setEtat(userStoryDTO.etat());

        // Save the updated entity
        existingUserStory = userStoryRepository.save(existingUserStory);
        return userStoryMapper.fromEntity(existingUserStory);
    }

    @Override
    public void deleteUserStory(Long id) {
        UserStory userStory = userStoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + id));
        userStoryRepository.delete(userStory);
    }

    @Override
    public List<UserStoryDTO> getUserStories() {
        return userStoryRepository.findAll()
                .stream() // Convert the list to a stream
                .map(userStoryMapper::fromEntity) // Use the injected mapper to map entities to DTOs
                .collect(Collectors.toList()); // Collect the results into a list
    }

    @Override
    @Transactional
    public TaskDTO addTask(Long userStoryId,TaskDTO taskDTO) {
        UserStory tempUserStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + userStoryId));
        Task task = taskMapper.toEntity(taskDTO);
        tempUserStory.getTasks().add(task);

        userStoryRepository.save(tempUserStory);
        return taskDTO;
    }

    @Override
    @Transactional
    public void deleteTask(Long userStoryId, TaskDTO taskId) {
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + userStoryId));

        // Find and remove the task with the given ID
        boolean removed = userStory.getTasks().removeIf(task -> task.getId().equals(taskId.id()));

        if (!removed) {
            throw new EntityNotFoundException("Task not found with id: " + taskId + " in UserStory: " + userStoryId);
        }

        // Save the updated UserStory
        userStoryRepository.save(userStory);
    }

    @Override
    @Transactional
    public UserStoryDTO calculerTauxDavancement(Long userStoryId) {
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + userStoryId));

        List<Task> tasks = userStory.getTasks();

        if (tasks == null || tasks.isEmpty()) {
            userStory.setTauxDavancenement(0L);
        } else {
            long validatedTasksCount = tasks.stream()
                    .filter(task -> task.getEtat() != null)
                    .filter(task -> task.getEtat() == Etat.VALIDEE)
                    .count();

            long progressPercentage = (validatedTasksCount * 100) / tasks.size();
            userStory.setTauxDavancenement(progressPercentage);
        }

        userStory = userStoryRepository.save(userStory);
        return userStoryMapper.fromEntity(userStory);
    }

}
