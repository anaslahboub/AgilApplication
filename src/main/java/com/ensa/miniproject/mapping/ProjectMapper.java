package com.ensa.miniproject.mapping;

import com.ensa.miniproject.dto.ProjectDTO;
import com.ensa.miniproject.entities.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectDTO fromEntity(Project project);
    Project toEntity(ProjectDTO projectDTO);
}
