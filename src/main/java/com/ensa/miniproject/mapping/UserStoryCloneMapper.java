package com.ensa.miniproject.mapping;

import com.ensa.miniproject.DTO.UserStoryCloneDTO;
import com.ensa.miniproject.DTO.UserStoryDTO;
import com.ensa.miniproject.entities.UserStoryClone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserStoryMapper.class)
public interface UserStoryCloneMapper {
    UserStoryClone toEntity(UserStoryCloneDTO userStoryCloneDTO);

    UserStoryCloneDTO fromEntity(UserStoryClone userStoryClone);
}