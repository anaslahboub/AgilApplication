package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductBacklogDTO {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priorite priority;
    private List<Epic> epics;
    private List<UserStory> userStories;

    public ProductBacklog toEntity() {
        ProductBacklog backlog = new ProductBacklog();
        backlog.setId(id);
        backlog.setTitle(title);
        backlog.setDescription(description);
        backlog.setStatus(status);
        backlog.setPriority(priority);
        backlog.setEpics(epics);
        backlog.setUserStories(userStories);
        return backlog;
    }

    public static ProductBacklogDTO fromEntity(ProductBacklog backlog) {
        ProductBacklogDTO backlogDTO = new ProductBacklogDTO();
        backlogDTO.setId(backlog.getId());
        backlogDTO.setTitle(backlog.getTitle());
        backlogDTO.setDescription(backlog.getDescription());
        backlogDTO.setStatus(backlog.getStatus());
        backlogDTO.setPriority(backlog.getPriority());
        backlogDTO.setEpics(backlog.getEpics());
        backlogDTO.setUserStories(backlog.getUserStories());
        return backlogDTO;
    }
}
