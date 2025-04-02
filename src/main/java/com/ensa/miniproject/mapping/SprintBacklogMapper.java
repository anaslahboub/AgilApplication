package com.ensa.miniproject.mapping;

import com.ensa.miniproject.DTO.SprintBacklogDTO;
import com.ensa.miniproject.entities.SprintBacklog;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper(componentModel = "spring")
@Repository  // Add this annotatio
public interface SprintBacklogMapper {
    SprintBacklogDTO fromEntity(SprintBacklog sprintBacklog);
    SprintBacklog toEntity(SprintBacklogDTO sprintBacklogDTO);
}