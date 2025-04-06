package com.ensa.miniproject.DTO;

import lombok.Data;

@Data
public class DeveloperDto {
        private Long id;
        private String username;
        private String prenom;
        private String password;
        private String email;
        private String speciality;
        private int experienceYears;
        private Long equipeId;

}
