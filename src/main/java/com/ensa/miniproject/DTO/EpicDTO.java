package com.ensa.miniproject.DTO;


import com.ensa.miniproject.entities.UserStory;

import java.util.List;

public record EpicDTO(
        Long id,
        String title,
        String description,
        List<UserStory> userStories
) {}
