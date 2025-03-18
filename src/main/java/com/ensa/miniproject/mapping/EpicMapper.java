package com.ensa.miniproject.mapping;

import com.ensa.miniproject.DTO.EpicDTO;
import com.ensa.miniproject.entities.Epic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EpicMapper {
    EpicMapper INSTANCE = Mappers.getMapper(EpicMapper.class);

    Epic toEntity(EpicDTO epicDTO);
    EpicDTO fromEntity(Epic epic);

}
