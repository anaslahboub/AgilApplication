package com.ensa.miniproject.dto;


public record ScrumMasterDTO(
        Long id,
        String username,
        String prenom,
        String password,
        String email
) {}
