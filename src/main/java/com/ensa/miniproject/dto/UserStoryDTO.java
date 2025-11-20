package com.ensa.miniproject.dto;

import com.ensa.miniproject.entities.*;

import java.util.List;


public record UserStoryDTO(
        Long id,
        String title,
        String description,
        Priorite priority,
        Long tauxDavancement,
        Etat etat,
        List<Task> tasks
) {}
