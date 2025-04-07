package com.ensa.miniproject.controllers;

import com.ensa.miniproject.DTO.UserStoryCloneDTO;
import com.ensa.miniproject.services.UserStoryClone.UserStoryCloneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-story-clones")
public class UserStoryCloneController {

    private final UserStoryCloneService userStoryCloneService;

    public UserStoryCloneController(UserStoryCloneService userStoryCloneService) {
        this.userStoryCloneService = userStoryCloneService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<UserStoryCloneDTO> createUserStoryClone(@RequestBody UserStoryCloneDTO userStoryCloneDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userStoryCloneService.createUserStoryClone(userStoryCloneDTO));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<List<UserStoryCloneDTO>> getAllUserStoryClones() {
        return ResponseEntity.ok(userStoryCloneService.getUserStoryClones());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<UserStoryCloneDTO> getUserStoryCloneById(@PathVariable Long id) {
        return ResponseEntity.ok(userStoryCloneService.getUserStoryCloneById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<UserStoryCloneDTO> updateUserStoryClone(
            @PathVariable Long id,
            @RequestBody UserStoryCloneDTO userStoryCloneDTO) {
        return ResponseEntity.ok(userStoryCloneService.updateUserStoryClone(userStoryCloneDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<Void> deleteUserStoryClone(@PathVariable Long id) {
        userStoryCloneService.deleteUserStoryClone(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cloneId}/affect-to-user-story/{userStoryId}")
    @PreAuthorize("hasAnyRole('SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<UserStoryCloneDTO> affectToUserStory(
            @PathVariable Long cloneId,
            @PathVariable Long userStoryId) {
        return ResponseEntity.ok(userStoryCloneService.affectToUserStory(cloneId, userStoryId));
    }

    @PostMapping("/{cloneId}/remove-from-user-story")
    @PreAuthorize("hasAnyRole('SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<Void> deleteFromUserStory(@PathVariable Long cloneId) {
        userStoryCloneService.deleteFromUserStory(cloneId);
        return ResponseEntity.noContent().build();
    }
}