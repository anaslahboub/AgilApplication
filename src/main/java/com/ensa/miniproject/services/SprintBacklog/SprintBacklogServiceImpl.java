package com.ensa.miniproject.services.SprintBacklog;

import com.ensa.miniproject.DTO.SprintBacklogDTO;
import com.ensa.miniproject.entities.*;
import com.ensa.miniproject.execptions.ResourceNotFoundException;
import com.ensa.miniproject.mapping.SprintBacklogMapper;
import com.ensa.miniproject.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SprintBacklogServiceImpl implements SprintBacklogService {

    private final SprintBacklogRepository sprintBacklogRepository;
    private final EpicRepository epicRepository;
    private final UserStoryRepository userStoryRepository;
    private final SprintBacklogMapper sprintBacklogMapper;

    @Override
    @Transactional
    public SprintBacklogDTO createSprintBacklog(SprintBacklogDTO sprintBacklogDTO) {
        SprintBacklog sprintBacklog = sprintBacklogMapper.toEntity(sprintBacklogDTO);
        sprintBacklog = sprintBacklogRepository.save(sprintBacklog);
        return sprintBacklogMapper.fromEntity(sprintBacklog);
    }

    @Override
    public SprintBacklogDTO getSprintBacklogById(Long id) {
        SprintBacklog sprintBacklog = sprintBacklogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SprintBacklog", "id", id));
        return sprintBacklogMapper.fromEntity(sprintBacklog);
    }

    @Override
    @Transactional
    public SprintBacklogDTO updateSprintBacklog(SprintBacklogDTO sprintBacklogDTO) {
        SprintBacklog existingSprint = sprintBacklogRepository.findById(sprintBacklogDTO.id())
                .orElseThrow(() -> new ResourceNotFoundException("SprintBacklog", "id", sprintBacklogDTO.id()));

        existingSprint.setTitle(sprintBacklogDTO.title());
        existingSprint.setDescription(sprintBacklogDTO.description());
        existingSprint.setStatus(sprintBacklogDTO.status());
        existingSprint.setPriority(sprintBacklogDTO.priority());

        SprintBacklog updatedSprint = sprintBacklogRepository.save(existingSprint);
        return sprintBacklogMapper.fromEntity(updatedSprint);
    }

    @Override
    @Transactional
    public void deleteSprintBacklog(Long id) {
        SprintBacklog sprintBacklog = sprintBacklogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SprintBacklog", "id", id));


        sprintBacklogRepository.delete(sprintBacklog);
    }

    @Override
    public List<SprintBacklogDTO> getAllSprintBacklogs() {
        return sprintBacklogRepository.findAll()
                .stream()
                .map(sprintBacklogMapper::fromEntity)
                .collect(Collectors.toList());
    }


    /// ////////////////////////
    /// METIER /////////////////
    //////////////////////////

    @Override
    @Transactional
    public SprintBacklogDTO addEpicsToSprint(Long sprintId, List<Long> epicIds) {
        SprintBacklog sprint = sprintBacklogRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("SprintBacklog", "id", sprintId));

        List<Epic> epics = epicRepository.findAllById(epicIds);

        if (epics.size() != epicIds.size()) {
            Set<Long> foundIds = epics.stream().map(Epic::getId).collect(Collectors.toSet());
            String missingIds = epicIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            throw new ResourceNotFoundException("Missing Epic IDs: " + missingIds);
        }

        epics.forEach(epic -> {
            sprint.getEpics().add(epic);
        });
        epicRepository.saveAll(epics);

        return sprintBacklogMapper.fromEntity(sprint);
    }

    @Override
    @Transactional
    public SprintBacklogDTO removeEpicFromSprint(Long sprintId, Long epicId) {
        SprintBacklog sprint = sprintBacklogRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("SprintBacklog", "id", sprintId));

        Epic epic = epicRepository.findById(epicId)
                .orElseThrow(() -> new ResourceNotFoundException("Epic", "id", epicId));



        sprint.getEpics().remove(epic);
        epicRepository.save(epic);

        return sprintBacklogMapper.fromEntity(sprint);
    }

    @Override
    @Transactional
    public SprintBacklogDTO addUserStoriesToSprint(Long sprintId, List<Long> userStoryIds) {
        SprintBacklog sprint = sprintBacklogRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("SprintBacklog", "id", sprintId));

        List<UserStory> userStories = userStoryRepository.findAllById(userStoryIds);

        if (userStories.size() != userStoryIds.size()) {
            Set<Long> foundIds = userStories.stream().map(UserStory::getId).collect(Collectors.toSet());
            String missingIds = userStoryIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            throw new ResourceNotFoundException("Missing UserStory IDs: " + missingIds);
        }

        userStories.forEach(userStory -> {
            sprint.getUserStories().add(userStory);
        });
        userStoryRepository.saveAll(userStories);

        return sprintBacklogMapper.fromEntity(sprint);
    }

    @Override
    @Transactional
    public SprintBacklogDTO removeUserStoryFromSprint(Long sprintId, Long userStoryId) {
        SprintBacklog sprint = sprintBacklogRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("SprintBacklog", "id", sprintId));

        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException("UserStory", "id", userStoryId));



        sprint.getUserStories().remove(userStory);
        userStoryRepository.save(userStory);

        return sprintBacklogMapper.fromEntity(sprint);
    }
}