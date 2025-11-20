package com.ensa.miniproject.dto;

import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.entities.Priorite;
import com.ensa.miniproject.entities.Status;
import com.ensa.miniproject.entities.UserStory;

import java.util.List;

public record SprintBacklogDTO(
        Long id,
        String title,
        String description,
        Status status,
        Priorite priority,
        String goal,
        List<Epic> epics,
        List<UserStory> userStories
) {}