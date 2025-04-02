package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.entities.Priorite;
import com.ensa.miniproject.entities.ProductBacklog;
import com.ensa.miniproject.entities.UserStory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public record UserStoryDTO(
        Long id,
        String title,
        String description,
        Priorite priority
) {}
