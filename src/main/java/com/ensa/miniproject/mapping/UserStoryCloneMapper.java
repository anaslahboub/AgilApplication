package com.ensa.miniproject.mapping;

import com.ensa.miniproject.dto.UserStoryCloneDTO;
import com.ensa.miniproject.entities.UserStoryClone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserStoryMapper.class)
public interface UserStoryCloneMapper {
    UserStoryClone toEntity(UserStoryCloneDTO userStoryCloneDTO);

    UserStoryCloneDTO fromEntity(UserStoryClone userStoryClone);
}