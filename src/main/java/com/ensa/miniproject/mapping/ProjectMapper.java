package com.ensa.miniproject.mapping;

import com.ensa.miniproject.DTO.ProjectDTO;
import com.ensa.miniproject.entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);
    ProjectDTO fromEntity(Project project);
    Project toEntity(ProjectDTO projectDTO);
}
