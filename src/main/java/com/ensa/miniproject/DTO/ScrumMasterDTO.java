package com.ensa.miniproject.DTO;


import com.ensa.miniproject.entities.Project;
import com.ensa.miniproject.entities.ScrumMaster;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

public record ScrumMasterDTO(
        Long id,
        String username,
        String prenom,
        String password,
        String email
) {}
