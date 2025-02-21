package com.ensa.miniproject.services;

import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.entities.Priorite;
import com.ensa.miniproject.entities.ScrumMaster;
import com.ensa.miniproject.entities.UserStory;

import java.util.List;

public interface ScrumMasterService {


    public ScrumMaster createScrumMaster(ScrumMaster scrumMaster);
    public ScrumMaster getScrumMasterById(Long id);
    public ScrumMaster updateScrumMaster(ScrumMaster scrumMaster);
    public void deleteScrumMaster(Long id);
    public List<ScrumMaster> getScrumMasters();


    ///


    public UserStory createUserStory(UserStory userStory);
    public UserStory getUserStoryById(Long id);
    public UserStory updateUserStory(UserStory userStory);
    public void deleteUserStory(Long id);
    public List<UserStory> getUserStories();


    ///

    public Epic createEpic(Epic epic);
    public Epic getEpicById(Long id);
    public Epic updateEpic(Epic epic);
    public void deleteEpic(Long id);
    public List<Epic> getEpics();


    ///

    public UserStory assignerPrioriteToUserStory(Long userStoryId, Priorite priorite);
    public void affectUserStoryToEpic(Long userStoryId, Epic epic);





}
