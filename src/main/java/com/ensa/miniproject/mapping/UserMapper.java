package com.ensa.miniproject.mapping;

import com.ensa.miniproject.DTO.UserDTO;
import com.ensa.miniproject.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO fromEntity(User user);
    User toEntity(UserDTO userDTO);
}
