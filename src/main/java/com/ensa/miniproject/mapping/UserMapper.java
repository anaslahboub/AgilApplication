package com.ensa.miniproject.mapping;

import com.ensa.miniproject.DTO.UserDTO;
import com.ensa.miniproject.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO fromEntity(User user);
    User toEntity(UserDTO userDTO);
}
