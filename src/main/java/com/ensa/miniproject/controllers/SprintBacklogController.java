package com.ensa.miniproject.controllers;

import com.ensa.miniproject.DTO.SprintBacklogDTO;
import com.ensa.miniproject.services.SprintBacklog.SprintBacklogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sprint-backlogs")
public class SprintBacklogController {

    private final SprintBacklogService sprintBacklogService;

    public SprintBacklogController(SprintBacklogService sprintBacklogService) {
        this.sprintBacklogService = sprintBacklogService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SCRUMMASTER', 'PRODUCTOWNER', 'ADMIN')")
    public ResponseEntity<SprintBacklogDTO> createSprintBacklog(@RequestBody SprintBacklogDTO sprintBacklogDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sprintBacklogService.createSprintBacklog(sprintBacklogDTO));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUMMASTER', 'PRODUCTOWNER', 'ADMIN')")
    public ResponseEntity<List<SprintBacklogDTO>> getAllSprintBacklogs() {
        return ResponseEntity.ok(sprintBacklogService.getAllSprintBacklogs());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUMMASTER', 'PRODUCTOWNER', 'ADMIN')")
    public ResponseEntity<SprintBacklogDTO> getSprintBacklogById(@PathVariable Long id) {
        return ResponseEntity.ok(sprintBacklogService.getSprintBacklogById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SCRUMMASTER', 'PRODUCTOWNER', 'ADMIN')")
    public ResponseEntity<SprintBacklogDTO> updateSprintBacklog(@PathVariable Long id, @RequestBody SprintBacklogDTO sprintBacklogDTO) {
        return ResponseEntity.ok(sprintBacklogService.updateSprintBacklog(
                new SprintBacklogDTO(id, sprintBacklogDTO.title(), sprintBacklogDTO.description(),
                        sprintBacklogDTO.status(), sprintBacklogDTO.priority(), sprintBacklogDTO.goal(),
                        sprintBacklogDTO.epics(), sprintBacklogDTO.userStories())));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PRODUCTOWNER', 'ADMIN')")
    public ResponseEntity<Void> deleteSprintBacklog(@PathVariable Long id) {
        sprintBacklogService.deleteSprintBacklog(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{sprintId}/epics")
    @PreAuthorize("hasAnyRole('SCRUMMASTER', 'PRODUCTOWNER', 'ADMIN')")
    public ResponseEntity<SprintBacklogDTO> addEpicsToSprint(@PathVariable Long sprintId, @RequestBody List<Long> epicIds) {
        return ResponseEntity.ok(sprintBacklogService.addEpicsToSprint(sprintId, epicIds));
    }

    @DeleteMapping("/{sprintId}/epics/{epicId}")
    @PreAuthorize("hasAnyRole('SCRUMMASTER', 'PRODUCTOWNER', 'ADMIN')")
    public ResponseEntity<SprintBacklogDTO> removeEpicFromSprint(@PathVariable Long sprintId, @PathVariable Long epicId) {
        return ResponseEntity.ok(sprintBacklogService.removeEpicFromSprint(sprintId, epicId));
    }

    @PostMapping("/{sprintId}/user-stories")
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUMMASTER', 'PRODUCTOWNER', 'ADMIN')")
    public ResponseEntity<SprintBacklogDTO> addUserStoriesToSprint(@PathVariable Long sprintId, @RequestBody List<Long> userStoryIds) {
        return ResponseEntity.ok(sprintBacklogService.addUserStoriesToSprint(sprintId, userStoryIds));
    }

    @DeleteMapping("/{sprintId}/user-stories/{userStoryId}")
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUMMASTER', 'PRODUCTOWNER', 'ADMIN')")
    public ResponseEntity<SprintBacklogDTO> removeUserStoryFromSprint(@PathVariable Long sprintId, @PathVariable Long userStoryId) {
        return ResponseEntity.ok(sprintBacklogService.removeUserStoryFromSprint(sprintId, userStoryId));
    }
}