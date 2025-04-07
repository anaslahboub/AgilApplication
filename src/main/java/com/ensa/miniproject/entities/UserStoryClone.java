package com.ensa.miniproject.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStoryClone implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String enTantQue;
    private String JeVeux;
    private String aFinQue;
    private Etat etat;
    private Long tauxDavancenement;

    @Enumerated(EnumType.STRING)
    private Priorite priority;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "user_story_id")
    @JsonBackReference
    private UserStory originalUserStory;
}