package com.ensa.miniproject.dto;

import com.ensa.miniproject.entities.EquipeDevelopement;
import lombok.*;

@Setter @Getter @ToString @EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
