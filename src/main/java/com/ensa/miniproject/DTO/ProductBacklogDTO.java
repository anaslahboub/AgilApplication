package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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