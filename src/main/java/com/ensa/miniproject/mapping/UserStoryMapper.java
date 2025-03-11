package com.ensa.miniproject.mapping;

import com.ensa.miniproject.DTO.UserStoryDTO;
import com.ensa.miniproject.entities.UserStory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserStoryMapper {
    UserStoryMapper INSTANCE = Mappers.getMapper(UserStoryMapper.class);
    UserStoryDTO fromEntity(UserStory userStory);
    UserStory toEntity(UserStoryDTO userStoryDTO);
}
