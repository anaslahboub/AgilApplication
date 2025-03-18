package com.ensa.miniproject.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String enTantQue;
    private String JeVeux;
    private String aFinQue ;
    private Etat etat;

    @Enumerated(EnumType.STRING)
    private Priorite priority;

    @ManyToOne
    @JsonBackReference
    private ProductBacklog productBacklog;

    @ManyToOne(optional = true)
    private Epic epic;

}
