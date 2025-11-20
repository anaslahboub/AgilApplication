package com.ensa.miniproject.dto;

import com.ensa.miniproject.entities.SprintBacklog;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SprintDto {
    private int id;
    private String name;
    private Long days;
    private SprintBacklog sprintBacklog;
}
