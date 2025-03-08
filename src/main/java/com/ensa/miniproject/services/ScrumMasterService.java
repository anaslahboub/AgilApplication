package com.ensa.miniproject.services;

import com.ensa.miniproject.DTO.EpicDTO;
import com.ensa.miniproject.DTO.ScrumMasterDTO;
import com.ensa.miniproject.DTO.UserStoryDTO;
import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.entities.Priorite;
import com.ensa.miniproject.entities.ScrumMaster;
import com.ensa.miniproject.entities.UserStory;

import java.util.List;

public interface ScrumMasterService {


    public ScrumMasterDTO createScrumMaster(ScrumMasterDTO scrumMasterDTO);
    public ScrumMasterDTO getScrumMasterById(Long id);
    public ScrumMasterDTO updateScrumMaster(ScrumMasterDTO scrumMasterDTO);
    public void deleteScrumMaster(Long id);
    public List<ScrumMasterDTO> getScrumMasters();


    ///


    public UserStoryDTO createUserStory(UserStoryDTO userStoryDTO);
    public UserStoryDTO getUserStoryById(Long id);
    public UserStoryDTO updateUserStory(UserStoryDTO userStoryDTO);
    public void deleteUserStory(Long id);
    public List<UserStoryDTO> getUserStories();


    ///

    public EpicDTO createEpic(EpicDTO epicDTO);
    public EpicDTO getEpicById(Long id);
    public EpicDTO updateEpic(EpicDTO epicDTO);
    public void deleteEpic(Long id);
    public List<EpicDTO> getEpics();


    ///

    public UserStoryDTO assignerPrioriteToUserStory(Long userStoryId, Priorite priorite);
    public void affectUserStoryToEpic(Long userStoryId, Long epicId);





}
