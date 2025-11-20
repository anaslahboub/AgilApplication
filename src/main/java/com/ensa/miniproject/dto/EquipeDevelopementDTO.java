package com.ensa.miniproject.dto;

import com.ensa.miniproject.entities.Developer;

import java.util.List;

public record EquipeDevelopementDTO(
        Long id,
        List<Developer> developers
) {}
