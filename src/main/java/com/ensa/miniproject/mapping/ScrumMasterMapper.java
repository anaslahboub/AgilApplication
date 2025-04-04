package com.ensa.miniproject.mapping;

import com.ensa.miniproject.DTO.ScrumMasterDTO;
import com.ensa.miniproject.entities.ScrumMaster;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScrumMasterMapper {
    ScrumMasterDTO fromEntity(ScrumMaster scrumMaster);
    ScrumMaster toEntity(ScrumMasterDTO scrumMasterDTO);
}
