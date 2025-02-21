package com.ensa.miniproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStory {
    @Id
    private Long id;
    private String title;
    private String description;
    private int priority;
    @ManyToOne
    private ProductBacklog productBacklog;

}
