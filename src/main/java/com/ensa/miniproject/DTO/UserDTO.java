package com.ensa.miniproject.DTO;


import com.ensa.miniproject.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDTO {
    private Long id;
    private String username;
    private String prenom;
    private String password;
    private String email;

    public User toEntity() {
        User user = new User();
        user.setId(this.getId());
        user.setEmail(this.getEmail());
        user.setPassword(this.getPassword());
        user.setPrenom(this.getPrenom());
        user.setUsername(this.getUsername());
        return user;
    }

    public static UserDTO fromEntity(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPrenom(user.getPrenom());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }
}
