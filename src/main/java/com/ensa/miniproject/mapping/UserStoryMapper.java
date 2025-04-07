package com.ensa.miniproject.mapping;

import com.ensa.miniproject.DTO.UserStoryDTO;
import com.ensa.miniproject.entities.UserStory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserStoryMapper {
    UserStoryDTO fromEntity(UserStory userStory);

    @Mapping(target = "tasks", ignore = true) // Prevent circular references
    UserStory toEntity(UserStoryDTO userStoryDTO);

    default UserStoryDTO map(UserStory userStory) {
        return fromEntity(userStory);
    }
}