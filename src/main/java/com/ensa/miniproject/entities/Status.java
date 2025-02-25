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

//    // Helper method to get status by level
//    public static Status getByLevel(int level) {
//        for (Status status : Status.values()) {
//            if (status.getLevel() == level) {
//                return status;
//            }
//        }
//        throw new IllegalArgumentException("Invalid status level: " + level);
//    }
//
//    // Helper method to get the next status
//    public Status getNextStatus() {
//        int nextLevel = this.level + 1;
//        for (Status status : Status.values()) {
//            if (status.getLevel() == nextLevel) {
//                return status;
//            }
//        }
//        return this; // Return the same status if no next status found
//    }
}