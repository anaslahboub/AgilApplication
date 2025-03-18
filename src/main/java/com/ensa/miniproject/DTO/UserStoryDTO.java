package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.entities.Priorite;
import com.ensa.miniproject.entities.ProductBacklog;
import com.ensa.miniproject.entities.UserStory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserStoryDTO {
    private Long id;
    private String title;
    private String description;
    private Priorite priority;
    private ProductBacklog productBacklog;
    private Epic epic;

    public UserStory toEntity() {
        UserStory userStory = new UserStory();
        userStory.setId(id);
        userStory.setTitle(title);
        userStory.setDescription(description);
        userStory.setPriority(priority);
        userStory.setProductBacklog(productBacklog);
        userStory.setEpic(epic);
        return userStory;
    }

    public static UserStoryDTO fromEntity(UserStory userStory) {
        UserStoryDTO userStoryDTO = new UserStoryDTO();
        userStoryDTO.setId(userStory.getId());
        userStoryDTO.setTitle(userStory.getTitle());
        userStoryDTO.setDescription(userStory.getDescription());
        userStoryDTO.setPriority(userStory.getPriority());
        userStoryDTO.setProductBacklog(userStory.getProductBacklog());
        userStoryDTO.setEpic(userStory.getEpic());
        return userStoryDTO;
    }
}
