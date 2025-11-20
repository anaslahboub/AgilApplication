package com.ensa.miniproject.dto;

import com.ensa.miniproject.entities.Etat;
import com.ensa.miniproject.entities.Priorite;
import com.ensa.miniproject.entities.Task;
import com.ensa.miniproject.entities.UserStory;

import java.util.List;

public record UserStoryCloneDTO(
        Long id,
        String title,
        String description,
        String enTantQue,
        String JeVeux,
        String aFinQue,
        Etat etat,
        Long tauxDavancement,
        Priorite priority,
        List<Task> tasks,
        UserStory originalUserStory
) {}