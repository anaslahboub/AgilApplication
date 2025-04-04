package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.ProductOwner;
import com.ensa.miniproject.entities.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import java.util.List;

public record ProductOwnerDTO(
        Long id,
        String username,
        String prenom,
        String password,
        String email
) {}
