package com.ensa.miniproject.mapping;

import com.ensa.miniproject.DTO.ScrumMasterDTO;
import com.ensa.miniproject.entities.ScrumMaster;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScrumMasterMapper {
    ScrumMasterMapper INSTANCE = Mappers.getMapper(ScrumMasterMapper.class);
    ScrumMasterDTO fromEntity(ScrumMaster scrumMaster);
    ScrumMaster toEntity(ScrumMasterDTO scrumMasterDTO);
}
