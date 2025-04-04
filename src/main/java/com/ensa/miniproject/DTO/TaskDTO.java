package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.Critere;
import com.ensa.miniproject.entities.Etat;

public record TaskDTO(
        Long id,
        String task,
        String description,
        Etat etat,
        Critere critere
) {
}
