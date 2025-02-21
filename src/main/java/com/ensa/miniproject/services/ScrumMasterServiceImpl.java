package com.ensa.miniproject.services;

import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.entities.Priorite;
import com.ensa.miniproject.entities.ScrumMaster;
import com.ensa.miniproject.entities.UserStory;

import java.util.List;

public class ScrumMasterServiceImpl implements ScrumMasterService {
    @Override
    public ScrumMaster createScrumMaster(ScrumMaster scrumMaster) {
        return null;
    }

    @Override
    public ScrumMaster getScrumMasterById(Long id) {
        return null;
    }

    @Override
    public ScrumMaster updateScrumMaster(ScrumMaster scrumMaster) {
        return null;
    }

    @Override
    public void deleteScrumMaster(Long id) {

    }

    @Override
    public List<ScrumMaster> getScrumMasters() {
        return List.of();
    }


    @Override
    public UserStory createUserStory(UserStory userStory) {
        return null;
    }

    @Override
    public UserStory getUserStoryById(Long id) {
        return null;
    }

    @Override
    public UserStory updateUserStory(UserStory userStory) {
        return null;
    }

    @Override
    public void deleteUserStory(Long id) {

    }

    @Override
    public List<UserStory> getUserStories() {
        return List.of();
    }

    @Override
    public Epic createEpic(Epic epic) {
        return null;
    }

    @Override
    public Epic getEpicById(Long id) {
        return null;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        return null;
    }

    @Override
    public void deleteEpic(Long id) {

    }

    @Override
    public List<Epic> getEpics() {
        return List.of();
    }

    @Override
    public UserStory assignerPrioriteToUserStory(Long userStoryId, Priorite priorite) {
        return null;
    }

    @Override
    public void affectUserStoryToEpic(Long userStoryId, Epic epic) {

    }
}
