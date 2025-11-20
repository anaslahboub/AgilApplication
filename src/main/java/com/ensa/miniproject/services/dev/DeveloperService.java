package com.ensa.miniproject.services.dev;

import com.ensa.miniproject.dto.DeveloperDto;
import java.util.List;
import java.util.Optional;

public interface DeveloperService {

    DeveloperDto saveDeveloper(DeveloperDto developerDto);
    List<DeveloperDto> getAllDevelopers();
    Optional<DeveloperDto> getDeveloperById(Long id);
    DeveloperDto updateDeveloper(Long id, DeveloperDto developerDto);
    void deleteDeveloper(Long id);

    // Business logic
    List<DeveloperDto> findDevelopersBySpeciality(String speciality);
    DeveloperDto assignToEquipe(Long developerId, Long equipeId);
    List<DeveloperDto> getDevelopersByEquipe(Long equipeId);
}
