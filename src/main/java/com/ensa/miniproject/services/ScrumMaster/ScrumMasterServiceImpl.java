package com.ensa.miniproject.services.ScrumMaster;

import com.ensa.miniproject.DTO.ScrumMasterDTO;
import com.ensa.miniproject.entities.ScrumMaster;
import com.ensa.miniproject.mapping.EpicMapper;
import com.ensa.miniproject.mapping.ScrumMasterMapper;
import com.ensa.miniproject.mapping.UserStoryMapper;
import com.ensa.miniproject.repository.EpicRepository;
import com.ensa.miniproject.repository.ScrumMasterRepository;
import com.ensa.miniproject.repository.UserStoryRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class ScrumMasterServiceImpl implements ScrumMasterService {
    private final ScrumMasterRepository scrumMasterRepository;
    private final ScrumMasterMapper scrumMasterMapper;


    public ScrumMasterServiceImpl(ScrumMasterRepository scrumMasterRepository,
                                  UserStoryRepository userStoryRepository,
                                  ScrumMasterMapper scrumMasterMapper
                                  ) {
        this.scrumMasterRepository = scrumMasterRepository;
        this.scrumMasterMapper = scrumMasterMapper;
        

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
}
