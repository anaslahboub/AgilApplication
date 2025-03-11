package com.ensa.miniproject.mapping;

import com.ensa.miniproject.DTO.DeveloperDTO;
import com.ensa.miniproject.entities.Developer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeveloperMapper {
    DeveloperMapper INSTANCE = Mappers.getMapper(DeveloperMapper.class);

    DeveloperDTO fromEntity(Developer developer);
    Developer toEntity(DeveloperDTO developerDTO);
}
