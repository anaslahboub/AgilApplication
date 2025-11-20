package com.ensa.miniproject.dto;

import com.ensa.miniproject.entities.*;


import java.util.List;

public record ProductBacklogDTO(
        Long id,
        String title,
        String description,
        Status status,
        Priorite priority,
        List<Epic> epics,
        List<UserStory> userStories
) {}