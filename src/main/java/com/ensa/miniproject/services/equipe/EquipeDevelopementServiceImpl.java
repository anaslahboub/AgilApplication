package com.ensa.miniproject.services.equipe;

import com.ensa.miniproject.DTO.EquipeDevelopementDTO;
import com.ensa.miniproject.entities.Developer;
import com.ensa.miniproject.entities.EquipeDevelopement;
import com.ensa.miniproject.execptions.ResourceNotFoundException;
import com.ensa.miniproject.mapping.EquipeDevelopementMapper;
import com.ensa.miniproject.repository.DeveloperRepository;
import com.ensa.miniproject.repository.EquipeDevelopementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class EquipeDevelopementServiceImpl implements EquipeDevelopementService {

    private final EquipeDevelopementRepository equipeRepository;
    private final DeveloperRepository developerRepository;
    private final EquipeDevelopementMapper equipeDevelopementMapper;

    @Override
    public EquipeDevelopementDTO createEquipe(EquipeDevelopementDTO equipeDevelopementDTO) {
        EquipeDevelopement equipeDevelopement = new EquipeDevelopement();

        if(equipeDevelopementDTO.developers() != null) {
            equipeDevelopement.setDevelopers(equipeDevelopementDTO.developers());
            equipeDevelopement = equipeRepository.save(equipeDevelopement);

        }else {
            equipeDevelopement = equipeRepository.save(equipeDevelopement);

        }
        return equipeDevelopementMapper.fromEntity(equipeDevelopement);
    }

    @Override
    public EquipeDevelopementDTO getEquipeById(Long id) {
        EquipeDevelopement equipe = equipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipe not found with id: " + id));
        return equipeDevelopementMapper.fromEntity(equipe);
    }

    @Override
    public EquipeDevelopementDTO updateEquipe(EquipeDevelopementDTO equipeDevelopementDTO) {
        EquipeDevelopement existingEquipe = equipeRepository.findById(equipeDevelopementDTO.id())
                .orElseThrow(() -> new ResourceNotFoundException("Equipe not found with id: " + equipeDevelopementDTO.id()));

        // Update fields if needed
        // Since developers are managed through addDeveloperToEquipe, we might not update them here
        existingEquipe = equipeRepository.save(existingEquipe);
        return equipeDevelopementMapper.fromEntity(existingEquipe);
    }

    @Override
    public void deleteEquipe(Long id) {
        EquipeDevelopement equipe = equipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipe not found with id: " + id));
        equipeRepository.delete(equipe);
    }

    @Override
    public List<EquipeDevelopementDTO> getAllEquipes() {
        return equipeRepository.findAll()
                .stream()
                .map(equipeDevelopementMapper::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public EquipeDevelopementDTO addDeveloperToEquipe(Long equipeId, Long developerId) {
        EquipeDevelopement equipe = equipeRepository.findById(equipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipe not found with id: " + equipeId));

        Developer developer = developerRepository.findById(developerId)
                .orElseThrow(() -> new ResourceNotFoundException("Developer not found with id: " + developerId));

        // Set the bidirectional relationship
        equipe.getDevelopers().add(developer);
        developer.setEquipe(equipe);

        equipeRepository.save(equipe);
        developerRepository.save(developer);

        return equipeDevelopementMapper.fromEntity(equipe);
    }
}