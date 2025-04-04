package com.ensa.miniproject.controllers;

import com.ensa.miniproject.DTO.SprintBacklogDTO;
import com.ensa.miniproject.services.SprintBacklog.SprintBacklogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SprintBacklogDTO> createSprintBacklog(@RequestBody SprintBacklogDTO sprintBacklogDTO) {
        SprintBacklogDTO newSprint = sprintBacklogService.createSprintBacklog(sprintBacklogDTO);
        return new ResponseEntity<>(newSprint, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SprintBacklogDTO>> getAllSprintBacklogs() {
        List<SprintBacklogDTO> sprints = sprintBacklogService.getAllSprintBacklogs();
        return new ResponseEntity<>(sprints, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SprintBacklogDTO> getSprintBacklogById(@PathVariable Long id) {
        SprintBacklogDTO sprint = sprintBacklogService.getSprintBacklogById(id);
        return ResponseEntity.ok(sprint);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SprintBacklogDTO> updateSprintBacklog(@PathVariable Long id,
                                                                @RequestBody SprintBacklogDTO sprintBacklogDTO) {
        sprintBacklogDTO = new SprintBacklogDTO(
                id,
                sprintBacklogDTO.title(),
                sprintBacklogDTO.description(),
                sprintBacklogDTO.status(),
                sprintBacklogDTO.priority(),
                sprintBacklogDTO.goal(),
                sprintBacklogDTO.epics(),
                sprintBacklogDTO.userStories()
        );
        SprintBacklogDTO updatedSprint = sprintBacklogService.updateSprintBacklog(sprintBacklogDTO);
        return ResponseEntity.ok(updatedSprint);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSprintBacklog(@PathVariable Long id) {
        sprintBacklogService.deleteSprintBacklog(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{sprintId}/epics")
    public ResponseEntity<SprintBacklogDTO> addEpicsToSprint(
            @PathVariable Long sprintId,
            @RequestBody List<Long> epicIds) {
        SprintBacklogDTO updatedSprint = sprintBacklogService.addEpicsToSprint(sprintId, epicIds);
        return ResponseEntity.ok(updatedSprint);
    }

    @DeleteMapping("/{sprintId}/epics/{epicId}")
    public ResponseEntity<SprintBacklogDTO> removeEpicFromSprint(
            @PathVariable Long sprintId,
            @PathVariable Long epicId) {
        SprintBacklogDTO updatedSprint = sprintBacklogService.removeEpicFromSprint(sprintId, epicId);
        return ResponseEntity.ok(updatedSprint);
    }

    @PostMapping("/{sprintId}/user-stories")
    public ResponseEntity<SprintBacklogDTO> addUserStoriesToSprint(
            @PathVariable Long sprintId,
            @RequestBody List<Long> userStoryIds) {
        SprintBacklogDTO updatedSprint = sprintBacklogService.addUserStoriesToSprint(sprintId, userStoryIds);
        return ResponseEntity.ok(updatedSprint);
    }

    @DeleteMapping("/{sprintId}/user-stories/{userStoryId}")
    public ResponseEntity<SprintBacklogDTO> removeUserStoryFromSprint(
            @PathVariable Long sprintId,
            @PathVariable Long userStoryId) {
        SprintBacklogDTO updatedSprint = sprintBacklogService.removeUserStoryFromSprint(sprintId, userStoryId);
        return ResponseEntity.ok(updatedSprint);
    }
}