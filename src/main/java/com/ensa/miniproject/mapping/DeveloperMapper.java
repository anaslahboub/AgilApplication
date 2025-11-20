package com.ensa.miniproject.mapping;

import com.ensa.miniproject.dto.DeveloperDto;
import com.ensa.miniproject.entities.Developer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeveloperMapper {
    DeveloperDto fromEntity(Developer developer);
    Developer toEntity(DeveloperDto developerDTO);
}
