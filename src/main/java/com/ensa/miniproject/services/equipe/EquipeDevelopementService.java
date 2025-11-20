package com.ensa.miniproject.services.equipe;

import com.ensa.miniproject.dto.EquipeDevelopementDTO;
import java.util.List;

public interface EquipeDevelopementService {
    EquipeDevelopementDTO createEquipe(EquipeDevelopementDTO equipeDevelopementDTO);
    EquipeDevelopementDTO getEquipeById(Long id);
    EquipeDevelopementDTO updateEquipe(EquipeDevelopementDTO equipeDevelopementDTO);
    void deleteEquipe(Long id);
    List<EquipeDevelopementDTO> getAllEquipes();
    EquipeDevelopementDTO addDeveloperToEquipe(Long equipeId, Long developerId);
}