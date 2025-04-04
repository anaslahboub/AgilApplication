package com.ensa.miniproject.DTO;


import com.ensa.miniproject.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

public record UserDTO(
        Long id,
        String username,
        String prenom,
        String password,
        String email
) {}
