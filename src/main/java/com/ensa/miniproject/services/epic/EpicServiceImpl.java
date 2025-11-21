package com.ensa.miniproject.services.epic;

import com.ensa.miniproject.dto.EpicDTO;
import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.entities.UserStory;
import com.ensa.miniproject.execptions.ResourceNotFoundException;
import com.ensa.miniproject.mapping.EpicMapper;
import com.ensa.miniproject.repository.EpicRepository;
import com.ensa.miniproject.repository.UserStoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EpicServiceImpl implements EpicService{

    private final EpicRepository epicRepository;
    private final EpicMapper epicMapper;
    private final UserStoryRepository userStoryRepository;
    public static final String EPIC_NOT_FOUND = "epic not found with id: ";

    @Override
    public EpicDTO createEpic(EpicDTO epicDTO) {
        Epic epic = epicMapper.toEntity(epicDTO);
        epic = epicRepository.save(epic);
        return epicMapper.fromEntity(epic);
    }

    @Override
    public EpicDTO getEpicById(Long id) {
        return epicMapper.fromEntity(epicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EPIC_NOT_FOUND+ id)));
    }

    @Override
    public EpicDTO updateEpic(EpicDTO epicDTO) {
        Epic existingEpic = epicRepository.findById(epicDTO.id())
                .orElseThrow(() -> new EntityNotFoundException(EPIC_NOT_FOUND+ epicDTO.id()));
        // Update fields
        existingEpic.setTitle(epicDTO.title());
        existingEpic.setDescription(epicDTO.description());
        // Save the updated entity
        existingEpic = epicRepository.save(existingEpic);
        return epicMapper.fromEntity(existingEpic);
    }

    @Override
    public void deleteEpic(Long id) {
        Epic epic = epicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EPIC_NOT_FOUND + id));
        epicRepository.delete(epic);
    }

    @Override
    public List<EpicDTO> getEpics() {
        return epicRepository.findAll()
                .stream()
                .map(epicMapper::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public EpicDTO affectUserStoriesToEpic(Long epicId, List<Long> userStoryIds) {
        // 1. Find the epic
        Epic epic = epicRepository.findById(epicId)
                .orElseThrow(() -> new ResourceNotFoundException("epic", "id", epicId));
        // 2. Find all UserStories
        List<UserStory> userStories = userStoryRepository.findAllById(userStoryIds);
        // 3. Verify all requested UserStories were found
        if (userStories.size() != userStoryIds.size()) {
            throw new ResourceNotFoundException("One or more UserStories not found");
        }
        // 4. Update each userstory's epic reference
        userStories.forEach(userStory -> epic.getUserStories().add(userStory) );// Sync parent -> child
        userStoryRepository.saveAll(userStories);
        // 5. Refresh the epic to get updated user stories
        Epic res = epicRepository.findById(epicId)
                .orElseThrow(() -> new ResourceNotFoundException("epic", "id", epicId));
        return epicMapper.fromEntity(res);
    }
    @Override
    @Transactional
    public EpicDTO removeUserStoryFromEpic(Long epicId, Long userStoryId) {
        // 1. Fetch the epic (fail if not found)
        Epic epic = epicRepository.findById(epicId)
                .orElseThrow(() -> new ResourceNotFoundException("epic", "id", epicId));
        // 2. Fetch the userstory and verify it belongs to this epic
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new ResourceNotFoundException("userstory", "id", userStoryId));
        // 3. Remove the relationship (using helper method)
        epic.getUserStories().remove(userStory); // Updates both sides of the relationship
        // 4. Save changes
        userStoryRepository.save(userStory); // Updates the foreign key to NULL
        // 5. Return the updated epic
        return epicMapper.fromEntity(epic);
    }
}
