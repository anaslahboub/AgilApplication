package com.ensa.miniproject.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Priorite priority;

    @ManyToOne
    @JsonBackReference
    private ProductBacklog productBacklog;

    @ManyToOne(optional = true)
    private Epic epic;

}
