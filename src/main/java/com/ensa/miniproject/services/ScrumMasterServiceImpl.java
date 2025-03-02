package com.ensa.miniproject.services;

import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.entities.Priorite;
import com.ensa.miniproject.entities.ScrumMaster;
import com.ensa.miniproject.entities.UserStory;
import com.ensa.miniproject.repository.EpicRepository;
import com.ensa.miniproject.repository.ScrumMasterRepository;
import com.ensa.miniproject.repository.UserStoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrumMasterServiceImpl implements ScrumMasterService {
    ScrumMasterRepository scrumMasterRepository;
    UserStoryRepository userStoryRepository;
    EpicRepository epicRepository;
    public ScrumMasterServiceImpl(ScrumMasterRepository scrumMasterRepository,UserStoryRepository userStoryRepository,EpicRepository epicRepository ) {
        this.scrumMasterRepository = scrumMasterRepository;
        this.userStoryRepository = userStoryRepository;
        this.epicRepository = epicRepository;
    }


    @Override
    public ScrumMaster createScrumMaster(ScrumMaster scrumMaster) {
        scrumMasterRepository.save(scrumMaster);
        return scrumMaster;
    }

    @Override
    public ScrumMaster getScrumMasterById(Long id) {
        return scrumMasterRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Projet avec ID " + id + " non trouvé"));
    }

    @Override
    public ScrumMaster updateScrumMaster(ScrumMaster scrumMaster) {
        scrumMasterRepository.save(scrumMaster);
        return scrumMaster;
    }

    @Override
    public void deleteScrumMaster(Long id) {
        scrumMasterRepository.delete(getScrumMasterById(id));
    }

    @Override
    public List<ScrumMaster> getScrumMasters() {
        return scrumMasterRepository.findAll();
    }


    @Override
    public UserStory createUserStory(UserStory userStory) {
        return userStoryRepository.save(userStory);
    }

    @Override
    public UserStory getUserStoryById(Long id) {

        return userStoryRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Projet avec ID " + id + " non trouvé"));
    }

    @Override
    public UserStory updateUserStory(UserStory userStory) {

        return userStoryRepository.save(userStory);
    }

    @Override
    public void deleteUserStory(Long id) {
        userStoryRepository.delete(this.getUserStoryById(id));
    }

    @Override
    public List<UserStory> getUserStories() {

        return userStoryRepository.findAll();
    }

    @Override
    public Epic createEpic(Epic epic) {

        return epicRepository.save(epic);
    }

    @Override
    public Epic getEpicById(Long id) {
        return epicRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Projet avec ID " + id + " non trouvé"));
    }

    @Override
    public Epic updateEpic(Epic epic) {

        return epicRepository.save(epic);
    }

    @Override
    public void deleteEpic(Long id) {
        epicRepository.delete(this.getEpicById(id));
    }

    @Override
    public List<Epic> getEpics() {

        return epicRepository.findAll();
    }

    @Override
    public UserStory assignerPrioriteToUserStory(Long userStoryId, Priorite priorite) {

        return null;
    }

    @Override
    public void affectUserStoryToEpic(Long userStoryId, Epic epic) {
        UserStory us = this.getUserStoryById(userStoryId);
        us.setEpic(epic);
    }
}
