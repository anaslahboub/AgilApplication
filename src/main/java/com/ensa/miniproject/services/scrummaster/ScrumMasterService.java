package com.ensa.miniproject.services.scrummaster;

import com.ensa.miniproject.dto.ScrumMasterDTO;

import java.util.List;

public interface ScrumMasterService {
     ScrumMasterDTO createScrumMaster(ScrumMasterDTO scrumMasterDTO);
     ScrumMasterDTO getScrumMasterById(Long id);
     ScrumMasterDTO updateScrumMaster(ScrumMasterDTO scrumMasterDTO);
     void deleteScrumMaster(Long id);
     List<ScrumMasterDTO> getScrumMasters();
}
