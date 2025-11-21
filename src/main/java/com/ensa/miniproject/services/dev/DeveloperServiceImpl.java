package com.ensa.miniproject.services.dev;

import com.ensa.miniproject.dto.DeveloperDto;
import com.ensa.miniproject.entities.Developer;
import com.ensa.miniproject.entities.EquipeDevelopement;
import com.ensa.miniproject.mapping.DeveloperMapper;
import com.ensa.miniproject.repository.DeveloperRepository;
import com.ensa.miniproject.repository.EquipeDevelopementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import static com.ensa.miniproject.execptions.ErrorMessages.*;

@Service
@AllArgsConstructor
@Transactional
public class DeveloperServiceImpl implements DeveloperService {
    private final  DeveloperRepository developerRepository;
    private final DeveloperMapper developerMapper;
    private final EquipeDevelopementRepository equipeRepository;

    @Override
    public DeveloperDto saveDeveloper(DeveloperDto developerDto) {
        Developer dev = developerMapper.toEntity(developerDto);
        return developerMapper.fromEntity(developerRepository.save(dev));
    }

    @Override
    public List<DeveloperDto> getAllDevelopers() {
        return developerRepository.findAll()
                .stream()
                .map(developerMapper::fromEntity)
                .toList();
    }

    @Override
    public Optional<DeveloperDto> getDeveloperById(Long id) {
        return developerRepository.findById(id).map(developerMapper::fromEntity);
    }

    @Override
    public DeveloperDto updateDeveloper(Long id, DeveloperDto dto) {
        Developer dev = developerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(DEVELOPER_NOT_FOUND));
        dev.setUsername(dto.getUsername());
        dev.setPrenom(dto.getPrenom());
        dev.setEmail(dto.getEmail());
        dev.setPassword(dto.getPassword());
        dev.setSpeciality(dto.getSpeciality());
        dev.setExperienceYears(dto.getExperienceYears());

        return developerMapper.fromEntity(developerRepository.save(dev));
    }

    @Override
    public void deleteDeveloper(Long id) {
        developerRepository.deleteById(id);
    }

    // ------------------------- METIER -------------------------

    @Override
    public List<DeveloperDto> findDevelopersBySpeciality(String speciality) {
        return developerRepository.findBySpecialityIgnoreCase(speciality)
                .stream()
                .map(developerMapper::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public DeveloperDto assignToEquipe(Long developerId, Long equipeId) {
        Developer dev = developerRepository.findById(developerId)
                .orElseThrow(() -> new RuntimeException(DEVELOPER_NOT_FOUND));

        EquipeDevelopement equipe = equipeRepository.findById(equipeId)
                .orElseThrow(() -> new RuntimeException("Equipe not found"));

        dev.setEquipe(equipe);
        equipe.getDevelopers().add(dev);

        return developerMapper.fromEntity(developerRepository.save(dev));
    }

    @Override
    public List<DeveloperDto> getDevelopersByEquipe(Long equipeId) {
        return developerRepository.findByEquipe_Id(equipeId)
                .stream()
                .map(developerMapper::fromEntity)
                .toList();
    }
}
