package com.ensa.miniproject.entities;

import lombok.Getter;

@Getter

public enum Priorite {
    MUST_HAVE("Must Have", 1),
    SHOULD_HAVE("Should Have", 2),
    COULD_HAVE("Could Have", 3),
    WONT_HAVE("Won't Have", 4);

    private final String description;
    private final int level;

    // Constructor
    Priorite(String description, int level) {
        this.description = description;
        this.level = level;
    }

}