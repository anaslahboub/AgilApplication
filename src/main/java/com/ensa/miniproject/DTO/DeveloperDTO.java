package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.EquipeDevelopement;
import com.ensa.miniproject.entities.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public record DeveloperDTO(
        Long id,
        String username,
        String prenom,
        String password,
        String email,
        EquipeDevelopement equipeDevelopement
) {}
