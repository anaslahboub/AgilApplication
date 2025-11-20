package com.ensa.miniproject.dto;


public record UserDTO(
        Long id,
        String username,
        String prenom,
        String password,
        String email
) {}
