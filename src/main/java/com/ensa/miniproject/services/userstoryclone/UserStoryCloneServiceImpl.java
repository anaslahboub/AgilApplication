package com.ensa.miniproject.services.userstoryclone;

import com.ensa.miniproject.dto.UserStoryCloneDTO;
import com.ensa.miniproject.entities.UserStory;
import com.ensa.miniproject.entities.UserStoryClone;
import com.ensa.miniproject.mapping.UserStoryCloneMapper;
import com.ensa.miniproject.repository.UserStoryCloneRepository;
import com.ensa.miniproject.repository.UserStoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserStoryCloneServiceImpl implements UserStoryCloneService {

    private final UserStoryCloneRepository userStoryCloneRepository;
    private final UserStoryRepository userStoryRepository;
    private final UserStoryCloneMapper userStoryCloneMapper;
    private static final String USER_STORY_CLONE_NOT_FOUND ="userstoryclone not found with id: ";

    @Override
    @Transactional
    public UserStoryCloneDTO createUserStoryClone(UserStoryCloneDTO userStoryCloneDTO) {
        UserStoryClone userStoryClone = userStoryCloneMapper.toEntity(userStoryCloneDTO);
        userStoryClone = userStoryCloneRepository.save(userStoryClone);
        return userStoryCloneMapper.fromEntity(userStoryClone);
    }

    @Override
    @Transactional(readOnly = true)
    public UserStoryCloneDTO getUserStoryCloneById(Long id) {
        UserStoryClone userStoryClone = userStoryCloneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException( USER_STORY_CLONE_NOT_FOUND+ id));
        return userStoryCloneMapper.fromEntity(userStoryClone);
    }

    @Override
    @Transactional
    public UserStoryCloneDTO updateUserStoryClone(UserStoryCloneDTO userStoryCloneDTO) {
        UserStoryClone existingClone = userStoryCloneRepository.findById(userStoryCloneDTO.id())
                .orElseThrow(() -> new EntityNotFoundException(USER_STORY_CLONE_NOT_FOUND+ userStoryCloneDTO.id()));

        existingClone.setTitle(userStoryCloneDTO.title());
        existingClone.setDescription(userStoryCloneDTO.description());
        existingClone.setEnTantQue(userStoryCloneDTO.enTantQue());
        existingClone.setJeVeux(userStoryCloneDTO.JeVeux());
        existingClone.setAFinQue(userStoryCloneDTO.aFinQue());
        existingClone.setEtat(userStoryCloneDTO.etat());
        existingClone.setTauxDavancenement(userStoryCloneDTO.tauxDavancement());
        existingClone.setPriority(userStoryCloneDTO.priority());
        existingClone.setTasks(userStoryCloneDTO.tasks());

        existingClone = userStoryCloneRepository.save(existingClone);
        return userStoryCloneMapper.fromEntity(existingClone);
    }

    @Override
    @Transactional
    public void deleteUserStoryClone(Long id) {
        UserStoryClone userStoryClone = userStoryCloneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_STORY_CLONE_NOT_FOUND+ id));
        userStoryCloneRepository.delete(userStoryClone);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserStoryCloneDTO> getUserStoryClones() {
        return userStoryCloneRepository.findAll()
                .stream()
                .map(userStoryCloneMapper::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public UserStoryCloneDTO affectToUserStory(Long cloneId, Long userStoryId) {
        UserStoryClone clone = userStoryCloneRepository.findById(cloneId)
                .orElseThrow(() -> new EntityNotFoundException(USER_STORY_CLONE_NOT_FOUND + cloneId));

        UserStory original = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new EntityNotFoundException(USER_STORY_CLONE_NOT_FOUND + userStoryId));

        clone.setOriginalUserStory(original);
        clone = userStoryCloneRepository.save(clone);

        return userStoryCloneMapper.fromEntity(clone);
    }

    @Override
    @Transactional
    public void deleteFromUserStory(Long cloneId) {
        UserStoryClone clone = userStoryCloneRepository.findById(cloneId)
                .orElseThrow(() -> new EntityNotFoundException(USER_STORY_CLONE_NOT_FOUND + cloneId));

        clone.setOriginalUserStory(null);
        userStoryCloneRepository.save(clone);
    }
}