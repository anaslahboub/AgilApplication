package com.ensa.miniproject.controllers;

import com.ensa.miniproject.DTO.EquipeDevelopementDTO;
import com.ensa.miniproject.services.equipe.EquipeDevelopementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipes")
public class EquipeController {

    private final EquipeDevelopementService equipeService;

    public EquipeController(EquipeDevelopementService equipeService) {
        this.equipeService = equipeService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SCRUM_MASTER', 'ADMIN')")
    public ResponseEntity<EquipeDevelopementDTO> createEquipe(@RequestBody EquipeDevelopementDTO equipeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(equipeService.createEquipe(equipeDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'ADMIN')")
    public ResponseEntity<EquipeDevelopementDTO> getEquipeById(@PathVariable Long id) {
        return ResponseEntity.ok(equipeService.getEquipeById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'ADMIN')")
    public ResponseEntity<List<EquipeDevelopementDTO>> getAllEquipes() {
        return ResponseEntity.ok(equipeService.getAllEquipes());
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('SCRUM_MASTER', 'ADMIN')")
    public ResponseEntity<EquipeDevelopementDTO> updateEquipe(@RequestBody EquipeDevelopementDTO equipeDTO) {
        return ResponseEntity.ok(equipeService.updateEquipe(equipeDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEquipe(@PathVariable Long id) {
        equipeService.deleteEquipe(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{equipeId}/developers/{developerId}")
    @PreAuthorize("hasAnyRole('SCRUM_MASTER', 'ADMIN')")
    public ResponseEntity<EquipeDevelopementDTO> addDeveloperToEquipe(
            @PathVariable Long equipeId,
            @PathVariable Long developerId) {
        return ResponseEntity.ok(equipeService.addDeveloperToEquipe(equipeId, developerId));
    }
}