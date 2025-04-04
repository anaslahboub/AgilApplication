package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
