package com.ensa.miniproject.controllers;

import com.ensa.miniproject.DTO.UserStoryDTO;
import com.ensa.miniproject.services.UserStory.UserStoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserStoryDTO> createUserStory(@RequestBody UserStoryDTO userStoryDTO) {
        UserStoryDTO newUserStory = userStoryService.createUserStory(userStoryDTO);
        return new ResponseEntity<>(newUserStory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserStoryDTO>> getAllUserStories() {
        List<UserStoryDTO> userStories = userStoryService.getUserStories();
        return new ResponseEntity<>(userStories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserStoryDTO> getUserStoryById(@PathVariable Long id) {
        try {
            UserStoryDTO userStoryDTO = userStoryService.getUserStoryById(id);
            return ResponseEntity.ok(userStoryDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserStoryDTO> updateUserStory(@PathVariable Long id,
                                                        @RequestBody UserStoryDTO userStoryDTO) {
        UserStoryDTO updatedUserStory = userStoryService.updateUserStory(userStoryDTO);
        return new ResponseEntity<>(updatedUserStory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserStory(@PathVariable Long id) {
        userStoryService.deleteUserStory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}