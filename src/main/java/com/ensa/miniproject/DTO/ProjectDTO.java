package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public record ProjectDTO(
        Long id,
        String nom,
        LocalDate dateDebut,
        LocalDate dateFin,
        ProductOwner owner,
        ScrumMaster scrumMaster,
        EquipeDevelopement equipeDevelopement,
        ProductBacklog productBacklog
) {}