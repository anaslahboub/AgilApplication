package com.ensa.miniproject.DTO;


import com.ensa.miniproject.entities.Project;
import com.ensa.miniproject.entities.ScrumMaster;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ScrumMasterDTO extends UserDTO{
    List<Project> projects;

    public ScrumMaster toEntity(){
        ScrumMaster scrumMaster = new ScrumMaster();
        scrumMaster.setUsername(this.getUsername());
        scrumMaster.setPassword(this.getPassword());
        scrumMaster.setEmail(this.getEmail());
        scrumMaster.setProjects(projects);
        scrumMaster.setId(this.getId());
        scrumMaster.setPrenom(this.getPrenom());
        return scrumMaster;

    }

    public static ScrumMasterDTO fromEntity(ScrumMaster scrumMaster){
        ScrumMasterDTO scrumMasterDTO = new ScrumMasterDTO();
        scrumMasterDTO.setId(scrumMaster.getId());
        scrumMasterDTO.setUsername(scrumMaster.getUsername());
        scrumMasterDTO.setPassword(scrumMaster.getPassword());
        scrumMasterDTO.setEmail(scrumMaster.getEmail());
        scrumMasterDTO.setProjects(scrumMaster.getProjects());
        scrumMasterDTO.setPrenom(scrumMaster.getPrenom());
        return scrumMasterDTO;
    }
}
