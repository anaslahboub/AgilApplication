package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.Developer;
import com.ensa.miniproject.entities.EquipeDevelopement;
import com.ensa.miniproject.entities.Project;
import com.ensa.miniproject.entities.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeveloperDTO extends UserDTO {

    private EquipeDevelopement equipeDevelopement;

    private List<Project> projects;

    public Developer toEntity() {
        Developer developer = new Developer();
        developer.setEmail(this.getEmail());
        developer.setPassword(this.getPassword());
        developer.setEquipeDevelopement(this.getEquipeDevelopement());
        developer.setProjects(this.getProjects());
        developer.setUsername(this.getUsername());
        developer.setPrenom(this.getPrenom());
        developer.setId(this.getId());
        return developer;
    }
    public static DeveloperDTO fromEntity(Developer developer) {
        DeveloperDTO developerDTO = new DeveloperDTO();
        developerDTO.setEmail(developer.getEmail());
        developerDTO.setPassword(developer.getPassword());
        developerDTO.setEquipeDevelopement(developer.getEquipeDevelopement());
        developerDTO.setProjects(developer.getProjects());
        developerDTO.setUsername(developer.getUsername());
        developerDTO.setPrenom(developer.getPrenom());
        developerDTO.setId(developer.getId());
        return developerDTO;
    }
}
