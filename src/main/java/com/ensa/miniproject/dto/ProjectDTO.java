package com.ensa.miniproject.dto;

import com.ensa.miniproject.entities.*;

import java.time.LocalDate;

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