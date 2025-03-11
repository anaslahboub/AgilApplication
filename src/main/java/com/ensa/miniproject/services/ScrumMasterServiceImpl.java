package com.ensa.miniproject.services;

import com.ensa.miniproject.DTO.EpicDTO;
import com.ensa.miniproject.DTO.ScrumMasterDTO;
import com.ensa.miniproject.DTO.UserStoryDTO;
import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.entities.Priorite;
import com.ensa.miniproject.entities.ScrumMaster;
import com.ensa.miniproject.entities.UserStory;
import com.ensa.miniproject.mapping.EpicMapper;
import com.ensa.miniproject.mapping.ScrumMasterMapper;
import com.ensa.miniproject.mapping.UserStoryMapper;
import com.ensa.miniproject.repository.EpicRepository;
import com.ensa.miniproject.repository.ScrumMasterRepository;
import com.ensa.miniproject.repository.UserStoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScrumMasterServiceImpl implements ScrumMasterService {
    private final ScrumMasterRepository scrumMasterRepository;
    private final UserStoryRepository userStoryRepository;
    private final EpicRepository epicRepository;
    private final ScrumMasterMapper scrumMasterMapper;
    private final UserStoryMapper userStoryMapper;
    private final EpicMapper epicMapper;


    public ScrumMasterServiceImpl(ScrumMasterRepository scrumMasterRepository,
                                  UserStoryRepository userStoryRepository,
                                  EpicRepository epicRepository,
                                  ScrumMasterMapper scrumMasterMapper,
                                  UserStoryMapper userStoryMapper,
                                  EpicMapper epicMapper) {
        this.scrumMasterRepository = scrumMasterRepository;
        this.userStoryRepository = userStoryRepository;
        this.epicRepository = epicRepository;
        this.scrumMasterMapper = scrumMasterMapper;
        this.userStoryMapper = userStoryMapper;
        this.epicMapper = epicMapper;
    }

    @Override
    public ScrumMasterDTO createScrumMaster(ScrumMasterDTO scrumMasterDTO) {
        ScrumMaster scrumMaster = ScrumMasterMapper.INSTANCE.toEntity(scrumMasterDTO);
        scrumMaster = scrumMasterRepository.save(scrumMaster);
        return ScrumMasterMapper.INSTANCE.fromEntity(scrumMaster);
    }

    @Override
    public ScrumMasterDTO getScrumMasterById(Long id) {
        ScrumMaster scrumMaster = scrumMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No ScrumMaster found with id: " + id));
        return ScrumMasterMapper.INSTANCE.fromEntity(scrumMaster);
    }

    @Override
    public ScrumMasterDTO updateScrumMaster(ScrumMasterDTO scrumMasterDTO) {
        ScrumMaster existingScrumMaster = scrumMasterRepository.findById(scrumMasterDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("No ScrumMaster found with id: " + scrumMasterDTO.getId()));

        // Update fields
        existingScrumMaster.setPrenom(scrumMasterDTO.getPrenom());
        existingScrumMaster.setEmail(scrumMasterDTO.getEmail());
        existingScrumMaster.setProjects(scrumMasterDTO.getProjects());
        existingScrumMaster.setPassword(scrumMasterDTO.getPassword());
        existingScrumMaster.setUsername(scrumMasterDTO.getUsername());

        // Save the updated entity
        existingScrumMaster = scrumMasterRepository.save(existingScrumMaster);
        return ScrumMasterMapper.INSTANCE.fromEntity(existingScrumMaster);
    }

    @Override
    public void deleteScrumMaster(Long id) {
        ScrumMaster scrumMaster = scrumMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No ScrumMaster found with id: " + id));
        scrumMasterRepository.delete(scrumMaster);
    }

    @Override
    public List<ScrumMasterDTO> getScrumMasters() {
        return scrumMasterRepository.findAll()
                .stream() // Convert the list to a stream
                .map(scrumMasterMapper::fromEntity) // Use MapStruct mapper to convert each entity to DTO
                .collect(Collectors.toList()); // Collect the results into a list
    }

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

    @Override
    public EpicDTO createEpic(EpicDTO epicDTO) {
        Epic epic = EpicMapper.INSTANCE.toEntity(epicDTO);
        epic = epicRepository.save(epic);
        return EpicMapper.INSTANCE.fromEntity(epic);
    }

    @Override
    public EpicDTO getEpicById(Long id) {
        return EpicMapper.INSTANCE.fromEntity(epicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Epic not found with id: " + id)));
    }

    @Override
    public EpicDTO updateEpic(EpicDTO epicDTO) {
        Epic existingEpic = epicRepository.findById(epicDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Epic not found with id: " + epicDTO.getId()));

        // Update fields
        existingEpic.setTitle(epicDTO.getTitle());
        existingEpic.setDescription(epicDTO.getDescription());
        existingEpic.setProductBacklog(epicDTO.getProductBacklog());

        // Save the updated entity
        existingEpic = epicRepository.save(existingEpic);
        return EpicMapper.INSTANCE.fromEntity(existingEpic);
    }

    @Override
    public void deleteEpic(Long id) {
        Epic epic = epicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Epic not found with id: " + id));
        epicRepository.delete(epic);
    }

    @Override
    public List<EpicDTO> getEpics() {
        return epicRepository.findAll()
                .stream() // Convert the list to a stream
                .map(epicMapper::fromEntity) // Use the injected mapper to map entities to DTOs
                .collect(Collectors.toList()); // Collect the results into a list
    }

    @Override
    public UserStoryDTO assignerPrioriteToUserStory(Long userStoryId, Priorite priorite) {
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + userStoryId));
        userStory.setPriority(priorite);
        userStory = userStoryRepository.save(userStory);
        return UserStoryDTO.fromEntity(userStory);
    }

    @Override
    @Transactional
    public void affectUserStoryToEpic(Long userStoryId, Long epicId) {
        Epic epic = epicRepository.findById(epicId)
                .orElseThrow(() -> new EntityNotFoundException("Epic not found with id: " + epicId));
        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new EntityNotFoundException("UserStory not found with id: " + userStoryId));

        // Update the epic

        userStory.setEpic(epic);
    }
}