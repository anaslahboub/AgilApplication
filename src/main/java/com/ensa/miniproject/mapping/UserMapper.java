package com.ensa.miniproject.mapping;

import com.ensa.miniproject.dto.UserDTO;
import com.ensa.miniproject.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO fromEntity(User user);
    User toEntity(UserDTO userDTO);
}
