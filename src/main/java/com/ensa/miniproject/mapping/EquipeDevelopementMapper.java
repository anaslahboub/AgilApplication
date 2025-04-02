package com.ensa.miniproject.mapping;


import com.ensa.miniproject.DTO.EquipeDevelopementDTO;
import com.ensa.miniproject.entities.EquipeDevelopement;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EquipeDevelopementMapper {
    EquipeDevelopementDTO fromEntity(EquipeDevelopement equipeDevelopement);
    EquipeDevelopement toEntity(EquipeDevelopementDTO equipeDevelopementDTO);
}
