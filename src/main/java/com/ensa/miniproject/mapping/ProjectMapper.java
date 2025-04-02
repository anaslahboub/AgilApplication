package com.ensa.miniproject.mapping;

import com.ensa.miniproject.DTO.ProjectDTO;
import com.ensa.miniproject.entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectDTO fromEntity(Project project);
    Project toEntity(ProjectDTO projectDTO);
}
