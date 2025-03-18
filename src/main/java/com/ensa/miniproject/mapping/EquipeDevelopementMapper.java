package com.ensa.miniproject.mapping;


import com.ensa.miniproject.DTO.EquipeDevelopementDTO;
import com.ensa.miniproject.entities.EquipeDevelopement;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EquipeDevelopementMapper {
    EquipeDevelopementMapper INSTANCE = Mappers.getMapper(EquipeDevelopementMapper.class);

    EquipeDevelopementDTO fromEntity(EquipeDevelopement equipeDevelopement);
    EquipeDevelopement toEntity(EquipeDevelopementDTO equipeDevelopementDTO);
}
