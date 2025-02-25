package com.ensa.miniproject.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priorite priority;

    @OneToMany(mappedBy = "productBacklog")
    @JsonManagedReference
    private List<Epic> epics;


    @OneToMany(mappedBy = "productBacklog")
    @JsonBackReference
    private List<UserStory> userStories;

    @ManyToOne
    @JsonBackReference
    private Project project;
}
