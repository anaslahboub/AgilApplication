package com.ensa.miniproject.services.UserStory;

import com.ensa.miniproject.DTO.UserStoryDTO;
import com.ensa.miniproject.entities.UserStory;
import com.ensa.miniproject.mapping.UserStoryMapper;
import com.ensa.miniproject.repository.UserStoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class UserStoryServiceImpl implements UserStoryService {

    private final UserStoryRepository userStoryRepository;
    private final UserStoryMapper userStoryMapper;




    @Override
    public UserStoryDTO createUserStory(UserStoryDTO userStoryDTO) {
        UserStory userStory = UserStoryMapper.INSTANCE.toEntity(userStoryDTO);
        userStory = userStoryRepository.save(userStory);
        return UserStoryMapper.INSTANCE.fromEntity(userStory);
    }

    @Override
    public UserStoryDTO getUserStoryById(Long id) {
        UserStory userStory = userStoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + id));
        return UserStoryMapper.INSTANCE.fromEntity(userStory);
    }

    @Override
    public UserStoryDTO updateUserStory(UserStoryDTO userStoryDTO) {
        UserStory existingUserStory = userStoryRepository.findById(userStoryDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + userStoryDTO.getId()));

        // Update fields
        existingUserStory.setTitle(userStoryDTO.getTitle());
        existingUserStory.setDescription(userStoryDTO.getDescription());
        existingUserStory.setPriority(userStoryDTO.getPriority());
        existingUserStory.setEpic(userStoryDTO.getEpic());
        existingUserStory.setProductBacklog(userStoryDTO.getProductBacklog());

        // Save the updated entity
        existingUserStory = userStoryRepository.save(existingUserStory);
        return UserStoryMapper.INSTANCE.fromEntity(existingUserStory);
    }

    @Override
    public void deleteUserStory(Long id) {
        UserStory userStory = userStoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + id));
        userStoryRepository.delete(userStory);
    }

    @Override
    public List<UserStoryDTO> getUserStories() {
        return userStoryRepository.findAll()
                .stream() // Convert the list to a stream
                .map(userStoryMapper::fromEntity) // Use the injected mapper to map entities to DTOs
                .collect(Collectors.toList()); // Collect the results into a list
    }
}
