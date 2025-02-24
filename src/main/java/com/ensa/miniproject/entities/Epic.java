package com.ensa.miniproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Epic {
    @Id
    private Long id;
    private String title;
    private String description;

    @ManyToOne
    private ProductBacklog productBacklog;

    @OneToMany
    List<UserStory> userStories;

}
