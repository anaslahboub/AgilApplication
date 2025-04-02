package com.ensa.miniproject.services.SprintBacklog;

import com.ensa.miniproject.DTO.SprintBacklogDTO;
import java.util.List;

public interface SprintBacklogService {
    SprintBacklogDTO createSprintBacklog(SprintBacklogDTO sprintBacklogDTO);
    SprintBacklogDTO getSprintBacklogById(Long id);
    SprintBacklogDTO updateSprintBacklog(SprintBacklogDTO sprintBacklogDTO);
    void deleteSprintBacklog(Long id);
    List<SprintBacklogDTO> getAllSprintBacklogs();

    // Relationship management
    SprintBacklogDTO addEpicsToSprint(Long sprintId, List<Long> epicIds);
    SprintBacklogDTO removeEpicFromSprint(Long sprintId, Long epicId);
    SprintBacklogDTO addUserStoriesToSprint(Long sprintId, List<Long> userStoryIds);
    SprintBacklogDTO removeUserStoryFromSprint(Long sprintId, Long userStoryId);
}