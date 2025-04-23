package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.EquipeDevelopement;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DeveloperDto {
        private Long id;
        private String username;
        private String prenom;
        private String password;
        private String email;
        private String speciality;
        private int experienceYears;
        private EquipeDevelopement equipe;

}
