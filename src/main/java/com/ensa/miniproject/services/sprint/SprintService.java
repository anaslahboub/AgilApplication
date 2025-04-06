package com.ensa.miniproject.services.sprint;

import com.ensa.miniproject.DTO.SprintDto;
import java.util.List;
import java.util.Optional;

public interface SprintService {

    // CRUD operations
    SprintDto saveSprint(SprintDto sprint);                      // Create or Update
    List<SprintDto> getAllSprints();                          // Read All
    Optional<SprintDto> getSprintById(int id);                // Read by ID
    SprintDto updateSprint(int id, SprintDto sprint);            // Update
    void deleteSprint(int id);                             // Delete

    // Metier (Business logic)
    List<SprintDto> findSprintsByDuration(Long days);         // Find sprints with specific duration
    SprintDto assignSprintBacklog(int sprintId, Long backlogId); // Link SprintBacklog to Sprint
    List<SprintDto> getSprintsWithoutBacklog();               // Find all sprints that don’t have a backlog yet
    boolean isSprintActive(int sprintId);                  // Check if a sprint is currently active (based on some criteria)

    // You can also imagine more advanced logic, such as:
     //List<SprintDto> getSprintsByProject(int projectId);     // If sprints are tied to a project
}
