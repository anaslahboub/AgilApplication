package com.ensa.miniproject.mapping;

import com.ensa.miniproject.DTO.EpicDTO;
import com.ensa.miniproject.DTO.CritereDTO;
import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.entities.Critere;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CritereMapper {
    Critere toEntity(CritereDTO critereDTO);
    CritereDTO fromEntity(Critere critere);
}
