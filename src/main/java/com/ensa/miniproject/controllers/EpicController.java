package com.ensa.miniproject.controllers;

import com.ensa.miniproject.DTO.EpicDTO;
import com.ensa.miniproject.services.Epic.EpicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<EpicDTO> createEpic(@RequestBody EpicDTO epicDTO) {
        EpicDTO newEpic = epicService.createEpic(epicDTO);
        return new ResponseEntity<>(newEpic, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EpicDTO>> getAllEpics() {
        List<EpicDTO> epics = epicService.getEpics();
        return new ResponseEntity<>(epics, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpicDTO> getEpicById(@PathVariable Long id) {
        try {
            EpicDTO epicDTO = epicService.getEpicById(id);
            return ResponseEntity.ok(epicDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EpicDTO> updateEpic(@PathVariable Long id,
                                              @RequestBody EpicDTO epicDTO) {
        EpicDTO updatedEpic = epicService.updateEpic(epicDTO);
        return new ResponseEntity<>(updatedEpic, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpic(@PathVariable Long id) {
        epicService.deleteEpic(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PostMapping("/{epicId}/user-stories")
    public ResponseEntity<EpicDTO> affectUserStoriesToEpic(
            @PathVariable Long epicId,
            @RequestBody List<Long> userStoryIds) {
        EpicDTO updatedEpic = epicService.affectUserStoriesToEpic(epicId, userStoryIds);
        return new ResponseEntity<>(updatedEpic, HttpStatus.OK);
    }
    @DeleteMapping("/{epicId}/user-stories/{userStoryId}")
    public ResponseEntity<EpicDTO> removeUserStoryFromEpic(
            @PathVariable Long epicId,
            @PathVariable Long userStoryId) {
        EpicDTO updatedEpic = epicService.removeUserStoryFromEpic(epicId, userStoryId);
        return ResponseEntity.ok(updatedEpic);
    }
}