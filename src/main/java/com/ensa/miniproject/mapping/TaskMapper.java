package com.ensa.miniproject.mapping;

import com.ensa.miniproject.DTO.EpicDTO;
import com.ensa.miniproject.DTO.TaskDTO;
import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.entities.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toEntity(TaskDTO taskDTO);
    TaskDTO fromEntity(Task task);
}
