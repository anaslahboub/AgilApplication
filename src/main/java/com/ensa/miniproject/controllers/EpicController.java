package com.ensa.miniproject.controllers;

import com.ensa.miniproject.dto.EpicDTO;
import com.ensa.miniproject.services.epic.EpicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/epics")
public class EpicController {

    private final EpicService epicService;

    public EpicController(EpicService epicService) {
        this.epicService = epicService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<EpicDTO> createEpic(@RequestBody EpicDTO epicDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(epicService.createEpic(epicDTO));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<List<EpicDTO>> getAllEpics() {
        return ResponseEntity.ok(epicService.getEpics());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<EpicDTO> getEpicById(@PathVariable Long id) {
        return ResponseEntity.ok(epicService.getEpicById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<EpicDTO> updateEpic(
            @PathVariable Long id,
            @RequestBody EpicDTO epicDTO) {
        return ResponseEntity.ok(epicService.updateEpic(epicDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEpic(@PathVariable Long id) {
        epicService.deleteEpic(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{epicId}/user-stories")
    @PreAuthorize("hasAnyRole('SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<EpicDTO> affectUserStoriesToEpic(
            @PathVariable Long epicId,
            @RequestBody List<Long> userStoryIds) {
        return ResponseEntity.ok(epicService.affectUserStoriesToEpic(epicId, userStoryIds));
    }

    @DeleteMapping("/{epicId}/user-stories/{userStoryId}")
    @PreAuthorize("hasAnyRole('SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<EpicDTO> removeUserStoryFromEpic(
            @PathVariable Long epicId,
            @PathVariable Long userStoryId) {
        return ResponseEntity.ok(epicService.removeUserStoryFromEpic(epicId, userStoryId));
    }
}