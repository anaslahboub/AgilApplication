package com.ensa.miniproject.dto;

public record ProductOwnerDTO(
        Long id,
        String username,
        String prenom,
        String password,
        String email
) {}
