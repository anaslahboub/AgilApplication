package com.ensa.miniproject.mapping;


import com.ensa.miniproject.dto.EquipeDevelopementDTO;
import com.ensa.miniproject.entities.EquipeDevelopement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EquipeDevelopementMapper {
    EquipeDevelopementDTO fromEntity(EquipeDevelopement equipeDevelopement);
    EquipeDevelopement toEntity(EquipeDevelopementDTO equipeDevelopementDTO);
}
