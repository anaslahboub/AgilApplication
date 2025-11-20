package com.ensa.miniproject.mapping;

import com.ensa.miniproject.dto.EpicDTO;
import com.ensa.miniproject.entities.Epic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EpicMapper {
    Epic toEntity(EpicDTO epicDTO);
    EpicDTO fromEntity(Epic epic);
}
