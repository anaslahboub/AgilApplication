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

}
