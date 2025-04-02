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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScrumMasterServiceImpl implements ScrumMasterService {
    private final ScrumMasterRepository scrumMasterRepository;
    private final ScrumMasterMapper scrumMasterMapper;

    @Autowired
    public ScrumMasterServiceImpl(ScrumMasterRepository scrumMasterRepository,
                                  UserStoryRepository userStoryRepository,
                                  ScrumMasterMapper scrumMasterMapper
                                  ) {
        this.scrumMasterRepository = scrumMasterRepository;
        this.scrumMasterMapper = scrumMasterMapper;
        

    }
    @Override
    public ScrumMasterDTO createScrumMaster(ScrumMasterDTO scrumMasterDTO) {
        ScrumMaster scrumMaster = scrumMasterMapper.toEntity(scrumMasterDTO);
        scrumMaster = scrumMasterRepository.save(scrumMaster);
        return scrumMasterMapper.fromEntity(scrumMaster);
    }

    @Override
    public ScrumMasterDTO getScrumMasterById(Long id) {
        ScrumMaster scrumMaster = scrumMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No ScrumMaster found with id: " + id));
        return scrumMasterMapper.fromEntity(scrumMaster);
    }

    @Override
    public ScrumMasterDTO updateScrumMaster(ScrumMasterDTO scrumMasterDTO) {
        ScrumMaster existingScrumMaster = scrumMasterRepository.findById(scrumMasterDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("No ScrumMaster found with id: " + scrumMasterDTO.id()));

        // Update fields
        existingScrumMaster.setPrenom(scrumMasterDTO.prenom());
        existingScrumMaster.setEmail(scrumMasterDTO.email());
        existingScrumMaster.setPassword(scrumMasterDTO.password());
        existingScrumMaster.setUsername(scrumMasterDTO.username());

        // Save the updated entity
        existingScrumMaster = scrumMasterRepository.save(existingScrumMaster);
        return scrumMasterMapper.fromEntity(existingScrumMaster);
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
