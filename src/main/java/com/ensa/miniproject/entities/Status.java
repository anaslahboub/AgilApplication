package com.ensa.miniproject.entities;


import lombok.Getter;

@Getter
public enum Status {
    TODO("À faire", 1),
    IN_PROGRESS("En cours", 2),
    TESTING("En test", 3),
    DONE("Termine", 4);

    private final String description;
    private final int level;

    // Constructor
    Status(String description, int level) {
        this.description = description;
        this.level = level;
    }
}