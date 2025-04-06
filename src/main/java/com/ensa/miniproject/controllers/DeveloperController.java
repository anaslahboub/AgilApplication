package com.ensa.miniproject.controllers;


import com.ensa.miniproject.DTO.DeveloperDto;
import com.ensa.miniproject.services.dev.DeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/developers")
@RequiredArgsConstructor
public class DeveloperController {

    private final DeveloperService developerService;

    // ---------------------- CRUD ----------------------

    @PostMapping
    public ResponseEntity<DeveloperDto> createDeveloper(@RequestBody DeveloperDto dto) {
        return ResponseEntity.ok(developerService.saveDeveloper(dto));
    }

    @GetMapping
    public ResponseEntity<List<DeveloperDto>> getAllDevelopers() {
        return ResponseEntity.ok(developerService.getAllDevelopers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeveloperDto> getDeveloperById(@PathVariable Long id) {
        return developerService.getDeveloperById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeveloperDto> updateDeveloper(@PathVariable Long id, @RequestBody DeveloperDto dto) {
        return ResponseEntity.ok(developerService.updateDeveloper(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable Long id) {
        developerService.deleteDeveloper(id);
        return ResponseEntity.noContent().build();
    }

    // ---------------------- Métier ----------------------

    @GetMapping("/speciality/{speciality}")
    public ResponseEntity<List<DeveloperDto>> getDevelopersBySpeciality(@PathVariable String speciality) {
        return ResponseEntity.ok(developerService.findDevelopersBySpeciality(speciality));
    }

    @PutMapping("/{developerId}/assign-equipe/{equipeId}")
    public ResponseEntity<DeveloperDto> assignToEquipe(
            @PathVariable Long developerId,
            @PathVariable Long equipeId
    ) {
        return ResponseEntity.ok(developerService.assignToEquipe(developerId, equipeId));
    }

    @GetMapping("/equipe/{equipeId}")
    public ResponseEntity<List<DeveloperDto>> getDevelopersByEquipe(@PathVariable Long equipeId) {
        return ResponseEntity.ok(developerService.getDevelopersByEquipe(equipeId));
    }
}
