package com.ensa.miniproject.mapping;

import com.ensa.miniproject.DTO.SprintDto;
import com.ensa.miniproject.entities.Sprint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface SprintMapper {
    Sprint mapToEntity(SprintDto dto);
    SprintDto mapToDto(Sprint sprint);
}
