package com.ensa.miniproject.controllers;

import com.ensa.miniproject.dto.UserStoryDTO;
import com.ensa.miniproject.services.userstory.UserStoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user-stories")
public class UserStoryController {

    private final UserStoryService userStoryService;

    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<UserStoryDTO> createUserStory(@RequestBody UserStoryDTO userStoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userStoryService.createUserStory(userStoryDTO));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<List<UserStoryDTO>> getAllUserStories() {
        return ResponseEntity.ok(userStoryService.getUserStories());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<UserStoryDTO> getUserStoryById(@PathVariable Long id) {
        return ResponseEntity.ok(userStoryService.getUserStoryById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<UserStoryDTO> updateUserStory(
            @PathVariable Long id,
            @RequestBody UserStoryDTO userStoryDTO) {
        return ResponseEntity.ok(userStoryService.updateUserStory(userStoryDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<Void> deleteUserStory(@PathVariable Long id) {
        userStoryService.deleteUserStory(id);
        return ResponseEntity.noContent().build();
    }
}