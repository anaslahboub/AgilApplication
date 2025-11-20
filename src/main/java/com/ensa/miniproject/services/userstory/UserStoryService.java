package com.ensa.miniproject.services.userstory;

import com.ensa.miniproject.dto.TaskDTO;
import com.ensa.miniproject.dto.UserStoryDTO;
import java.util.List;

public interface UserStoryService {
     UserStoryDTO createUserStory(UserStoryDTO userStoryDTO);
     UserStoryDTO getUserStoryById(Long id);
     UserStoryDTO updateUserStory(UserStoryDTO userStoryDTO);
     void deleteUserStory(Long id);
     List<UserStoryDTO> getUserStories();
     TaskDTO addTask(Long userStoryId,TaskDTO taskDTO);
     void deleteTask(Long userStoryId,TaskDTO taskDTO);
     UserStoryDTO calculerTauxDavancement(Long userStoryId);

}
