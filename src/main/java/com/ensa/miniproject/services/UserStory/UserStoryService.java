package com.ensa.miniproject.services.UserStory;

import com.ensa.miniproject.DTO.UserStoryDTO;

import java.util.List;

public interface UserStoryService {
    public UserStoryDTO createUserStory(UserStoryDTO userStoryDTO);
    public UserStoryDTO getUserStoryById(Long id);
    public UserStoryDTO updateUserStory(UserStoryDTO userStoryDTO);
    public void deleteUserStory(Long id);
    public List<UserStoryDTO> getUserStories();
}
