package com.ensa.miniproject.services.ScrumMaster;

import com.ensa.miniproject.DTO.ScrumMasterDTO;

import java.util.List;

public interface ScrumMasterService {
    public ScrumMasterDTO createScrumMaster(ScrumMasterDTO scrumMasterDTO);
    public ScrumMasterDTO getScrumMasterById(Long id);
    public ScrumMasterDTO updateScrumMaster(ScrumMasterDTO scrumMasterDTO);
    public void deleteScrumMaster(Long id);
    public List<ScrumMasterDTO> getScrumMasters();
}
