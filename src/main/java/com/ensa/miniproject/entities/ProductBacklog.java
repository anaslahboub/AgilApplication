package com.ensa.miniproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBacklog {
    @Id
    private Long id;
    private String title;
    private String description;
    private String status;
    private int priority;
    @OneToMany
    private List<Epic> epics;
    @OneToMany
    private List<UserStory> userStories;
    @ManyToOne
    private Project project;
}
