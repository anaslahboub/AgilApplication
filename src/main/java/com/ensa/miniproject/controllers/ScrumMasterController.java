package com.ensa.miniproject.controllers;

import com.ensa.miniproject.DTO.ScrumMasterDTO;
import com.ensa.miniproject.services.ScrumMaster.ScrumMasterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ScrumMasterDTO> createScrumMaster(@RequestBody ScrumMasterDTO scrumMasterDTO) {
        ScrumMasterDTO newScrumMaster = scrumMasterService.createScrumMaster(scrumMasterDTO);
        return new ResponseEntity<>(newScrumMaster, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScrumMasterDTO>> getAllScrumMasters() {
        List<ScrumMasterDTO> scrumMasters = scrumMasterService.getScrumMasters();
        return new ResponseEntity<>(scrumMasters, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScrumMasterDTO> getScrumMasterById(@PathVariable Long id) {
        try {
            ScrumMasterDTO scrumMasterDTO = scrumMasterService.getScrumMasterById(id);
            return ResponseEntity.ok(scrumMasterDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScrumMasterDTO> updateScrumMaster(@PathVariable Long id,
                                                            @RequestBody ScrumMasterDTO scrumMasterDTO) {
        ScrumMasterDTO updatedScrumMaster = scrumMasterService.updateScrumMaster(scrumMasterDTO);
        return new ResponseEntity<>(updatedScrumMaster, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScrumMaster(@PathVariable Long id) {
        scrumMasterService.deleteScrumMaster(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}