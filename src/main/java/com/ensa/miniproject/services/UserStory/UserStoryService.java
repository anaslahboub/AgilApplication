package com.ensa.miniproject.services.UserStory;

import com.ensa.miniproject.DTO.TaskDTO;
import com.ensa.miniproject.DTO.UserStoryDTO;
import com.ensa.miniproject.entities.Task;

import java.util.List;

public interface UserStoryService {
    public UserStoryDTO createUserStory(UserStoryDTO userStoryDTO);
    public UserStoryDTO getUserStoryById(Long id);
    public UserStoryDTO updateUserStory(UserStoryDTO userStoryDTO);
    public void deleteUserStory(Long id);
    public List<UserStoryDTO> getUserStories();

    /// /////////////////
    /// //metier/////////
    ///  ////////////////
    public TaskDTO addTask(Long userStoryId,TaskDTO taskDTO);
    public void deleteTask(Long userStoryId,TaskDTO taskDTO);

    public UserStoryDTO calculerTauxDavancement(Long userStoryId);

}
