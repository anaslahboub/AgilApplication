package com.ensa.miniproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Epic {
    @Id
    private Long id;
    private String title;
    private String description;

    @ManyToOne
    private ProductBacklog productBacklog;

}
