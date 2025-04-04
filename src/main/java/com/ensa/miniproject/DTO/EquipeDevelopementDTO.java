package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.Developer;
import com.ensa.miniproject.entities.EquipeDevelopement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public record EquipeDevelopementDTO(
        Long id,
        List<Developer> developers
) {}
