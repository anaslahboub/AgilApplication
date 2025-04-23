package com.ensa.miniproject.controllers;

import com.ensa.miniproject.DTO.DeveloperDto;
import com.ensa.miniproject.services.dev.DeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/developers")
@RequiredArgsConstructor
public class DeveloperController {

    private final DeveloperService developerService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeveloperDto> createDeveloper(@RequestBody DeveloperDto dto) {
        return ResponseEntity.ok(developerService.saveDeveloper(dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SCRUM_MASTER')")
    public ResponseEntity<List<DeveloperDto>> getAllDevelopers() {
        return ResponseEntity.ok(developerService.getAllDevelopers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCRUM_MASTER') or (hasRole('DEVELOPER') and #id == principal.id)")
    public ResponseEntity<DeveloperDto> getDeveloperById(@PathVariable Long id) {
        return developerService.getDeveloperById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('DEVELOPER') and #id == principal.id)")
    public ResponseEntity<DeveloperDto> updateDeveloper(@PathVariable Long id, @RequestBody DeveloperDto dto) {
        return ResponseEntity.ok(developerService.updateDeveloper(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable Long id) {
        developerService.deleteDeveloper(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/speciality/{speciality}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCRUM_MASTER')")
    public ResponseEntity<List<DeveloperDto>> getDevelopersBySpeciality(@PathVariable String speciality) {
        return ResponseEntity.ok(developerService.findDevelopersBySpeciality(speciality));
    }

    @PostMapping("/{developerId}/assign-equipe/{equipeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCRUM_MASTER')")
    public ResponseEntity<DeveloperDto> assignToEquipe(
            @PathVariable Long developerId,
            @PathVariable Long equipeId
    ) {
        return ResponseEntity.ok(developerService.assignToEquipe(developerId, equipeId));
    }

    @GetMapping("/equipe/{equipeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCRUM_MASTER', 'DEVELOPER')")
    public ResponseEntity<List<DeveloperDto>> getDevelopersByEquipe(@PathVariable Long equipeId) {
        return ResponseEntity.ok(developerService.getDevelopersByEquipe(equipeId));
    }
}