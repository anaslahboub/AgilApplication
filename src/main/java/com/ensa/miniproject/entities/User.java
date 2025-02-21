package com.ensa.miniproject.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public abstract class  User {
    @Id
    private Long id;
    private String username;
    private String prenom;
    private String password;
    private String email;
}
