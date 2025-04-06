package com.ensa.miniproject.controllers;

import com.ensa.miniproject.DTO.SprintDto;
import com.ensa.miniproject.services.sprint.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sprints")
@RequiredArgsConstructor
public class SprintController {

    private final SprintService sprintService;

    @PostMapping
    @PreAuthorize("hasAnyRole('SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<SprintDto> createSprint(@RequestBody SprintDto sprintDto) {
        return ResponseEntity.ok(sprintService.saveSprint(sprintDto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<List<SprintDto>> getAllSprints() {
        return ResponseEntity.ok(sprintService.getAllSprints());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<SprintDto> getSprintById(@PathVariable int id) {
        return sprintService.getSprintById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<SprintDto> updateSprint(@PathVariable int id, @RequestBody SprintDto sprintDto) {
        return ResponseEntity.ok(sprintService.updateSprint(id, sprintDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<Void> deleteSprint(@PathVariable int id) {
        sprintService.deleteSprint(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/duration/{days}")
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<List<SprintDto>> findByDuration(@PathVariable Long days) {
        return ResponseEntity.ok(sprintService.findSprintsByDuration(days));
    }

    @PutMapping("/{sprintId}/assign-backlog/{backlogId}")
    @PreAuthorize("hasAnyRole('SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<SprintDto> assignBacklog(
            @PathVariable int sprintId,
            @PathVariable Long backlogId
    ) {
        return ResponseEntity.ok(sprintService.assignSprintBacklog(sprintId, backlogId));
    }

    @GetMapping("/no-backlog")
    @PreAuthorize("hasAnyRole('SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<List<SprintDto>> getSprintsWithoutBacklog() {
        return ResponseEntity.ok(sprintService.getSprintsWithoutBacklog());
    }

    @GetMapping("/{sprintId}/active")
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<Boolean> isSprintActive(@PathVariable int sprintId) {
        return ResponseEntity.ok(sprintService.isSprintActive(sprintId));
    }
}