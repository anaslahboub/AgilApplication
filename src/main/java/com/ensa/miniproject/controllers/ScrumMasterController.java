package com.ensa.miniproject.controllers;

import com.ensa.miniproject.dto.ScrumMasterDTO;
import com.ensa.miniproject.services.scrummaster.ScrumMasterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/scrum-masters")
public class ScrumMasterController {

    private final ScrumMasterService scrumMasterService;

    public ScrumMasterController(ScrumMasterService scrumMasterService) {
        this.scrumMasterService = scrumMasterService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ScrumMasterDTO> createScrumMaster(@RequestBody ScrumMasterDTO scrumMasterDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(scrumMasterService.createScrumMaster(scrumMasterDTO));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCT_OWNER', 'SCRUM_MASTER')")
    public ResponseEntity<List<ScrumMasterDTO>> getAllScrumMasters() {
        return ResponseEntity.ok(scrumMasterService.getScrumMasters());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCT_OWNER', 'SCRUM_MASTER', 'DEVELOPER')")
    public ResponseEntity<ScrumMasterDTO> getScrumMasterById(@PathVariable Long id) {
        return ResponseEntity.ok(scrumMasterService.getScrumMasterById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCRUM_MASTER')")
    public ResponseEntity<ScrumMasterDTO> updateScrumMaster(
            @PathVariable Long id,
            @RequestBody ScrumMasterDTO scrumMasterDTO) {
        return ResponseEntity.ok(scrumMasterService.updateScrumMaster(scrumMasterDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteScrumMaster(@PathVariable Long id) {
        scrumMasterService.deleteScrumMaster(id);
        return ResponseEntity.noContent().build();
    }
}