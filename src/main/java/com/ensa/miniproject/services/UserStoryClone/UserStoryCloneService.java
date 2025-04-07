package com.ensa.miniproject.services.UserStoryClone;

import com.ensa.miniproject.DTO.UserStoryCloneDTO;
import java.util.List;

public interface UserStoryCloneService {
    UserStoryCloneDTO createUserStoryClone(UserStoryCloneDTO userStoryCloneDTO);
    UserStoryCloneDTO getUserStoryCloneById(Long id);
    UserStoryCloneDTO updateUserStoryClone(UserStoryCloneDTO userStoryCloneDTO);
    void deleteUserStoryClone(Long id);
    List<UserStoryCloneDTO> getUserStoryClones();

    UserStoryCloneDTO affectToUserStory(Long cloneId, Long userStoryId);
    void deleteFromUserStory(Long cloneId);
}